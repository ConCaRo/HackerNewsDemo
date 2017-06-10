package concaro.hackernews.app.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;
import concaro.hackernews.app.data.repository.datasource.*;
import concaro.hackernews.app.data.rest.RestApi;
import concaro.hackernews.app.data.rest.RestApiImpl;

/**
 * Created by Concaro on 12/19/2016.
 */

public class CloudDataSource implements concaro.hackernews.app.data.repository.datasource.DataStore {

    private RestApi restApi;
    // No use Realm from UI thread
    private Realm realm;

    public CloudDataSource(@NonNull Context context, @NonNull Realm realm) {
        restApi = new RestApiImpl(context);
        this.realm = realm;
    }


    @Override
    public Observable<Object> getHackerNewsTopStories(boolean refresh) {
        return restApi.getHackerNewsTopStories().doOnNext(new Action1<Object>() {
            @Override
            public void call(Object objectBaseEntity) {
                // Save to database
//                if (objectBaseEntity != null) {
//                    // Need to create realm instance in this thread
//                    Realm realm = Realm.getDefaultInstance();
//                    try {
//                        realm.beginTransaction();
                        // TODO: Clear data
                        // Clear data
//                        RealmResults<Deal> dataRealm = realm.where(Comment.class)
//                                .equalTo("post_id", Integer.valueOf(post_id))
//                                .findAll();
//                        dataRealm.deleteAllFromRealm();
//                        // Save data
//                        String strData = ClientApi.getGson().toJson(objectBaseEntity.getData());
//                        List<Comment> data = BaseApi.getGson().fromJson(strData,
//                                new TypeToken<List<Comment>>() {
//                                }.getType());
//                        for (int i = 0; i < data.size(); i++) {
//                            data.get(i).setPost_id(Integer.valueOf(post_id));
//                        }
//                        realm.copyToRealmOrUpdate(data);
//                        realm.commitTransaction();
//                    } finally {
//                        realm.close();
//                    }
//                }
            }
        });
    }

    @Override
    public Observable<Object> getHackerNewStoryDetail(boolean refresh, String id) {
        return restApi.getHackerNewStoryDetail(id).doOnNext(new Action1<Object>() {
            @Override
            public void call(Object objectBaseEntity) {
                // Save to database
//                if (objectBaseEntity != null) {
                    // Need to create realm instance in this thread
//                    Realm realm = Realm.getDefaultInstance();
//                    try {
//                        realm.beginTransaction();
                        // TODO: Clear data
                        // Clear data
//                        RealmResults<Deal> dataRealm = realm.where(Comment.class)
//                                .equalTo("post_id", Integer.valueOf(post_id))
//                                .findAll();
//                        dataRealm.deleteAllFromRealm();
//                        // Save data
//                        String strData = ClientApi.getGson().toJson(objectBaseEntity.getData());
//                        List<Comment> data = BaseApi.getGson().fromJson(strData,
//                                new TypeToken<List<Comment>>() {
//                                }.getType());
//                        for (int i = 0; i < data.size(); i++) {
//                            data.get(i).setPost_id(Integer.valueOf(post_id));
//                        }
//                        realm.copyToRealmOrUpdate(data);
//                        realm.commitTransaction();
//                    } finally {
//                        realm.close();
//                    }
//                }
            }
        });
    }

    @Override
    public Observable<Object> getHackerNewCommentDetail(boolean refresh, String id) {
        return restApi.getHackerNewCommentDetail(id).doOnNext(new Action1<Object>() {
            @Override
            public void call(Object objectBaseEntity) {
                // Save to database
//                if (objectBaseEntity != null) {
                    // Need to create realm instance in this thread
//                    Realm realm = Realm.getDefaultInstance();
//                    try {
//                        realm.beginTransaction();
                        // TODO: Clear data
                        // Clear data
//                        RealmResults<Deal> dataRealm = realm.where(Comment.class)
//                                .equalTo("post_id", Integer.valueOf(post_id))
//                                .findAll();
//                        dataRealm.deleteAllFromRealm();
//                        // Save data
//                        String strData = ClientApi.getGson().toJson(objectBaseEntity.getData());
//                        List<Comment> data = BaseApi.getGson().fromJson(strData,
//                                new TypeToken<List<Comment>>() {
//                                }.getType());
//                        for (int i = 0; i < data.size(); i++) {
//                            data.get(i).setPost_id(Integer.valueOf(post_id));
//                        }
//                        realm.copyToRealmOrUpdate(data);
//                        realm.commitTransaction();
//                    } finally {
//                        realm.close();
//                    }
//                }
            }
        });
    }
}
