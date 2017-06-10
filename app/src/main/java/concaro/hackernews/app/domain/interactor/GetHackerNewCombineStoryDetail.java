package concaro.hackernews.app.domain.interactor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.FuncN;
import concaro.hackernews.app.domain.executer.PostExecutionThread;
import concaro.hackernews.app.domain.executer.ThreadExecutor;
import concaro.hackernews.app.domain.interactor.*;
import concaro.hackernews.app.domain.repository.DataRepository;
import concaro.hackernews.app.presentation.presenter.HackerNewListStoryPresenter;


/**
 * Created by Concaro on 11/4/2016.
 */

public class GetHackerNewCombineStoryDetail extends concaro.hackernews.app.domain.interactor.Usecase {

    private DataRepository dataRepository;
    boolean refresh;
    List<Integer> data;
    String page;

    @Inject
    public GetHackerNewCombineStoryDetail(
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            DataRepository dataRepository) {
        super(threadExecutor, postExecutionThread);
        this.dataRepository = dataRepository;
    }

    public GetHackerNewCombineStoryDetail init(boolean refresh, List<Integer> data, String page) {
        this.refresh = refresh;
        this.data = data;
        this.page = page;
        return this;
    }

    @Override
    protected Observable buildObserverable() {
        List<Observable<Object>> observables = new ArrayList<>();
        for (int i = (Integer.valueOf(page) - 1) * HackerNewListStoryPresenter.ITEM_PER_PAGE; i < (Integer.valueOf(page) * HackerNewListStoryPresenter.ITEM_PER_PAGE); i++) {
            observables.add(dataRepository.getHackerNewStoryDetail(refresh, String.valueOf(data.get(i))));
        }

        return Observable.zip(observables, new FuncN<Object>() {
            @Override
            public Object call(Object... args) {
                return args;
            }
        });
    }
}
