package concaro.hackernews.app.presentation.view;

import concaro.hackernews.app.presentation.model.HNComment;
import concaro.hackernews.app.presentation.view.*;

/**
 * Created by Concaro on 11/7/2016.
 */

public interface HackerCommentListView extends concaro.hackernews.app.presentation.view.View {

    void returnData(HNComment data);

    void updateItemChanged(HNComment data, int position);

}
