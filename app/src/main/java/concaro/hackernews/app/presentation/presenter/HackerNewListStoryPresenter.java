package concaro.hackernews.app.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
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
import concaro.hackernews.app.domain.interactor.GetHackerNewCombineStoryDetail;
import concaro.hackernews.app.domain.interactor.GetHackerNewStoryDetail;
import concaro.hackernews.app.domain.interactor.GetHackerNewsTopStories;
import concaro.hackernews.app.domain.interactor.Usecase;
import concaro.hackernews.app.presentation.exception.ErrorMessageFactory;
import concaro.hackernews.app.presentation.internal.di.PerActivity;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.model.PaginatorEntity;
import concaro.hackernews.app.presentation.presenter.*;
import concaro.hackernews.app.presentation.presenter.Presenter;
import concaro.hackernews.app.presentation.view.HackerNewListStoryView;

/**
 * Created by Concaro on 11/7/2016.
 */

@PerActivity
public class HackerNewListStoryPresenter extends concaro.hackernews.app.presentation.presenter.BasePresenter implements Presenter {

    public static int ITEM_PER_PAGE = 10;

    private HackerNewListStoryView view;
    private Usecase getHackerNewStoryDetail;
    private Usecase getHackerNewsTopStories;
    private Usecase getHackerNewCombineStoryDetail;

    List<HNStory> items;
    List<Integer> data;

    private final int DEFAULT_PAGE = 1;
    private int page = DEFAULT_PAGE;
    public boolean isFresh = false;
    PaginatorEntity paginatorEntity;


    public List<HNStory> getItems() {
        return items;
    }

    @Inject
    public HackerNewListStoryPresenter(@Named("getHackerNewStoryDetail") Usecase getHackerNewStoryDetail,
                                       @Named("getHackerNewsTopStories") Usecase getHackerNewsTopStories,
                                       @Named("getHackerNewCombineStoryDetail") Usecase getHackerNewCombineStoryDetail,
                                       @NonNull Realm realm) {
        super(realm);
        this.getHackerNewStoryDetail = getHackerNewStoryDetail;
        this.getHackerNewsTopStories = getHackerNewsTopStories;
        this.getHackerNewCombineStoryDetail = getHackerNewCombineStoryDetail;
    }

    public void initData() {
        items = new ArrayList<>();
//        items = getItemsFromRealm();
        paginatorEntity = new PaginatorEntity(DEFAULT_PAGE);
    }


    public void setView(@NonNull HackerNewListStoryView view) {
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

    public void getListStory(boolean isRefresh) {
        ((GetHackerNewsTopStories) getHackerNewsTopStories)
                .init(isRefresh).execute(new SubscriberData(Object.class));
    }

    public void getStoryDetail(boolean isRefresh, String id) {
        ((GetHackerNewStoryDetail) getHackerNewStoryDetail)
                .init(isRefresh, id).execute(new SubscriberDataDetailStory(Object.class));
    }

    public void getListStoryDetail() {
        ((GetHackerNewCombineStoryDetail) getHackerNewCombineStoryDetail)
                .init(false, data, String.valueOf(page)).execute(new SubscriberDataCombineStoryDetail(Object.class));
    }

    private class SubscriberData<T> extends DefaultSubscriber<Object> {

        Class<T> classOfT;

        SubscriberData(Class<T> classOfT) {
            this.classOfT = classOfT;
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            Log.d("Trong", "onCompleted");
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
                data = ClientApi.getGson().fromJson(strData, new TypeToken<List<Integer>>() {
                }.getType());
                if (isFresh || page == DEFAULT_PAGE) {
                    items.clear();
                }
                isFresh = false;
                if (paginatorEntity != null) {
                    paginatorEntity.setLast_page((int) Math.floor(data.size() / ITEM_PER_PAGE));
                }
                loadListStoryDetail();
//                getItemsFromRealm();
//                view.returnData(data);
                view.hideLoading();
            }
        }
    }

    int count_item_load_more = 0;

    private class SubscriberDataDetailStory<T> extends DefaultSubscriber<Object> {

        Class<T> classOfT;

        SubscriberDataDetailStory(Class<T> classOfT) {
            this.classOfT = classOfT;
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            Log.d("Trong", "onCompleted");
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
                HNStory data = ClientApi.getGson().fromJson(strData, new TypeToken<HNStory>() {
                }.getType());
                if (data != null) {
                    count_item_load_more++;
                    int positionItem = getPositionItem(data.getId());
                    if (positionItem != -1) {
                        items.set(positionItem, data);
                    }
                }
                if (ITEM_PER_PAGE == count_item_load_more) {
                    view.returnData(items);
                }
                view.hideLoading();
            }
        }
    }

    private int getPositionItem(int id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == id) {
                return i;
            }
        }
        return -1;
    }


    private class SubscriberDataCombineStoryDetail<T> extends DefaultSubscriber<Object> {

        Class<T> classOfT;

        SubscriberDataCombineStoryDetail(Class<T> classOfT) {
            this.classOfT = classOfT;
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            Log.d("Trong", "onCompleted");
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
                HNStory[] data = ClientApi.getGson().fromJson(strData, new TypeToken<HNStory[]>() {
                }.getType());
                if (data != null) {
                    items.addAll(Arrays.asList(data));
                }
                view.returnData(items);
                view.hideLoading();
            }
        }
    }

    public void refresh() {
        page = DEFAULT_PAGE;
        if (paginatorEntity != null) {
            paginatorEntity.setTotal(DEFAULT_PAGE);
        }
        isFresh = true;
        getListStory(true);
    }

    public void loadingMore() {
        page++;
//        getListStory(false);

        loadListStoryDetail();
    }

    public void loadListStoryDetail() {
//        getListStoryDetail();
        count_item_load_more = 0;
        for (int i = 0; i < ITEM_PER_PAGE; i++) {
            items.add(new HNStory());
        }
        for (int i = (Integer.valueOf(page) - 1) * HackerNewListStoryPresenter.ITEM_PER_PAGE; i < (Integer.valueOf(page) * HackerNewListStoryPresenter.ITEM_PER_PAGE); i++) {
            getStoryDetail(false, String.valueOf(data.get(i)));
        }
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
        this.getHackerNewsTopStories.unsubscribe();
        this.getHackerNewStoryDetail.unsubscribe();
    }

//    public RealmResults<HNStory> getItemsFromRealm() {
//        items = realm.where(HNStory.class).findAll();
//        return items;
//    }

}
