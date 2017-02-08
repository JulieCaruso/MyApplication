package com.example.jcaruso.myapplication.moviedetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcaruso.myapplication.R;
import com.example.jcaruso.myapplication.movie.Movie;
import com.example.jcaruso.myapplication.movie.MovieManager;
import com.koushikdutta.ion.Ion;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "MovieDetailsActivity.EXTRA_MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        findViewById(R.id.movie_details_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle args = getIntent().getExtras();
        if (args != null) {
            Integer movieId = args.getInt(EXTRA_MOVIE_ID);
            if (movieId != null)
                setView(MovieManager.getMovies().get(movieId));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadIpAddress();
    }

    private void setView(Movie movie) {
        String baseUrl = "https://images-na.ssl-images-amazon.com/images/M/";
        ImageView image = (ImageView) findViewById(R.id.movie_details_image);
        Ion.with(this).load(baseUrl + movie.getImageUrl()).intoImageView(image);
        ((TextView) findViewById(R.id.movie_details_title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.movie_details_director)).setText(getString(R.string.directed_by, movie.getDirector()));
        ((TextView) findViewById(R.id.movie_details_actors)).setText(movie.getActorsString());
    }

    private Observable<String> getIpAddress() {
        return new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()) {
                                String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                                observer.onNext(ip);
                            }
                        }
                    }
                } catch (SocketException e) {
                    observer.onError(e);
                }
            }
        };
    }

    private void loadIpAddress() {
        Observable<String> observable = getIpAddress();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        loadIpLocation(value);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("Test", "load ip address error: " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    private void loadIpLocation(final String ip) {
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint("http://geo.groupkt.com")
                .build();

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://geo.groupkt.com/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
*/
        final IpLocationService service = retrofit.create(IpLocationService.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Subscriber<IpLocation> subscriber = new Subscriber<IpLocation>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(IpLocation ipLocation) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                Observable<IpLocation> observable = service.getIpLocation(ip);
                observable.subscribe((Observer<? super IpLocation>) subscriber);
            }
        }).start();


    }

    public interface IpLocationService {
        @GET("/ip/{ip}/json")
        Observable<IpLocation> getIpLocation(@Path("ip") String ip);
    }
}
