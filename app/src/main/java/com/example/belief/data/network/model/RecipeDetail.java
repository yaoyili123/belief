package com.example.belief.data.network.model;
import java.util.Map;

public class RecipeDetail {

    private Integer rid;

    private Integer tid;

    private String name;

    private String photoUrl;

    private String detail;

    private Map<String, String> ingredient;

    private Map<String, Integer> nutritions;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(Map<String, String> ingredient) {
        this.ingredient = ingredient;
    }

    public Map<String, Integer> getNutritions() {
        return nutritions;
    }

    public void setNutritions(Map<String, Integer> nutritions) {
        this.nutritions = nutritions;
    }
}
