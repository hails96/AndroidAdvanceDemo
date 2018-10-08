package lsh.framgia.com.androidadvancedemo.githubdemo.source.local;

import lsh.framgia.com.androidadvancedemo.githubdemo.source.UserDataSource;

public class UserLocalDataSource implements UserDataSource.LocalDataSource {

    private static UserLocalDataSource sInstance;

    private UserLocalDataSource() {
    }

    public static synchronized UserLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UserLocalDataSource();
        }
        return sInstance;
    }
}
