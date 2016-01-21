package com.example.marcin.parseworkshops;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.ImageView;

/**
 * Created by Marcin on 19.01.2016.
 */
class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private ArrayList<Joke> items;

    public MyAdapter(ArrayList<Joke> items) {
        this.items = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(parent.getContext(), R.layout.row_joke, null));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Joke joke = items.get(position);
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    Vote vote = new Vote();
                    vote.setJoke(joke);
                    vote.setUser(ParseUser.getCurrentUser());
                    holder.score.setText("" + (Integer.parseInt(holder.score.getText().toString()) + 1));
                    vote.saveInBackground();
                    holder.plus.setTint(holder.itemView.getResources().getColor(R.color.colorAccent));
                    v.setTag(vote);
                } else {
                    Vote vote = (Vote) v.getTag();
                    holder.score.setText("" + (Integer.parseInt(holder.score.getText().toString()) - 1));
                    vote.deleteInBackground();
                    holder.plus.setTint(holder.itemView.getResources().getColor(R.color.carbon_icon_active_dark));
                    v.setTag(null);
                }
            }
        });

        ParseQuery<Vote> query = ParseQuery.getQuery(Vote.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("joke", joke);
        query.findInBackground(new FindCallback<Vote>() {
            @Override
            public void done(List<Vote> objects, ParseException e) {
                if (e != null)
                    return;
                if (objects != null && !objects.isEmpty()) {
                    holder.plus.setTag(objects.get(0));
                    holder.plus.setTint(holder.itemView.getResources().getColor(R.color.colorAccent));
                } else {
                    holder.plus.setTag(null);
                    holder.plus.setTint(holder.itemView.getResources().getColor(R.color.carbon_icon_active_dark));
                }
            }
        });
        ParseQuery<Vote> query2 = ParseQuery.getQuery(Vote.class);
        query2.whereEqualTo("joke", joke);
        query2.findInBackground(new FindCallback<Vote>() {
            @Override
            public void done(List<Vote> objects, ParseException e) {
                if (e != null || objects == null)
                    return;
                holder.score.setText("" + objects.size());
            }
        });

        holder.joke.setText(joke.getJoke());
        if (joke.getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
            holder.remove.setVisibility(View.VISIBLE);
        } else {
            holder.remove.setVisibility(View.GONE);
        }
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView score, joke;
        public ImageView plus, remove;

        public Holder(View itemView) {
            super(itemView);

            score = (TextView) itemView.findViewById(R.id.score);
            joke = (TextView) itemView.findViewById(R.id.joke);
            plus = (ImageView) itemView.findViewById(R.id.plus);
            remove = (ImageView) itemView.findViewById(R.id.remove);
        }
    }
}
