package com.example.coonax.coonax.model;

import com.google.gson.annotations.Expose;

public class Shop {

    @Expose
    private Integer id;
    @Expose
    private String image;
    @Expose
    private String latitude;
    @Expose
    private String longitude;
    @Expose
    private String name;
    @Expose
    private String shortDescription;

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
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
     * The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     *
     * @param shortDescription
     * The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}