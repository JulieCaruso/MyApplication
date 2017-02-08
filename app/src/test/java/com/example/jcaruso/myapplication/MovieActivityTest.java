package com.example.jcaruso.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.example.jcaruso.myapplication.movie.MovieActivity;
import com.example.jcaruso.myapplication.moviedetails.MovieDetailsActivity;

import org.hamcrest.Matchers;
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
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MovieActivityTest {

    private RecyclerView recycler;

    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MovieActivity.class);
        recycler = (RecyclerView) activity.findViewById(R.id.movie_recycler);
    }

    @Test
    public void itemsInRecycler() {
        Assert.assertTrue("Recycler should contain elements", recycler.getAdapter().getItemCount() > 0);
    }

    @Test
    public void clickItem() {
        recycler.findViewHolderForAdapterPosition(0).itemView.performClick();
        ShadowApplication application = Shadows.shadowOf(RuntimeEnvironment.application);
        Assert.assertThat("MovieDetailsActivity has started", application.getNextStartedActivity(), IsNull.notNullValue());

        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivityForResult().intent;
        Assert.assertThat("MovieDetailsActivity has started", intent.getComponent(), Matchers.equalTo(new ComponentName(activity, MovieDetailsActivity.class)));
        Assert.assertEquals("extra contains correct movie id : 0", intent.getIntExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, 1), 0);
    }
}
