package concaro.hackernews.app.domain.interactor;

import javax.inject.Inject;

import rx.Observable;
import concaro.hackernews.app.domain.executer.PostExecutionThread;
import concaro.hackernews.app.domain.executer.ThreadExecutor;
import concaro.hackernews.app.domain.interactor.*;
import concaro.hackernews.app.domain.repository.DataRepository;

/**
 * Created by Concaro on 11/4/2016.
 */

public class GetHackerNewsTopStories extends concaro.hackernews.app.domain.interactor.Usecase {

    private DataRepository dataRepository;
    boolean refresh;

    @Inject
    public GetHackerNewsTopStories(
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            DataRepository dataRepository) {
        super(threadExecutor, postExecutionThread);
        this.dataRepository = dataRepository;
    }

    public GetHackerNewsTopStories init(boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    @Override
    protected Observable buildObserverable() {
        return dataRepository.getHackerNewsTopStories(refresh);
    }
}
