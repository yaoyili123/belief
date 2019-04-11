package com.example.belief.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "food")
public class Food {

    @Id(autoincrement = true)
    private Long fid;

    private String name;

    @Property(nameInDb = "photo_id")
    private Long pid;

    private String detail;

    private String ingredient;

    @Generated(hash = 1486700592)
    public Food(Long fid, String name, Long pid, String detail, String ingredient) {
        this.fid = fid;
        this.name = name;
        this.pid = pid;
        this.detail = detail;
        this.ingredient = ingredient;
    }

    @Generated(hash = 866324199)
    public Food() {
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
