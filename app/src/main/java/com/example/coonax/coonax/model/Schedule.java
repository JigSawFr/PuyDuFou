package com.example.coonax.coonax.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

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

    public void writeSchedule(List<Schedule> myPreferedSchedules, Context myContext) {
        Log.d("PUYDUFOU", "SCHEDULE_MODEL :: Sauvegarde du planning personnalisé !");
        Gson gson = new Gson();
        String value = gson.toJson(myPreferedSchedules);
        SharedPreferences prefs = myContext.getSharedPreferences("myPreferedSchedules", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putString("schedules", value);
        e.commit();
    }

    public List<Schedule> readSchedule(Context myContext) {
        Log.d("PUYDUFOU", "SCHEDULE_MODEL :: Lecture du planning personnalisé !");
        SharedPreferences prefs = myContext.getSharedPreferences("myPreferedSchedules", Context.MODE_PRIVATE);
        String value = prefs.getString("schedules", null);
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        Schedule[] list = gson.fromJson(value, Schedule[].class);
        return Arrays.asList(list);
    }

    @Override
    public int compareTo(Schedule mySchedule) {
        return getStartTime().compareTo(mySchedule.getStartTime());
    }
}