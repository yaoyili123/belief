package com.example.belief.data.network.model;

public class UserSportInfo {

    private Integer uid;

    private Integer totalSportTime;

    private Integer totalKcal;

    private Integer todayKcal;

    public UserSportInfo() {
    }

    public UserSportInfo(Integer uid, Integer totalSportTime, Integer totalKocal, Integer todaykocal) {
        this.uid = uid;
        this.totalSportTime = totalSportTime;
        this.totalKcal = totalKocal;
        this.todayKcal = todaykocal;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTotalSportTime() {
        return totalSportTime;
    }

    public void setTotalSportTime(Integer totalSportTime) {
        this.totalSportTime = totalSportTime;
    }

    public Integer getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(Integer totalKcal) {
        this.totalKcal = totalKcal;
    }

    public Integer getTodayKcal() {
        return todayKcal;
    }

    public void setTodayKcal(Integer todayKcal) {
        this.todayKcal = todayKcal;
    }

}
