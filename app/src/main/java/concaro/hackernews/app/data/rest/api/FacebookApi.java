package concaro.hackernews.app.data.rest.api;

import android.util.Log;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

import java.util.List;

import concaro.hackernews.app.data.rest.api.FacebookService;

/**
 * Created by CONCARO on 10/28/2015.
 */
public class FacebookApi implements FacebookService {

    SimpleFacebook mSimpleFacebook;

    public FacebookApi(SimpleFacebook mSimpleFacebook) {
        this.mSimpleFacebook = mSimpleFacebook;
    }

    @Override
    public void login(OnLoginListener onLoginListener) {
        mSimpleFacebook.login(onLoginListener);
    }

    @Override
    public void logout(OnLogoutListener onLogoutListener) {
        mSimpleFacebook.logout(onLogoutListener);
    }

    @Override
    public void getAccountInfo(OnProfileListener onProfileListener) {
        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
        pictureAttributes.setHeight(500);
        pictureAttributes.setWidth(500);
        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

        Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.FIRST_NAME)
                .add(Profile.Properties.LAST_NAME)
                .add(Profile.Properties.EMAIL)
                .add(Profile.Properties.NAME)
                .add(Profile.Properties.AGE_RANGE)
                .add(Profile.Properties.PICTURE, pictureAttributes)
                .build();

        mSimpleFacebook.getProfile(properties, onProfileListener);
    }

    @Override
    public void getUserInfo() {

    }

     public OnLoginListener onLoginListener = new OnLoginListener() {

        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
            // change the state of the button or do whatever you want
            Log.i("Trong", "Logged in");
        }

        @Override
        public void onCancel() {
            // user canceled the dialog
        }

        @Override
        public void onFail(String reason) {
            // failed to register
        }

        @Override
        public void onException(Throwable throwable) {
            // exception from facebook
        }

    };

    public OnLogoutListener onLogoutListener = new OnLogoutListener() {
        @Override
        public void onLogout() {

        }
    };

    public OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile response) {

        }
    };
}
