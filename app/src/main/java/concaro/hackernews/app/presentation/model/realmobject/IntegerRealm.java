package concaro.hackernews.app.presentation.model.realmobject;

import io.realm.RealmObject;

/**
 * Created by Concaro on 5/19/2017.
 */

public class IntegerRealm extends RealmObject {

    int product_id;

    public IntegerRealm() {
    }

    public IntegerRealm(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
