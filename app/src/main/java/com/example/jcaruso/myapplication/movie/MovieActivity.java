package com.example.jcaruso.myapplication.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jcaruso.myapplication.R;

public class MovieActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);

        mRecycler = (RecyclerView) findViewById(R.id.movie_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieAdapter();
        mAdapter.setItems(MovieManager.getMovies());
        mRecycler.setAdapter(mAdapter);
    }
}
