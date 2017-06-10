package concaro.hackernews.app.data.rest;

import rx.Observable;

/**
 * Created by Concaro on 12/19/2016.
 */

public interface RestApi {

    Observable<Object> getHackerNewsTopStories();

    Observable<Object> getHackerNewStoryDetail(String id);

    Observable<Object> getHackerNewCommentDetail(String id);

}
