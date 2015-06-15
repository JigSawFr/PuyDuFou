package com.example.coonax.coonax.service;

import com.example.coonax.coonax.model.*;

import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public class PuyDuFou {

    private static final String DOMAIN = "10.154.128.142";
    private static final String PORT = "100800";
    private static final String PATH = "/puyDuFou/webresources";
    //public static final String ENDPOINT = "http://10.154.128.142:10080/puyDuFou/webresources";

    public String getENDPOINT() {
        return "http://" + DOMAIN + ":" + PORT + PATH;
    }

    @GET("/com.puydufou.entity.shops")
    void listShopAsync(Callback<List<Shop>> callback) {}

    @GET("/com.puydufou.entity.shops/{id}")
    void shopAsync(@Path("id") int id, Callback<Shop> callback) {}

    @GET("/com.puydufou.entity.shows")
    void listShowAsync(Callback<List<Show>> callback) {}

    @GET("/com.puydufou.entity.shows/{id}")
    void showAsync(@Path("id") int id, Callback<Shop> callback) {}

    @GET("/com.puydufou.entity.restaurants")
    void listRestaurantAsync(Callback<List<Restaurant>> callback) {}

    @GET("/com.puydufou.entity.restaurants/{id}")
    void restaurantAsync(@Path("id") int id, Callback<Restaurant> callback) {}
}
