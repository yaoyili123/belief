package com.example.belief.data.network.model;

import java.sql.Date;

public class UserKcalTrend {
    private Integer uid;

    private Integer kcal;

    private Date date;

    public UserKcalTrend() {
    }

    public UserKcalTrend(Integer uid, Integer kcal, Date date) {
        this.uid = uid;
        this.kcal = kcal;
        this.date = date;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getKcal() {
        return kcal;
    }

    public void setKcal(Integer kcal) {
        this.kcal = kcal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
