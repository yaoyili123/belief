package com.example.belief.data.network.model;

public class UserInfo {

    private Integer uid;

    private String name;

    private String bothday;

    private String city;

    private String sex;

    private String photoUrl;

    public UserInfo() {
    }

    public UserInfo(Integer uid, String name, String sex, String bothday, String city, String photoUrl) {
        this.uid = uid;
        this.name = name;
        this.sex = sex;
        this.bothday = bothday;
        this.city = city;
        this.photoUrl = photoUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBothday() {
        return bothday;
    }

    public void setBothday(String bothday) {
        this.bothday = bothday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
