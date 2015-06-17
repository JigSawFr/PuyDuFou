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
    private String statut;

    /**
     *
     * @return
     * The statut
     */
    public String getStatut() {
        return statut;
    }

    /**
     *
     * @param statut
     * The statut
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

}
