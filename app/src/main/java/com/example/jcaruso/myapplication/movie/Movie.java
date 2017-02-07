package com.example.jcaruso.myapplication.movie;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String mTitle;
    private String mDirector;
    private List<String> mActors = new ArrayList<>();
    private String mImageUrl;

    public Movie(String title, String director, List<String> actors, String imageUrl) {
        mTitle = title;
        mDirector = director;
        mActors.clear();
        if (actors != null) mActors.addAll(actors);
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDirector() {
        return mDirector;
    }

    public List<String> getActors() {
        return mActors;
    }

    public String getActorsString() {
        String actors = "";
        int last = mActors.size() - 1;
        for (int i = 0; i < mActors.size(); i++) {
            actors += mActors.get(i);
            if (i != last) actors += ", ";
        }
        return actors;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
