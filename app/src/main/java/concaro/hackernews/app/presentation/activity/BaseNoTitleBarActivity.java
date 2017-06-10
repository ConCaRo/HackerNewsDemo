package concaro.hackernews.app.presentation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import butterknife.ButterKnife;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import concaro.hackernews.app.presentation.navigation.Navigator;
import concaro.hackernews.app.presentation.util.Functions;
import concaro.hackernews.app.presentation.util.ProgressDialogListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by Concaro on 12/29/2015.
 */
public abstract class BaseNoTitleBarActivity extends ActivityManagePermission implements ProgressDialogListener {

    public Navigator navigator;

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
//    @BindView(R.id.tvToolbarTitle)
//    TextView tvToolbarTitle;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        navigator = new Navigator();
        Functions.hideSoftKeyboard(this);
    }

    @NonNull
    public abstract Integer getLayout();

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private ProgressDialog progress_dialog;

    @Override
    public void showProgressDialog() {
        if (progress_dialog == null) {
            progress_dialog = new ProgressDialog(this);
        }

        if (!progress_dialog.isShowing()) {
            progress_dialog.setMessage("Loading");
            progress_dialog.setCancelable(false);
            progress_dialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progress_dialog != null && progress_dialog.isShowing()) {
            progress_dialog.dismiss();
        }
    }

}
