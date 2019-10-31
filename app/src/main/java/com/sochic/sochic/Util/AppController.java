package com.sochic.sochic.Util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import androidx.multidex.MultiDex;

import com.kakao.auth.KakaoSDK;
import com.sochic.sochic.SplashFolder.MainActivity;
import com.sochic.sochic.Util.UserFolder.UserPreferenceManager;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AppController
        extends Application
{
    private static volatile Activity currentActivity = null;
    private static AppController mInstance;
    private UserPreferenceManager pref;
    private Scheduler scheduler;

    public static AppController create(Context paramContext)
    {
        return get(paramContext);
    }

    private static AppController get(Context paramContext)
    {
        return (AppController)paramContext.getApplicationContext();
    }

    public static AppController getAppControllerContext()
    {
        if (mInstance == null) {
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        }
        return mInstance;
    }

    public static Activity getCurrentActivity()
    {
        return currentActivity;
    }


    public static synchronized AppController getInstance() {
        AppController appController;
        synchronized (AppController.class) {
            if (mInstance == null) {
                mInstance = new AppController();
            }

            appController = mInstance;
        }
        return appController;
    }

    protected void attachBaseContext(Context paramContext)
    {
        super.attachBaseContext(paramContext);
    }

    public UserPreferenceManager getPrefManager()
    {
        if (this.pref == null) {
            this.pref = new UserPreferenceManager(this);
        }
        return this.pref;
    }

    public void logout()
    {
        pref.clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
       // FirebaseApp.initializeApp(getApplicationContext());
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public void setScheduler(Scheduler paramScheduler)
    {
        this.scheduler = paramScheduler;
    }

    public Scheduler subscribeScheduler()
    {
        if (this.scheduler == null) {
            this.scheduler = Schedulers.io();
        }
        return this.scheduler;
    }
}