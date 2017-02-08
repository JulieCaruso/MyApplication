package com.example.jcaruso.myapplication.moviedetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcaruso.myapplication.R;
import com.example.jcaruso.myapplication.movie.Movie;
import com.example.jcaruso.myapplication.movie.MovieManager;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "MovieDetailsActivity.EXTRA_MOVIE_ID";
    private final String locationApiUrl = "http://ip-api.com/";

    private TextView mLocationIon;
    private TextView mLocationRxRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        mLocationIon = (TextView) findViewById(R.id.movie_details_location_ion);
        mLocationRxRetrofit = (TextView) findViewById(R.id.movie_details_location_rxretrofit);

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
        Log.d("azerty", "start ion");
        loadIpLocationIon();
        Log.d("azerty", "start rxretrofit");
        loadIpLocationRXRetrofit();
    }

    private void setView(Movie movie) {
        String baseUrl = "https://images-na.ssl-images-amazon.com/images/M/";
        ImageView image = (ImageView) findViewById(R.id.movie_details_image);
        Ion.with(this).load(baseUrl + movie.getImageUrl()).intoImageView(image);
        ((TextView) findViewById(R.id.movie_details_title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.movie_details_director)).setText(getString(R.string.directed_by, movie.getDirector()));
        ((TextView) findViewById(R.id.movie_details_actors)).setText(movie.getActorsString());
    }

    private void loadIpLocationIon() {
        Ion.with(getApplicationContext())
                .load(locationApiUrl + "json")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, final JsonObject result) {
                        Log.d("azerty", "ion: " + result.get("city").getAsString());
                        mLocationIon.post(new Runnable() {
                            @Override
                            public void run() {
                                mLocationIon.setText(getString(R.string.location, result.get("city").getAsString()));
                            }
                        });
                    }
                });
    }

    private void loadIpLocationRXRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(locationApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        LocationService service = retrofit.create(LocationService.class);
        Observable<IpLocation> observable = service.getLocation();
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<IpLocation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(final IpLocation value) {
                        Log.d("azerty", "rxretrofit: " + value.getCity());
                        mLocationRxRetrofit.post(new Runnable() {
                            @Override
                            public void run() {
                                mLocationRxRetrofit.setText(getString(R.string.location, value.getCity()));
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface LocationService {
        @GET("json")
        Observable<IpLocation> getLocation();
    }
}
