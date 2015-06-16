package com.example.coonax.coonax.service;

import com.example.coonax.coonax.model.*;

import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 15/06/2015 à 21:37
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public interface PuyDuFou {

    String ENDPOINT = "http://10.154.128.142:10080/puyDuFou/webresources";

    @GET("/com.puydufou.entity.shops")
    void listShopAsync(Callback<List<Shop>> callback);

    @GET("/com.puydufou.entity.shops/{id}")
    void shopAsync(@Path("id") int id, Callback<Shop> callback);

    @GET("/com.puydufou.entity.shows")
    void listShowAsync(Callback<List<Show>> callback);

    @GET("/com.puydufou.entity.shows/{id}")
    void showAsync(@Path("id") int id, Callback<Show> callback);

    @GET("/com.puydufou.entity.shows/notes/{id}/{mark}")
    void showMarkAsync(@Path("id") int id, @Path("mark") int mark, Callback<Mark> callback);

    @GET("/com.puydufou.entity.restaurants")
    void listRestaurantAsync(Callback<List<Restaurant>> callback);

    @GET("/com.puydufou.entity.restaurants/{id}")
    void restaurantAsync(@Path("id") int id, Callback<Restaurant> callback);
}
