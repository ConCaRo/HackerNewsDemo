package concaro.hackernews.app.data.rest.api;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import concaro.hackernews.app.data.rest.util.ConstantApi;
import concaro.hackernews.app.data.rest.util.Constants;
import concaro.hackernews.app.presentation.model.realmobject.IntegerRealm;
import concaro.hackernews.app.presentation.model.realmobject.MyMap;
import concaro.hackernews.app.presentation.util.MapDeserializerDoubleAsIntFix;

/**
 * Created by CONCARO on 10/28/2015.
 */
public class BaseApi {

    Context context;

    public BaseApi(Context context) {
        this.context = context;
    }

    private Retrofit retrofitAdapter;
    private Retrofit retrofitAdapterFirebase;
    private Retrofit retrofitHackerNewsService;
    private static Gson gson;
    private static Type tokenInt;
    private static Type tokenMap;

    public static Gson getGson() {
        if (gson == null) {
            tokenInt = new TypeToken<RealmList<IntegerRealm>>() {}.getType();
            tokenMap = new TypeToken<RealmList<MyMap>>() {}.getType();
            gson = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                    }.getType(), new MapDeserializerDoubleAsIntFix())
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .registerTypeAdapter(tokenInt, new TypeAdapter<RealmList<IntegerRealm>>() {

                        @Override
                        public void write(JsonWriter out, RealmList<IntegerRealm> value) throws IOException {
                            // Ignore
                        }

                        @Override
                        public RealmList<IntegerRealm> read(JsonReader in) throws IOException {
                            RealmList<IntegerRealm> list = new RealmList<IntegerRealm>();
                            in.beginArray();
                            while (in.hasNext()) {
                                list.add(new IntegerRealm(in.nextInt()));
                            }
                            in.endArray();
                            return list;
                        }
                    })
//                    .registerTypeAdapter(tokenMap, new TypeAdapter<RealmList<MyMap>>() {
//
//                        @Override
//                        public void write(JsonWriter out, RealmList<MyMap> value) throws IOException {
//                            // Ignore
//                        }
//
//                        @Override
//                        public RealmList<MyMap> read(JsonReader in) throws IOException {
//                            // https://stackoverflow.com/questions/16377754/parse-json-file-using-gson
//                            // "super_attribute":{
//                            //    "173":"13",
//                            //     "92":"17"
//                            // }
//                            RealmList<MyMap> myMap = new RealmList<MyMap>();
//                            in.beginObject();
//                            try {
//                                while (in.hasNext()) {
//                                    String key = in.nextName();
//                                    String value = in.nextString();
//                                    myMap.add(new MyMap(key, value));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            in.endObject();
//                            return myMap;
//                        }
//                    })
                    .create();
        }
        return gson;
    }

    public BaseApi() {
        gson = getGson();
        try {
            retrofitAdapter = new Retrofit.Builder()
                    .baseUrl(ConstantApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();

            retrofitAdapterFirebase = new Retrofit.Builder()
                    .baseUrl(ConstantApi.BASE_FIREBASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Retrofit getRetrofitAdapter() {
        return retrofitAdapter;
    }

    public <T> T getService(Class<T> cls) {
        if (getRetrofitAdapter() == null) {
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();
            try {
                retrofitAdapter = new Retrofit.Builder()
                        .baseUrl(ConstantApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                        .client(getOkHttpClient())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getRetrofitAdapter().create(cls);
    }

    private Retrofit getRetrofitAdapterFirebase() {
        return retrofitAdapterFirebase;
    }

    public <T> T getFirebaseService(Class<T> cls) {
        if (getRetrofitAdapterFirebase() == null) {
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();
            try {
                retrofitAdapterFirebase = new Retrofit.Builder()
                        .baseUrl(ConstantApi.BASE_FIREBASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(getOkHttpClient())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getRetrofitAdapterFirebase().create(cls);
    }

    private Retrofit getRetrofitHackerNewsService() {
        return retrofitHackerNewsService;
    }

    public <T> T getHackerNewsService(Class<T> cls) {
        if (getRetrofitHackerNewsService() == null) {
            Gson gson = getGson();

            try {
                retrofitHackerNewsService = new Retrofit.Builder()
                        .baseUrl(ConstantApi.BASE_HACKERNEWS_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(getOkHttpClient())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getRetrofitHackerNewsService().create(cls);
    }


    private OkHttpClient getOkHttpClient() throws KeyManagementException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(20);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.NANOSECONDS)
                .connectTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .dispatcher(dispatcher)
//                .sslSocketFactory(getSSLSocketFactory())
                .build();
        return okHttpClient;
    }

    private OkHttpClient getOkHttpClientSSL(Context context) throws KeyManagementException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.NANOSECONDS)
                .connectTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
//                .sslSocketFactory(getSSLSocketFactory(context))
                .build();
        return okHttpClient;
    }


//    private SSLSocketFactory getSSLSocketFactory(Context context)
//            throws CertificateException, KeyStoreException, IOException,
//            NoSuchAlgorithmException, KeyManagementException {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        InputStream XmlFileInputStream = context.getResources().openRawResource(R.raw.cert); // getting XML
//        java.security.cert.Certificate ca = cf.generateCertificate(XmlFileInputStream);
//        XmlFileInputStream.close();
//        KeyStore keyStore = KeyStore.getInstance("BKS");
//        keyStore.load(null, null);
//        keyStore.setCertificateEntry("ca", ca);
//
//        // creating a TrustManager that trusts the CAs in our KeyStore
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        // creating an SSLSocketFactory that uses our TrustManager
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);
//
//        return sslContext.getSocketFactory();
//    }

}
