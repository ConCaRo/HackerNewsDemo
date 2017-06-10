package concaro.hackernews.app.domain.repository;

import rx.Observable;

/**
 * Created by Concaro on 12/19/2016.
 */

public interface DataRepository {


    Observable<Object> getHackerNewsTopStories(boolean refresh);

    Observable<Object> getHackerNewStoryDetail(boolean refresh, String id);

    Observable<Object> getHackerNewCommentDetail(boolean refresh, String id);
}


