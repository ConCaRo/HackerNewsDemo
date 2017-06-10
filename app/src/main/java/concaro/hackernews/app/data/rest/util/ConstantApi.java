package concaro.hackernews.app.data.rest.util;

/**
 * Created by CONCARO on 10/28/2015.
 */
public class ConstantApi {

    public static final String URL = "http://139.59.98.188/"; // DEV
//    public static final String URL = "https://mobile.pocca.co/";    // PRODUCTION


    public static final String BASE_URL = URL + "api/";

    public static final String BASE_FIREBASE_URL = "https://pocca-1162.firebaseio.com/";

    public static final String BASE_HACKERNEWS_URL = "https://hacker-news.firebaseio.com/v0/";

    public static String buildAuthorization(String accessToken) {
        return "Bearer " + accessToken;
    }

    public static final String URL_HELP = URL + "post/help-center";
    public static final String URL_COOKIE_POLICY = URL + "post/cookie-policy";
    public static final String URL_AGREEMENT = URL + "post/user-agreement";
    public static final String URL_PRIVACY_POLICY = URL + "post/privacy-policy";
    public static final String URL_TERMS = URL + "post/terms";

}

