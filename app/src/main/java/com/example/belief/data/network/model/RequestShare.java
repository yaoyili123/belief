package com.example.belief.data.network.model;

public class RequestShare {

    private Integer uid;

    private String title;

    private String photoUrl;

    private String detail;

    public RequestShare() {}

    public RequestShare(Integer uid, String title, String photoUrl, String detail) {
        this.uid = uid;
        this.title = title;
        this.photoUrl = photoUrl;
        this.detail = detail;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
