package com.app.primeshow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.primeshow.Models.GenreItems;
import com.app.primeshow.R;

import java.util.ArrayList;
import java.util.List;

public class GenresEachFilmListAdapter extends RecyclerView.Adapter<GenresEachFilmListAdapter.ViewHolder> {
    List<String> movieListModel;
    Context context;

    public GenresEachFilmListAdapter(List<String> movieListModel) {
        this.movieListModel = movieListModel;
    }

    @NonNull
    @Override
    public GenresEachFilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresEachFilmListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(movieListModel.get(position));

//        RequestOptions requestOptions=new RequestOptions();
//        requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(30));
//
//        Glide.with(context).load(movieListModel.getData().get(position).getPoster()).apply(requestOptions).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieListModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.titletextcat);
        }
    }
}
