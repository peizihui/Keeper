package com.keeper.model.dao;

/*
 * Created by @GoodforGod on 4.04.2017.
 */

import com.keeper.util.dao.DatabaseResolver;

import javax.persistence.*;
import java.util.List;

/**
 * Default Comment
 */
@Entity
@Embeddable
@Table(name = DatabaseResolver.TABLE_TAGS, schema = DatabaseResolver.SCHEMA)
public class Tag {

    public static final Tag empty = new Tag();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)   private Long taskId;
    @Column(name = "tag")                                 private String tag;
    @Column(name = "counter")                               private Long counter;

//    @ManyToMany(fetch=FetchType.LAZY)
//    @JoinColumn(name="task_id")//from here to TagManager
//    private List<Task> tasks;

    private Tag() {
        this.taskId = -1L;
        this.tag = "";
        this.counter = 0L;
    }

    public Tag(Long taskId, String tag) {
        this.taskId = taskId;
        this.tag = tag;
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getTaskId() {
        return taskId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public void incCounter() {
        counter++;
    }

    public void decCounter() {
        counter--;
    }
    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return tag != null ? tag.equals(tag.tag) : tag.tag == null;
    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }
}
