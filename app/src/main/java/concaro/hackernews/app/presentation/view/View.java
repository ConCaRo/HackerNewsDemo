package concaro.hackernews.app.presentation.view;

import android.content.Context;

/**
 * Created by Concaro on 11/7/2016.
 */

public interface View<T> {

//    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

    void showMessage(String message);

    Context context();
}
