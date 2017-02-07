package com.example.jcaruso.myapplication.movie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jcaruso.myapplication.R;
import com.example.jcaruso.myapplication.moviedetails.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {

    private List<Movie> mItems = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(new MovieItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MovieViewHolder viewHolder = (MovieViewHolder) holder;
        Movie movie = mItems.get(position);
        viewHolder.mItem.setTitle(movie.getTitle());
        viewHolder.mItem.setActors(movie.getActorsString());
        viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.mItem.getContext(), MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, position);
                viewHolder.mItem.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<Movie> movies) {
        mItems.clear();
        if (movies != null) mItems.addAll(movies);
        notifyDataSetChanged();
    }

    private class MovieItem extends LinearLayout {

        private TextView mTitle;
        private TextView mActors;

        public MovieItem(Context context) {
            super(context);
            View.inflate(context, R.layout.movie_item, this);
            mTitle = (TextView) findViewById(R.id.movie_item_title);
            mActors = (TextView) findViewById(R.id.movie_item_actors);
        }

        public void setTitle(String title) {
            mTitle.setText(title);
        }

        public void setActors(String actors) {
            mActors.setText(actors);
        }
    }

    private class MovieViewHolder extends RecyclerView.ViewHolder {
        private MovieItem mItem;

        public MovieViewHolder(MovieItem itemView) {
            super(itemView);
            mItem = itemView;
        }
    }
}
