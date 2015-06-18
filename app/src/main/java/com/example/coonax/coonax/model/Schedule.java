package com.example.coonax.coonax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Projet       ~~ PuyDuFou ~~
 * ***************************************
 * Créé par JigSaw le 17/06/2015 à 15:15
 * **************************************
 * ***************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class Schedule implements Comparable<Schedule> {

    @Expose
    private Integer id;
    @Expose
    @SerializedName(value="idShow")
    private Show show;
    @Expose
    private String startTime;
    private boolean isChoosen;

    {
        isChoosen = false;
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
     * The show
     */
    public Show getShow() {
        return show;
    }

    /**
     *
     * @param show
     * The show
     */
    public void setShow(Show show) {
        this.show = show;
    }

    /**
     *
     * @return
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public boolean isChoosen() {
        return isChoosen;
    }

    public void setIsChoosen(boolean b) {
        this.isChoosen = b;
    }

    @Override
    public int compareTo(Schedule mySchedule) {
        return getStartTime().compareTo(mySchedule.getStartTime());
    }
}