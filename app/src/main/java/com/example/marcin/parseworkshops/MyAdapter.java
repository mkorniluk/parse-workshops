package com.example.marcin.parseworkshops;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

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
    public void onBindViewHolder(final Holder holder, int position) {
        final Joke joke = items.get(position);

        holder.score.setText("" + joke.getScore());
        holder.joke.setText(joke.getJoke());
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joke.incrementScore();
                holder.score.setText("" + joke.getScore());
                joke.saveInBackground();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView score, joke;
        public Button plus;

        public Holder(View itemView) {
            super(itemView);

            score = (TextView) itemView.findViewById(R.id.score);
            joke = (TextView) itemView.findViewById(R.id.joke);
            plus = (Button) itemView.findViewById(R.id.plus);
        }
    }
}
