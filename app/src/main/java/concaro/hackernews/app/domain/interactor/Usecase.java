package concaro.hackernews.app.domain.interactor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import concaro.hackernews.app.domain.executer.PostExecutionThread;
import concaro.hackernews.app.domain.executer.ThreadExecutor;

/**
 * Created by Concaro on 11/4/2016.
 */

public abstract class Usecase {

    private PostExecutionThread postExecutionThread;
    private ThreadExecutor threadExecutor;

    private Subscription subscription = Subscriptions.empty();

    public Usecase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    protected abstract Observable buildObserverable();

    //    Execute the current use case
    public void execute(Subscriber subscriber) {
        this.subscription = this.buildObserverable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(subscriber);
    }


    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
