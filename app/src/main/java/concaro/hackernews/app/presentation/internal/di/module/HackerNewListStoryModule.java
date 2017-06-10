package concaro.hackernews.app.presentation.internal.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import concaro.hackernews.app.domain.executer.PostExecutionThread;
import concaro.hackernews.app.domain.executer.ThreadExecutor;
import concaro.hackernews.app.domain.interactor.GetHackerNewCombineStoryDetail;
import concaro.hackernews.app.domain.interactor.GetHackerNewStoryDetail;
import concaro.hackernews.app.domain.interactor.GetHackerNewsTopStories;
import concaro.hackernews.app.domain.interactor.Usecase;
import concaro.hackernews.app.domain.repository.DataRepository;
import concaro.hackernews.app.presentation.internal.di.PerActivity;

/**
 * Created by Concaro on 11/7/2016.
 */

@Module
public class HackerNewListStoryModule {

    public HackerNewListStoryModule() {
    }

    @Provides
    @PerActivity
    @Named("getHackerNewStoryDetail")
    Usecase provideGetHackerNewStoryDetail(DataRepository dataRepository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        return new GetHackerNewStoryDetail(threadExecutor, postExecutionThread, dataRepository);
    }


    @Provides
    @PerActivity
    @Named("getHackerNewsTopStories")
    Usecase provideGetHackerNewsTopStories(DataRepository dataRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
        return new GetHackerNewsTopStories(threadExecutor, postExecutionThread, dataRepository);
    }

    @Provides
    @PerActivity
    @Named("getHackerNewCombineStoryDetail")
    Usecase provideGetHackerNewCombineStoryDetail(DataRepository dataRepository, ThreadExecutor threadExecutor,
                                           PostExecutionThread postExecutionThread) {
        return new GetHackerNewCombineStoryDetail(threadExecutor, postExecutionThread, dataRepository);
    }

}
