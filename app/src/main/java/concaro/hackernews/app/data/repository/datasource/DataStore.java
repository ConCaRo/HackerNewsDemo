package concaro.hackernews.app.data.repository.datasource;

import rx.Observable;

/**
 * Created by Concaro on 12/19/2016.
 */

public interface DataStore {


    Observable<Object> getHackerNewsTopStories(boolean refresh);

    Observable<Object> getHackerNewStoryDetail(boolean refresh, String id);

    Observable<Object> getHackerNewCommentDetail(boolean refresh, String id);
}
