package concaro.hackernews.app.presentation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import concaro.hackernews.app.R;
import concaro.hackernews.app.app.MyApplication;
import concaro.hackernews.app.presentation.internal.di.component.ApplicationComponent;
import concaro.hackernews.app.presentation.internal.di.module.ActivityModule;
import concaro.hackernews.app.presentation.navigation.Navigator;
import concaro.hackernews.app.presentation.util.Functions;
import concaro.hackernews.app.presentation.util.ProgressDialogListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by Concaro on 12/29/2015.
 */
public abstract class BaseActivity extends ActivityManagePermission implements ProgressDialogListener {

    public Navigator navigator;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getApplicationComponent().inject(this);
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

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

//    /**
//     * Gets a component for dependency injection by its type.
//     */
//    @SuppressWarnings("unchecked")
//    protected <C> C getComponent(Class<C> componentType) {
//        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
//    }

}
