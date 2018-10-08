package lsh.framgia.com.androidadvancedemo.githubdemo.repository;

import lsh.framgia.com.androidadvancedemo.githubdemo.model.UsersResponse;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.UserDataSource;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.local.UserLocalDataSource;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.SearchUserAsyncTask;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.UserRemoteDataSource;
import retrofit2.Callback;

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
    public void getUsersViaHttpUrlConnection(String query, SearchUserAsyncTask.OnResponseListener listener) {
        mUserRemoteDataSource.getUsersViaHttpUrlConnection(query, listener);
    }

    @Override
    public void getUsersViaRetrofit(String query, Callback<UsersResponse> callback) {
        mUserRemoteDataSource.getUsersViaRetrofit(query, callback);
    }
}
