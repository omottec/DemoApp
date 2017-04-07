package com.omottec.demoapp.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qinbingbing on 07/04/2017.
 */

@Entity
public class Good {
    @Id
    private Long id;
    private String name;
    private String desc;

    @Generated(hash = 337051043)
    public Good(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    @Generated(hash = 2016981037)
    public Good() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
