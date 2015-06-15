package com.example.coonax.coonax.model;

import com.google.gson.annotations.Expose;

public class Show {

    @Expose
    private Integer actorsCount;
    @Expose
    private Integer id;
    @Expose
    private String image;
    @Expose
    private Double latitude;
    @Expose
    private Integer lenght;
    @Expose
    private String longDescription;
    @Expose
    private Double longitude;
    @Expose
    private String name;
    @Expose
    private String shortDesciption;

    /**
     *
     * @return
     * The actorsCount
     */
    public Integer getActorsCount() {
        return actorsCount;
    }

    /**
     *
     * @param actorsCount
     * The actorsCount
     */
    public void setActorsCount(Integer actorsCount) {
        this.actorsCount = actorsCount;
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
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The lenght
     */
    public Integer getLenght() {
        return lenght;
    }

    /**
     *
     * @param lenght
     * The lenght
     */
    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    /**
     *
     * @return
     * The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     *
     * @param longDescription
     * The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
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
     * The shortDesciption
     */
    public String getShortDesciption() {
        return shortDesciption;
    }

    /**
     *
     * @param shortDesciption
     * The shortDesciption
     */
    public void setShortDesciption(String shortDesciption) {
        this.shortDesciption = shortDesciption;
    }

}
