package com.example.belief.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "recipe_type")
public class RecipeType {

    @Id(autoincrement = true)
    private Long tid;

    private String name;

    @Property(nameInDb = "photo_id")
    private Long pid;

    @Generated(hash = 1162969664)
    public RecipeType(Long tid, String name, Long pid) {
        this.tid = tid;
        this.name = name;
        this.pid = pid;
    }

    @Generated(hash = 1830843401)
    public RecipeType() {
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
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
}
