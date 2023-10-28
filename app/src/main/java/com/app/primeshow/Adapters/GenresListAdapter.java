package com.app.primeshow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.primeshow.Activities.DetailActivity;
import com.app.primeshow.Models.GenreItems;
import com.app.primeshow.Models.MovieListModel;
import com.app.primeshow.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GenresListAdapter extends RecyclerView.Adapter<GenresListAdapter.ViewHolder> {
    ArrayList<GenreItems> movieListModel;
    Context context;

    public GenresListAdapter(ArrayList<GenreItems> movieListModel) {
        this.movieListModel = movieListModel;
    }

    @NonNull
    @Override
    public GenresListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(movieListModel.get(position).getName());

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
