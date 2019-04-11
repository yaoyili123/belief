package com.example.belief.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "recipe")
public class Recipe {

    @Id(autoincrement = true)
    private Long rid;

    private Long tid;

    private String name;

    @Property(nameInDb = "photo_id")
    private Long pid;

    private String ingredient;

    @Generated(hash = 1708996644)
    public Recipe(Long rid, Long tid, String name, Long pid, String ingredient) {
        this.rid = rid;
        this.tid = tid;
        this.name = name;
        this.pid = pid;
        this.ingredient = ingredient;
    }

    @Generated(hash = 829032493)
    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
