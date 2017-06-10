package concaro.hackernews.app.presentation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.activity.BaseNoTitleBarActivity;
import concaro.hackernews.app.presentation.fragment.HackerCommentListFragment;
import concaro.hackernews.app.presentation.model.HNStory;
import concaro.hackernews.app.presentation.util.Constants;


/**
 * Created by Concaro on 12/30/2015.
 */
public class HNListCommentActivity extends BaseNoTitleBarActivity {

    public static final String PARAM_STORY_ID = "id";

    public static Intent getCallingIntent(Context context, int story_id, HNStory hnStory) {
        return new Intent(context, HNListCommentActivity.class).putExtra(PARAM_STORY_ID, story_id).putExtra("story", hnStory);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, HackerCommentListFragment.newInstance())
                    .commit();
        }
    }

    public void initToolbar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if (getIntent().getExtras() != null && !TextUtils.isEmpty(getIntent().getExtras().getString(Constants.INTENT_TITLE))) {
            getSupportActionBar().setTitle(getIntent().getExtras().getString(Constants.INTENT_TITLE));
        } else {
            getSupportActionBar().setTitle("Comments");
        }
    }

    @NonNull
    @Override
    public Integer getLayout() {
        return R.layout.container_no_tollbar;
    }

    public HackerCommentListFragment getFragment() {
        return (HackerCommentListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            HackerCommentListFragment fragment = getFragment();
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}