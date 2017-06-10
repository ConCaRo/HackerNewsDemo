package concaro.hackernews.app.data.rest.api;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by CONCARO on 10/19/2015.
 */
public interface HackerNewsService {

    @GET("topstories.json")
    Observable<Object> getHackerNewsTopStories();

    @GET("item/{id}.json")
    Observable<Object> getHackerNewStoryDetail(@Path("id") String id);

    @GET("item/{id}.json")
    Observable<Object> getHackerNewCommentDetail(@Path("id") String id);

}
