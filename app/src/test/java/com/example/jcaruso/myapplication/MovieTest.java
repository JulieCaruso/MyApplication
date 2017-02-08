package com.example.jcaruso.myapplication;

import com.example.jcaruso.myapplication.movie.Movie;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MovieTest {

    @Test
    public void movieCreationWithNullActor() {
        Movie movie = new Movie("title", "director", null, "imageUrl");
        Assert.assertNotNull("Actors list size mustn't be null", movie.getActors());
        Assert.assertEquals("Actors list size must be 0", movie.getActors().size(), 0);
    }

    @Test
    public void movieCreationWithNoActor() {
        Movie movie = new Movie("title", "director", new ArrayList<String>(), "imageUrl");
        Assert.assertEquals("Actors list size must be 0", movie.getActors().size(), 0);
    }

    @Test
    public void movieCreationWithActors() {
        Movie movie = new Movie("title", "director", new ArrayList<String>() {{
            add("actor0");
            add("actor1");
            add("actor2");
        }}, "imageUrl");
        Assert.assertEquals("Actors list size must be 3", movie.getActors().size(), 3);
    }

    @Test
    public void getActorsStringWithNoActor() {
        Movie movie = new Movie("title", "director", null, "imageUrl");
        Assert.assertEquals("Actors string must be empty string", movie.getActorsString(), "");
        Assert.assertTrue("Actors string must be empty string", movie.getActorsString().isEmpty());
        Assert.assertNotNull("Actors string mustn't be null", movie.getActorsString());
    }

    @Test
    public void getActorsStringWithActors() {
        Movie movie = new Movie("title", "director", new ArrayList<String>() {{
            add("actor0");
            add("actor1");
            add("actor2");
        }}, "imageUrl");
        Assert.assertEquals("Actors string must be as", movie.getActorsString(), "actor0, actor1, actor2");
    }
}
