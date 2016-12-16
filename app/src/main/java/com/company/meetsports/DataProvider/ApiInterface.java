package com.company.meetsports.DataProvider;

import com.company.meetsports.Entities.Event;
import com.company.meetsports.Entities.User;
import com.google.gson.annotations.JsonAdapter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    // Event methods
    @GET("events")
    //Call<EventsResponse> getAllEvents(@Query("api_key") String apiKey);
    Call<List<Event>> getAllEvents();

    /*
        @GET("events/{id}")
        //Call<EventsResponse> getEventById(@Path("id") int id, @Query("api_key") String apiKey);
        Call<Event> getEventById(@Path("id") Integer id_event);
    */
    @GET("events/attending/{id}")
    Call<List<Event>> getEventsAttendingByUserId(@Path("id") Integer id_user);

    @GET("events/created/{id}")
    Call<List<Event>> getEventsCreatedByUserId(@Path("id") Integer id_user);

    @POST("events/position")
    @FormUrlEncoded
    Call<List<Event>> getEventsByPosition(@Field("latitude") Double latitude, @Field("longitude") Double longitude);

    @POST("events")
    Call<ResponseBody> addEvent(@Body Event event);

    /*
        @POST("events/{id}")
        Call<Event> addEventForUserId(@Path("id") Integer id_user, @Body Event event);
    */
    @DELETE("events/{id}")
    Call<ResponseBody> deleteEvent(@Path("id") Integer id_event);


    // User methods

    @POST("users")
    @FormUrlEncoded
    Call<User> getUserByUsername(@Field("email") String email);

    @POST("users/access")
    @FormUrlEncoded
    Call<Boolean> getUserAccess(@Field("email") String email, @Field("password") String password);

    @POST("users")
    Call<ResponseBody> addUser(@Body User user);

    // Attendance methods
    @POST("attendances/{id_event}/{id_user}")
    Call<ResponseBody> addAttendance(@Path("id_event") Integer id_event, @Path("id_user") Integer id_user);

    @DELETE("attendances/{id_event}/{id_user}")
    Call<ResponseBody> deleteAttendance(@Path("id_event") Integer id_event, @Path("id_user") Integer id_user);

}