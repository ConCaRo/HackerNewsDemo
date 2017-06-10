package concaro.hackernews.app.presentation.internal.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import concaro.hackernews.app.domain.executer.PostExecutionThread;
import concaro.hackernews.app.domain.executer.ThreadExecutor;
import concaro.hackernews.app.domain.interactor.GetHackerNewCommentDetail;
import concaro.hackernews.app.domain.interactor.Usecase;
import concaro.hackernews.app.domain.repository.DataRepository;
import concaro.hackernews.app.presentation.internal.di.PerActivity;

/**
 * Created by Concaro on 11/7/2016.
 */

@Module
public class HackerCommentListModule {

    public HackerCommentListModule() {
    }

    @Provides
    @PerActivity
    @Named("getHackerNewCommentDetail")
    Usecase provideGetHackerNewCommentDetail(DataRepository dataRepository, ThreadExecutor threadExecutor,
                                           PostExecutionThread postExecutionThread) {
        return new GetHackerNewCommentDetail(threadExecutor, postExecutionThread, dataRepository);
    }

}
