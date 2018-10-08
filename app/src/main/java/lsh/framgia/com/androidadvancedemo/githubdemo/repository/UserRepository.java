package lsh.framgia.com.androidadvancedemo.githubdemo.repository;

import lsh.framgia.com.androidadvancedemo.githubdemo.source.UserDataSource;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.local.UserLocalDataSource;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.SearchUserAsyncTask;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.UserRemoteDataSource;

public class UserRepository implements UserDataSource.RemoteDataSource,
        UserDataSource.LocalDataSource {

    private static UserRepository sInstance;
    private UserRemoteDataSource mUserRemoteDataSource;
    private UserLocalDataSource mUserLocalDataSource;

    private UserRepository(UserRemoteDataSource userRemoteDataSource,
                           UserLocalDataSource userLocalDataSource) {
        mUserRemoteDataSource = userRemoteDataSource;
        mUserLocalDataSource = userLocalDataSource;
    }

    public static UserRepository getInstance(UserRemoteDataSource userRemoteDataSource,
                                             UserLocalDataSource userLocalDataSource) {
        if (sInstance == null) {
            sInstance = new UserRepository(userRemoteDataSource, userLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public void getUsers(String query, SearchUserAsyncTask.OnResponseListener listener) {
        mUserRemoteDataSource.getUsers(query, listener);
    }
}
