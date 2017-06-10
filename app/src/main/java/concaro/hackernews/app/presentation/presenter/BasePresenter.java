package concaro.hackernews.app.presentation.presenter;

import io.realm.Realm;

/**
 * Created by Concaro on 11/7/2016.
 */

public abstract class BasePresenter {

    Realm realm;

    public BasePresenter(Realm realm) {
        this.realm = realm;
    }
}
