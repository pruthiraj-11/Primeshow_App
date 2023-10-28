package com.app.primeshow.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.primeshow.Adapters.ActorListAdapter;
import com.app.primeshow.Adapters.GenresEachFilmListAdapter;
import com.app.primeshow.Models.FilmItem;
import com.app.primeshow.databinding.ActivityDetailBinding;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private int idFilm;
    private RecyclerView.Adapter adapterActorList,adapterCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        idFilm=getIntent().getIntExtra("id",0);
        binding.imageRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.genreRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.backbtndetail.setOnClickListener(v -> finish());
        sendRequest();
    }

    private void sendRequest() {
        requestQueue= Volley.newRequestQueue(this);
        binding.progressBarDetail.setVisibility(View.VISIBLE);
        binding.scrollView2.setVisibility(View.GONE);
        stringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                binding.progressBarDetail.setVisibility(View.GONE);
                binding.scrollView2.setVisibility(View.VISIBLE);

                FilmItem item=gson.fromJson(response, FilmItem.class);
                Glide.with(DetailActivity.this).load(item.getPoster()).into(binding.picDetail);
                binding.movieName.setText(item.getTitle());
                binding.movieStar.setText(item.getImdbRating());
                binding.movieTime.setText(item.getRuntime());
                binding.movieSummary.setText(item.getPlot());
                binding.movieActorInfo.setText(item.getActors());

                if(item.getImages()!=null){
                    adapterActorList=new ActorListAdapter(item.getImages());
                    binding.imageRecyclerView.setAdapter(adapterActorList);
                }
                if(item.getGenres()!=null){
                    adapterCategoryList=new GenresEachFilmListAdapter(item.getGenres());
                    binding.genreRecyclerView.setAdapter(adapterCategoryList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBarDetail.setVisibility(View.GONE);
            }
        });
        requestQueue.add(stringRequest);
    }
}