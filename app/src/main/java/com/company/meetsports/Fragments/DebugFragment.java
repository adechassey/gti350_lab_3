package com.company.meetsports.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.meetsports.Adapters.MoviesAdapter;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Movie;
import com.company.meetsports.Entities.MoviesResponse;
import com.company.meetsports.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Antoine on 09/11/2016.
 */

public class DebugFragment extends Fragment {
    private static final String TAG = "DebugFragment";

    /**
     *
     * http://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
     *
     */

    // TODO - insert API KEY here (https://www.themoviedb.org)
    private final static String API_KEY = "3dc7a68bed9ff5f3029a3ae47a9f6589";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_debug, container, false);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
        }

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return v;
    }
}
