package lsh.framgia.com.androidadvancedemo.githubdemo.source.remote;

import lsh.framgia.com.androidadvancedemo.githubdemo.source.UserDataSource;
import lsh.framgia.com.androidadvancedemo.utils.StringUtils;

public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {

    private static UserRemoteDataSource sInstance;

    private UserRemoteDataSource() {
    }

    public static synchronized UserRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UserRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getUsers(String query, SearchUserAsyncTask.OnResponseListener listener) {
        new SearchUserAsyncTask(listener).execute(StringUtils.formatSearchUrl(query));
    }
}
