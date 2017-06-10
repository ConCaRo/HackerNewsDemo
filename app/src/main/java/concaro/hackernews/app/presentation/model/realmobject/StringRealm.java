package concaro.hackernews.app.presentation.model.realmobject;

import io.realm.RealmObject;

/**
 * Created by Concaro on 5/19/2017.
 */

public class StringRealm extends RealmObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
