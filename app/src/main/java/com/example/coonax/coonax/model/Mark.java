package com.example.coonax.coonax.model;

import com.google.gson.annotations.Expose;

/**
 * Projet       ~~ PuyDuFou ~~
 * ***************************************
 * Créé par JigSaw le 17/06/2015 à 01:20
 * **************************************
 * ***************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class Mark {

    @Expose
    private Integer average;

    /**
     *
     * @return
     * The average
     */
    public Integer getAverage() {
        return average;
    }

    /**
     *
     * @param average
     * The average
     */
    public void setAverage(Integer average) {
        this.average = average;
    }

}