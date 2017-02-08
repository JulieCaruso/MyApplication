package com.example.jcaruso.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcaruso.myapplication.movie.Movie;
import com.example.jcaruso.myapplication.movie.MovieManager;
import com.example.jcaruso.myapplication.moviedetails.MovieDetailsActivity;

import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MovieDetailsActivityTest {

    private TextView title;
    private TextView director;
    private TextView actors;
    private ImageView image;
    private ImageView back;

    private Movie movie;

    ShadowActivity shadowActivity;

    @Before
    public void setup() {
        Intent intent = new Intent(Shadows.shadowOf(RuntimeEnvironment.application).getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, 0);
        movie = MovieManager.getMovies().get(0);
        Activity activity = Robolectric.buildActivity(MovieDetailsActivity.class).withIntent(intent).create().get();
        shadowActivity = Shadows.shadowOf(activity);

        title = (TextView) activity.findViewById(R.id.movie_details_title);
        director = (TextView) activity.findViewById(R.id.movie_details_director);
        actors = (TextView) activity.findViewById(R.id.movie_details_actors);
        image = (ImageView) activity.findViewById(R.id.movie_details_image);
        back = (ImageView) activity.findViewById(R.id.movie_details_back);
    }

    @Test
    public void correctMovieIsDisplayed() {
        Assert.assertEquals("correct title", title.getText().toString(), movie.getTitle());
        Assert.assertEquals("correct director", director.getText().toString(),
                Shadows.shadowOf(RuntimeEnvironment.application).getApplicationContext().getString(R.string.directed_by, movie.getDirector()));
        Assert.assertEquals("correct actors", actors.getText().toString(), movie.getActorsString());
        Assert.assertNotNull("imageView isn't empty", image.getDrawable());
    }

    @Test
    public void onClickBack() {
        ShadowApplication application = Shadows.shadowOf(RuntimeEnvironment.application);
        back.performClick();
        Assert.assertTrue("activity is finished", shadowActivity.isFinishing());
    }
}
