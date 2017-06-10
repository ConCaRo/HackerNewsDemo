package concaro.hackernews.app.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import concaro.hackernews.app.data.repository.datasource.*;
import concaro.hackernews.app.data.repository.datasource.DataStore;
import concaro.hackernews.app.data.repository.datasource.DiskDataSource;

/**
 * Created by Concaro on 12/19/2016.
 */

@Singleton
public class DataStoreFactory {

    private Context context;
    public Realm realm;

    public Realm getRealm() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    @Inject
    public DataStoreFactory(@NonNull Context context, @NonNull Realm realm) {
        this.context = context;
        this.realm = realm;
    }

    public concaro.hackernews.app.data.repository.datasource.DataStore getCloudDataStore() {
        return new concaro.hackernews.app.data.repository.datasource.CloudDataSource(context, realm);
    }

    private concaro.hackernews.app.data.repository.datasource.DataStore getDiskDataStore() {
        return new DiskDataSource(realm);
    }

    public DataStore getPetTopics() {
        // TODO: need conditions to decide  CloudDataSource or DiskDataSource
        return getCloudDataStore();
    }

}
