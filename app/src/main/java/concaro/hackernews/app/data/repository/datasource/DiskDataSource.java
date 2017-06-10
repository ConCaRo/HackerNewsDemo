package concaro.hackernews.app.data.repository.datasource;

import android.support.annotation.NonNull;

import io.realm.Realm;
import rx.Observable;
import concaro.hackernews.app.data.repository.datasource.*;

/**
 * Created by Concaro on 12/19/2016.
 */

public class DiskDataSource implements concaro.hackernews.app.data.repository.datasource.DataStore {

    private Realm realm;

    public DiskDataSource(@NonNull Realm realm) {
        this.realm = realm;
    }


    @Override
    public Observable<Object> getHackerNewsTopStories(boolean refresh) {
        return null;
    }

    @Override
    public Observable<Object> getHackerNewStoryDetail(boolean refresh, String id) {
        return null;
    }

    @Override
    public Observable<Object> getHackerNewCommentDetail(boolean refresh, String id) {
        return null;
    }

}
