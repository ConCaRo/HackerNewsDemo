package concaro.hackernews.app.presentation.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Concaro on 12/8/2016.
 */

public class HNComment  implements Serializable {

//    @PrimaryKey
    private int id;
    private int parent;
    private String by;
    private List<Integer> kids;

    private Long time;
    private String text;
    private String type;

    int level;
    boolean available = false;

    public HNComment() {
    }

    public HNComment(int  id, int level, boolean available) {
        this.id = id;
        this.level = level;
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
