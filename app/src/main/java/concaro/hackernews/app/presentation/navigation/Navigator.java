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
package concaro.hackernews.app.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import concaro.hackernews.app.presentation.activity.HNListCommentActivity;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.util.Constants;

/**
 * Class used to navigate through the application.
 */
public class Navigator {


    public Navigator() {
        //empty
    }


    public void navigateToHNListCommentActivity(Context context, int story_id, HNStory hnStory, String title) {
        if (context != null) {
            Intent intentToLaunch = HNListCommentActivity.getCallingIntent(context, story_id, hnStory);
            intentToLaunch.putExtra(Constants.INTENT_TITLE, title);
            context.startActivity(intentToLaunch);
        }
    }

}
