package lsh.framgia.com.androidadvancedemo.githubdemo.source;

import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.SearchUserAsyncTask;

public interface UserDataSource {

    interface LocalDataSource extends UserDataSource {
    }

    interface RemoteDataSource extends UserDataSource {
        void getUsers(String query, SearchUserAsyncTask.OnResponseListener listener);
    }
}
