package team.khonnan.android.miccook;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by quyntg94 on 10/08/2017.
 */

public class CookMixApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
