package com.example.jcaruso.myapplication.moviedetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcaruso.myapplication.R;
import com.example.jcaruso.myapplication.movie.Movie;
import com.example.jcaruso.myapplication.movie.MovieManager;
import com.koushikdutta.ion.Ion;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "MovieDetailsActivity.EXTRA_MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            Integer movieId = args.getInt(EXTRA_MOVIE_ID);
            if (movieId != null)
                setView(MovieManager.getMovies().get(movieId));
        }
    }

    private void setView(Movie movie) {
        String baseUrl = "https://images-na.ssl-images-amazon.com/images/M/";
        ImageView image = (ImageView) findViewById(R.id.movie_details_image);
        Ion.with(this).load(baseUrl+movie.getImageUrl()).intoImageView(image);
        ((TextView) findViewById(R.id.movie_details_title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.movie_details_director)).setText(getString(R.string.directed_by, movie.getDirector()));
        ((TextView) findViewById(R.id.movie_details_actors)).setText(movie.getActorsString());
    }

    /*
    private void loadImage(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://images-na.ssl-images-amazon.com/images/M/")
                .build();

        ImageUrlService service = retrofit.create(ImageUrlService.class);

        Observable<Drawable> observable = service.getImage(url);

        observable.subscribe(new Observer<Drawable>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("Test", " on subscribe");
            }

            @Override
            public void onNext(Drawable value) {
                Log.d("Test", value.toString());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("Test", "error: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("Test", " -- complete -- ");
            }
        });

        }
        */

/*
    public interface ImageUrlService {
        @GET("{file}")
        Observable<Drawable> getImage(@Path("file") String file);
    }
    */

}
