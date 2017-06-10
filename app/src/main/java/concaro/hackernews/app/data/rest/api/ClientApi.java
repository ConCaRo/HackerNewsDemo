package concaro.hackernews.app.data.rest.api;

import android.content.Context;

import com.sromku.simple.fb.SimpleFacebook;

import concaro.hackernews.app.data.rest.api.*;

/**
 * Created by CONCARO on 10/19/2015.
 */
public class ClientApi extends concaro.hackernews.app.data.rest.api.BaseApi {

    public ClientApi(Context context) {
        super(context);
    }

    public RestService getRestService() {
        return this.getService(RestService.class);
    }

    public FacebookService getFacebookService(SimpleFacebook mSimpleFacebook) {
        return new FacebookApi(mSimpleFacebook);
    }

    public FirebaseService getFireBaseService() {
        return this.getFirebaseService(FirebaseService.class);
    }

    public HackerNewsService getHackerNewsService() {
        return this.getHackerNewsService(HackerNewsService.class);
    }

}
