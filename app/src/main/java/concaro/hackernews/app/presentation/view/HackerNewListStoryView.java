package concaro.hackernews.app.presentation.view;

import java.util.List;

import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.view.*;

/**
 * Created by Concaro on 11/7/2016.
 */

public interface HackerNewListStoryView extends concaro.hackernews.app.presentation.view.View {

    void returnData(List<HNStory> data);

}
