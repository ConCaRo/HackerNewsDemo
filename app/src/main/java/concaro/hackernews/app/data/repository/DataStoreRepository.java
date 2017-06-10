package concaro.hackernews.app.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import concaro.hackernews.app.data.repository.datasource.DataStore;
import concaro.hackernews.app.data.repository.datasource.DataStoreFactory;
import concaro.hackernews.app.domain.repository.DataRepository;

/**
 * Created by Concaro on 12/19/2016.
 */
@Singleton
public class DataStoreRepository implements DataRepository {

    private DataStoreFactory dataStoreFactory;

    @Inject
    public DataStoreRepository(DataStoreFactory dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<Object> getHackerNewsTopStories(boolean refresh) {
        DataStore dataStore = dataStoreFactory.getCloudDataStore();
        return dataStore.getHackerNewsTopStories(refresh);
    }

    @Override
    public Observable<Object> getHackerNewStoryDetail(boolean refresh, String id) {
        DataStore dataStore = dataStoreFactory.getCloudDataStore();
        return dataStore.getHackerNewStoryDetail(refresh, id);
    }

    @Override
    public Observable<Object> getHackerNewCommentDetail(boolean refresh, String id) {
        DataStore dataStore = dataStoreFactory.getCloudDataStore();
        return dataStore.getHackerNewCommentDetail(refresh, id);
    }

}
