package concaro.hackernews.app.data.rest;

import android.content.Context;

import rx.Observable;
import rx.functions.Func1;
import concaro.hackernews.app.data.rest.*;
import concaro.hackernews.app.data.rest.api.ClientApi;

/**
 * Created by Concaro on 11/4/2016.
 */
public class RestApiImpl implements concaro.hackernews.app.data.rest.RestApi {

    ClientApi clientApi;

    public RestApiImpl(Context context) {
        clientApi = new ClientApi(context);
    }


    @Override
    public Observable<Object> getHackerNewsTopStories() {
        return clientApi.getHackerNewsService().getHackerNewsTopStories()
                .flatMap(new Func1<Object, Observable<? extends Object>>() {
                    @Override
                    public Observable<? extends Object> call(Object objectBaseEntity) {
                        return Observable.just(objectBaseEntity);
                    }
                });
    }

    @Override
    public Observable<Object> getHackerNewStoryDetail(String id) {
        return clientApi.getHackerNewsService().getHackerNewStoryDetail(id)
                .flatMap(new Func1<Object, Observable<? extends Object>>() {
                    @Override
                    public Observable<? extends Object> call(Object objectBaseEntity) {
                        return Observable.just(objectBaseEntity);
                    }
                });
    }

    @Override
    public Observable<Object> getHackerNewCommentDetail(String id) {
        return clientApi.getHackerNewsService().getHackerNewCommentDetail(id)
                .flatMap(new Func1<Object, Observable<? extends Object>>() {
                    @Override
                    public Observable<? extends Object> call(Object objectBaseEntity) {
                        return Observable.just(objectBaseEntity);
                    }
                });
    }


//    @Override
//    public Observable<BaseEntity<Object>> userValidateCoupon(String userid, String
//            token, String coupon) {
//        return clientApi.getRestService().userValidateCoupon(userid, token, coupon)
//                .flatMap(new Func1<BaseEntity<Object>, Observable<? extends BaseEntity<Object>>>() {
//                    @Override
//                    public Observable<? extends BaseEntity<Object>> call(BaseEntity<Object> objectBaseEntity) {
//                        return Observable.just(objectBaseEntity);
//                    }
//                });
//    }
}
