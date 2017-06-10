package concaro.hackernews.app.presentation.model.realmobject;

import io.realm.RealmObject;

/**
 * Created by Concaro on 5/22/2017.
 */

public class MyMap extends RealmObject {
    private int key;
    private int value;

    public MyMap() {
    }

    public MyMap(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
