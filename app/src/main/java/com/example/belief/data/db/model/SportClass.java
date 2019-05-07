package com.example.belief.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "sport_class")
public class SportClass {

    @Id(autoincrement = true)
    private Long scid;

    private String name;

    private Long time;

    private Long kcal;

    private Long level;

    private Long type;

    @Property(nameInDb = "video_url")
    private String videoUrl;

    private String detail;

    @Generated(hash = 749090421)
    public SportClass(Long scid, String name, Long time, Long kcal, Long level,
            Long type, String videoUrl, String detail) {
        this.scid = scid;
        this.name = name;
        this.time = time;
        this.kcal = kcal;
        this.level = level;
        this.type = type;
        this.videoUrl = videoUrl;
        this.detail = detail;
    }

    @Generated(hash = 674947618)
    public SportClass() {
    }

    public Long getScid() {
        return scid;
    }

    public void setScid(Long scid) {
        this.scid = scid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getKcal() {
        return this.kcal;
    }

    public void setKcal(Long kcal) {
        this.kcal = kcal;
    }

    public Long getLevel() {
        return this.level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getType() {
        return this.type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "SportClass{" +
                "scid=" + scid +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", kcal=" + kcal +
                ", level=" + level +
                ", type=" + type +
                ", videoUrl='" + videoUrl + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
