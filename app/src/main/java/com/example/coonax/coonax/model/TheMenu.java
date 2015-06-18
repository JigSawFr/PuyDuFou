package com.example.coonax.coonax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Projet       ~~ PuyDuFou ~~
 * ***************************************
 * Créé par JigSaw le 18/06/2015 à 05:22
 * **************************************
 * ***************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class TheMenu {

    @Expose
    private String dessert;
    @Expose
    private Integer id;
    @SerializedName(value="idRestaurant")
    private Restaurant restaurant;
    @Expose
    private String image;
    @Expose
    private String middle;
    @Expose
    private String name;
    @Expose
    private Double price;
    @Expose
    private String starter;

    /**
     *
     * @return
     * The dessert
     */
    public String getDessert() {
        return dessert;
    }

    /**
     *
     * @param dessert
     * The dessert
     */
    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     *
     * @param restaurant
     * The restaurant
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The middle
     */
    public String getMiddle() {
        return middle;
    }

    /**
     *
     * @param middle
     * The middle
     */
    public void setMiddle(String middle) {
        this.middle = middle;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The price
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The starter
     */
    public String getStarter() {
        return starter;
    }

    /**
     *
     * @param starter
     * The starter
     */
    public void setStarter(String starter) {
        this.starter = starter;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
