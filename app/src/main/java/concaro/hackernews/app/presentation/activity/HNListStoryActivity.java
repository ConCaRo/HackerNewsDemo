package concaro.hackernews.app.presentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import concaro.hackernews.app.R;
import concaro.hackernews.app.presentation.activity.BaseNoTitleBarActivity;
import concaro.hackernews.app.presentation.fragment.HackerNewListStoryFragment;
import concaro.hackernews.app.presentation.util.Constants;


/**
 * Created by Concaro on 12/30/2015.
 */
public class HNListStoryActivity extends BaseNoTitleBarActivity {

//    public static final String PARAM_ADDRESS_ID = "address_id";
//
//    public static Intent getCallingIntent(Context context, int address_id) {
//        return new Intent(context, HNListStoryActivity.class).putExtra(PARAM_ADDRESS_ID, address_id);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, HackerNewListStoryFragment.newInstance())
                    .commit();
        }
    }

    @NonNull
    @Override
    public Integer getLayout() {
        return R.layout.container_no_tollbar;
    }

    public void initToolbar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        if (getIntent().getExtras() != null && !TextUtils.isEmpty(getIntent().getExtras().getString(Constants.INTENT_TITLE))) {
            getSupportActionBar().setTitle(getIntent().getExtras().getString(Constants.INTENT_TITLE));
        } else {
            getSupportActionBar().setTitle("Top Stories");
        }
    }

    public HackerNewListStoryFragment getFragment() {
        return (HackerNewListStoryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            HackerNewListStoryFragment fragment = getFragment();
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