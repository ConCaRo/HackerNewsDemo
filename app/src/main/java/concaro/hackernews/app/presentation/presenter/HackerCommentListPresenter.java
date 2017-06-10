package concaro.hackernews.app.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import concaro.hackernews.app.data.rest.api.ClientApi;
import concaro.hackernews.app.domain.exception.DefaultErrorBundle;
import concaro.hackernews.app.domain.exception.ErrorBundle;
import concaro.hackernews.app.domain.interactor.DefaultSubscriber;
import concaro.hackernews.app.domain.interactor.GetHackerNewCommentDetail;
import concaro.hackernews.app.domain.interactor.Usecase;
import concaro.hackernews.app.presentation.exception.ErrorMessageFactory;
import concaro.hackernews.app.presentation.internal.di.PerActivity;
import concaro.hackernews.app.presentation.model.HNComment;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.model.PaginatorEntity;
import concaro.hackernews.app.presentation.presenter.*;
import concaro.hackernews.app.presentation.presenter.Presenter;
import concaro.hackernews.app.presentation.util.Functions;
import concaro.hackernews.app.presentation.view.HackerCommentListView;

/**
 * Created by Concaro on 11/7/2016.
 */

@PerActivity
public class HackerCommentListPresenter extends concaro.hackernews.app.presentation.presenter.BasePresenter implements Presenter {

    String TAG = "HackerCommentListPresenter";

    private HackerCommentListView view;
    private Usecase getHackerNewCommentDetail;

    List<HNComment> items;

    private final int DEFAULT_PAGE = 1;
    private int page = DEFAULT_PAGE;
    public boolean isFresh = false;
    PaginatorEntity paginatorEntity;

    int id;
    HNStory story;

    public List<HNComment> getItems() {
        return items;
    }

    @Inject
    public HackerCommentListPresenter(@Named("getHackerNewCommentDetail") Usecase getHackerNewCommentDetail,
                                      @NonNull Realm realm) {
        super(realm);
        this.getHackerNewCommentDetail = getHackerNewCommentDetail;
    }

    public void initData(int id, HNStory story) {
        items = new ArrayList<>();
        this.id = id;
        this.story = story;
//        items = getItemsFromRealm();
        paginatorEntity = new PaginatorEntity(DEFAULT_PAGE);
    }


    public void setView(@NonNull HackerCommentListView view) {
        this.view = view;
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.view.context(),
                errorBundle.getException());
        view.hideLoading();
        this.view.showError(errorMessage);
    }

    private void showErrorMessage(String errorMessage) {
        view.hideLoading();
        this.view.showError(errorMessage);
    }

    public void getCommentDetail(boolean isRefresh, int level, int id, int position) {
        ((GetHackerNewCommentDetail) getHackerNewCommentDetail)
                .init(isRefresh, String.valueOf(id)).execute(new SubscriberDataDetailStory(Object.class, level, position));
    }

    private class SubscriberDataDetailStory<T> extends DefaultSubscriber<Object> {

        Class<T> classOfT;
        int level;
        int position;

        SubscriberDataDetailStory(Class<T> classOfT, int level, int position) {
            this.classOfT = classOfT;
            this.level = level;
            this.position = position;
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            Log.d("Trong", "onCompleted");
            this.unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.d("Trong", "onError");
            if (e instanceof HttpException) {
                ResponseBody body = ((HttpException) e).response().errorBody();
                showErrorMessage(new DefaultErrorBundle(body));
            } else {
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }
        }

        @Override
        public void onNext(Object object) {
            super.onNext(object);
            if (object != null) {
                // Get data
                String strData = ClientApi.getGson().toJson(object);
                HNComment data = ClientApi.getGson().fromJson(strData, new TypeToken<HNComment>() {
                }.getType());

                replaceData(data, level, position);

                view.hideLoading();
            }
        }
    }

    private void replaceData(HNComment data, int level, int position) {

        synchronized (this) {

            if (data != null) {
                data.setLevel(level);
                data.setAvailable(true);

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getId() == data.getId()) {
                        Functions.showLogMessage(TAG, "found + " + data.getId());
                        items.get(i).setAvailable(true);
                        items.get(i).setKids(data.getKids());
                        items.get(i).setText(data.getText());
                        items.get(i).setBy(data.getBy());
                        items.get(i).setTime(data.getTime());
//                        items.add(i, data);
                        initSubCommentList(i, data.getKids(), level + 1);
                        break;
                    }
                }
            }
        }
    }

    public void initSubCommentList(int position_parent, List<Integer> kids, int level) {
        if (kids != null && kids.size() > 0) {
            for (int i = 0; i < kids.size(); i++) {
                if(!isAdded(kids.get(i))) {
                    items.add(position_parent + 1 + i, new HNComment(kids.get(i), level, false));
                }
            }
            view.returnData(null);
        } else {
            view.updateItemChanged(null, position_parent);
        }
    }

    // TODO: bug here=> work around by check duplicate items
    private boolean isAdded(int id) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }



    public void refresh() {
        page = DEFAULT_PAGE;
        if (paginatorEntity != null) {
            paginatorEntity.setTotal(DEFAULT_PAGE);
        }
        isFresh = true;
        initCommentFirstTime();
    }

    public void loadingMore() {
        page++;
//        getCommentDetail(false, this.id);
    }


    public boolean canLoadMore() {
        if (paginatorEntity != null) {
            return page < Integer.valueOf(paginatorEntity.getLast_page());
        }
        return false;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getHackerNewCommentDetail.unsubscribe();
    }

    public void initCommentFirstTime() {
        if (story != null && story.getKids() != null && story.getKids().size() > 0) {
            items.clear();
            for (int i = 0; i < story.getKids().size(); i++) {
                items.add(new HNComment(story.getKids().get(i), 0, false));
            }
            view.returnData(null);
        }
    }

//    public RealmResults<HNStory> getItemsFromRealm() {
//        items = realm.where(HNStory.class).findAll();
//        return items;
//    }

}
