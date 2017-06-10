package concaro.hackernews.app.presentation.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.ButterKnife;
import permission.auron.com.marshmallowpermissionhelper.FragmentManagePermission;
import concaro.hackernews.app.R;
import concaro.hackernews.app.app.MyApplication;
import concaro.hackernews.app.presentation.internal.di.component.ApplicationComponent;
import concaro.hackernews.app.presentation.internal.di.module.ActivityModule;
import concaro.hackernews.app.presentation.navigation.Navigator;
import concaro.hackernews.app.presentation.util.Functions;
import concaro.hackernews.app.presentation.util.ProgressDialogListener;

/**
 * Created by Concaro on 12/29/2015.
 */
public abstract class BaseFragment extends FragmentManagePermission implements ProgressDialogListener {

    public String getTAG() {
        return TAG;
    }

    public String TAG = "";

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public Navigator navigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        navigator = new Navigator();
        Functions.hideSoftKeyboard(getActivity());
        return view;
    }

    @NonNull
    protected abstract Integer getLayout();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private ProgressDialog progress_dialog;

    @Override
    public void showProgressDialog() {
        if (progress_dialog == null) {
            progress_dialog = new ProgressDialog(getActivity());
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

    public boolean isProgressShowing() {
        if (progress_dialog != null) {
            return progress_dialog.isShowing();
        }
        return false;
    }


    /**
     * Refresh
     */
    public void refresh(RecyclerView recyclerView) {
        if (getArguments().getInt("index", 0) > 0 && recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (getView() != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            getView().startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden() {
        if (getView() != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            getView().startAnimation(fadeOut);
        }
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getActivity().getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(getActivity());
    }

//    /**
//     * Gets a component for dependency injection by its type.
//     */
//    @SuppressWarnings("unchecked")
//    protected <C> C getComponent(Class<C> componentType) {
//        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
//    }


}