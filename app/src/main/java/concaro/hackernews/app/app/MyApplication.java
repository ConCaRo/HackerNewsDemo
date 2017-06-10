package concaro.hackernews.app.app;

import android.location.Location;
import android.support.multidex.MultiDexApplication;

import com.barryzhang.temptyview.TEmptyView;
import com.barryzhang.temptyview.TViewUtil;
import com.facebook.stetho.Stetho;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import concaro.hackernews.app.R;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import concaro.hackernews.app.presentation.internal.di.component.ApplicationComponent;
import concaro.hackernews.app.presentation.internal.di.component.DaggerApplicationComponent;
import concaro.hackernews.app.presentation.internal.di.module.ApplicationModule;

public final class MyApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    private Location location;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initFacebookConfiguration();
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        int schemaVersion = 0;
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(schemaVersion)
                .migration(realmMigrations)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        TEmptyView.init(TViewUtil.EmptyViewBuilder.getInstance(this)
                .setShowText(true)
                .setEmptyText("CURRENTLY NOT AVAILABLE")
                .setShowButton(false)
                .setShowIcon(true));

        initializeInjector();
//        Fresco.initialize(this);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initFacebookConfiguration() {
        Permission[] permissions = new Permission[]{
                Permission.EMAIL,
        };
        SimpleFacebookConfiguration
                configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getString(R.string.facebook_app_id))
                .setNamespace(getString(R.string.app_name))
                .setPermissions(permissions)
                .setGraphVersion("v2.5")
                .build();
        SimpleFacebook.setConfiguration(configuration);
    }

    // Example migration adding a new class
    RealmMigration realmMigrations = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();

            if (oldVersion < 1) {
            }
            if (oldVersion < 2) {
            }
            if (oldVersion < 3) {
            }
        }
    };
}