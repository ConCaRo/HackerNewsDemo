/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package concaro.hackernews.app.presentation.internal.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import concaro.hackernews.app.domain.executer.PostExecutionThread;
import concaro.hackernews.app.domain.executer.ThreadExecutor;
import concaro.hackernews.app.domain.repository.DataRepository;
import concaro.hackernews.app.presentation.activity.BaseActivity;
import concaro.hackernews.app.presentation.internal.di.module.ApplicationModule;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    //java.lang.IllegalStateException: Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.
    // Provide Realm instance no effect in this case(using with rx)
    Realm realm();

    DataRepository dataRepository();

}
