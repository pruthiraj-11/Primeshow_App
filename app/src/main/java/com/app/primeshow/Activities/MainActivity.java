package com.app.primeshow.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.primeshow.Adapters.GenresListAdapter;
import com.app.primeshow.Adapters.MovieListAdapter;
import com.app.primeshow.Adapters.SliderAdapters;
import com.app.primeshow.Models.GenreItems;
import com.app.primeshow.Models.MovieListModel;
import com.app.primeshow.Models.SliderItems;
import com.app.primeshow.R;
import com.app.primeshow.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private Handler handler=new Handler();
    private RecyclerView.Adapter adapterBest,adapterUpcoming,adapterCategory;
    private RequestQueue requestQueue;
    private StringRequest stringRequest,stringRequest2,stringRequest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewbestmovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        banners();
        sendRequestBest();
        sendRequestCategory();
        sendRequestUpcoming();
    }

    private void sendRequestBest() {
        requestQueue= Volley.newRequestQueue(this);
        binding.progressBar1.setVisibility(View.VISIBLE);
        stringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                binding.progressBar1.setVisibility(View.GONE);
                MovieListModel items=gson.fromJson(response, MovieListModel.class);
                adapterBest=new MovieListAdapter(items);
                binding.viewbestmovies.setAdapter(adapterBest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar1.setVisibility(View.GONE);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void sendRequestUpcoming() {
        requestQueue= Volley.newRequestQueue(this);
        binding.progressBar3.setVisibility(View.VISIBLE);
        stringRequest3=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                binding.progressBar3.setVisibility(View.GONE);
                MovieListModel items=gson.fromJson(response, MovieListModel.class);
                adapterUpcoming=new MovieListAdapter(items);
                binding.recyclerViewUpcoming.setAdapter(adapterUpcoming);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar3.setVisibility(View.GONE);
            }
        });
        requestQueue.add(stringRequest3);
    }

    private void sendRequestCategory() {
        requestQueue= Volley.newRequestQueue(this);
        binding.progressBar2.setVisibility(View.VISIBLE);
        stringRequest2=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                binding.progressBar2.setVisibility(View.GONE);
                ArrayList<GenreItems> catList=gson.fromJson(response,new TypeToken<ArrayList<GenreItems>>(){
                }.getType());
                adapterCategory=new GenresListAdapter(catList);
                binding.recyclerViewCategory.setAdapter(adapterCategory);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar2.setVisibility(View.GONE);
            }
        });
        requestQueue.add(stringRequest2);
    }

    private void banners(){
        ArrayList<SliderItems> list=new ArrayList<>();
        list.add(new SliderItems(R.drawable.wide));
        list.add(new SliderItems(R.drawable.wide1));
        list.add(new SliderItems(R.drawable.wide3));

        binding.viewPager.setAdapter(new SliderAdapters(list,binding.viewPager));
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setClipChildren(false);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r=1-Math.abs(position);
            page.setScaleY(0.85f+r*0.15f);
        });
        binding.viewPager.setPageTransformer(compositePageTransformer);
        binding.viewPager.setCurrentItem(1);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(slideR);
            }
        });
    }
    private Runnable slideR=new Runnable() {
        @Override
        public void run() {
            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(slideR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(slideR,2000);
    }
}