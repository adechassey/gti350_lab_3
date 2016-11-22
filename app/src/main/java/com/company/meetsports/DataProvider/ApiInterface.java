package com.company.meetsports.DataProvider;

import com.company.meetsports.Entities.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("events")
    //Call<EventsResponse> getAllEvents(@Query("api_key") String apiKey);
    Call<List<Event>> getAllEvents();

    @GET("events/{id}")
    //Call<EventsResponse> getEventById(@Path("id") int id, @Query("api_key") String apiKey);
    Call<Event> getEventById(@Path("id") Integer id_event);

    @POST("events")
    Call<Event> addEvent(@Body Event event);

    @DELETE("/{id}")
    Call<Event> deleteEvent(@Path("id") Integer id_event);
}