package lsh.framgia.com.androidadvancedemo;

import android.app.Application;

import lsh.framgia.com.androidadvancedemo.githubdemo.Constant;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.ServiceBuilder;

public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ServiceBuilder.init(Constant.GITHUB_BASE_API_URL);
    }

    public static App getInstance() {
        return sInstance;
    }
}
