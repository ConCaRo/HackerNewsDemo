package concaro.hackernews.app.data.rest.api;

import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

/**
 * Created by CONCARO on 10/27/2015.
 */
public interface FacebookService {

    void login(OnLoginListener onLoginListener);

    void logout(OnLogoutListener onLogoutListener);

    void getAccountInfo(OnProfileListener onProfileListener);

    void getUserInfo();
}
