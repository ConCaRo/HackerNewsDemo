package concaro.hackernews.app.presentation.internal.di.component;

import dagger.Component;
import concaro.hackernews.app.presentation.fragment.HackerCommentListFragment;
import concaro.hackernews.app.presentation.internal.di.PerActivity;
import concaro.hackernews.app.presentation.internal.di.component.*;
import concaro.hackernews.app.presentation.internal.di.component.ActivityComponent;
import concaro.hackernews.app.presentation.internal.di.module.ActivityModule;
import concaro.hackernews.app.presentation.internal.di.module.HackerCommentListModule;

/**
 * Created by Concaro on 11/7/2016.
 */

@PerActivity
@Component(dependencies = concaro.hackernews.app.presentation.internal.di.component.ApplicationComponent.class, modules = {ActivityModule.class, HackerCommentListModule.class})
public interface HackerCommentListComponent extends ActivityComponent {

    void inject(HackerCommentListFragment fragment);
}
