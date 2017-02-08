package com.example.jcaruso.myapplication;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.example.jcaruso.myapplication.movie.MovieActivity;

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

    @Before
    public void setup() {
        Activity activity = Robolectric.setupActivity(MovieActivity.class);
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
    }
}
