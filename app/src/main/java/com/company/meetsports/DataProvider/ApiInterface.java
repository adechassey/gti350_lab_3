package com.company.meetsports.DataProvider;

import com.company.meetsports.Entities.EventsResponse;
import com.company.meetsports.Entities.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("events")
    //Call<EventsResponse> getAllEvents(@Query("api_key") String apiKey);
    Call<EventsResponse> getAllEvents();

    @GET("events/{id}")
    //Call<EventsResponse> getEventById(@Path("id") int id, @Query("api_key") String apiKey);
    Call<EventsResponse> getEventById(@Path("id") Integer id_event);
}