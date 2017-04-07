package com.omottec.demoapp.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qinbingbing on 07/04/2017.
 */
@Entity
public class Note {
    @Id
    private Long id;

    @NotNull
    private String text;
    private String comment;

    @Generated(hash = 1038952471)
    public Note(Long id, @NotNull String text, String comment) {
        this.id = id;
        this.text = text;
        this.comment = comment;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
