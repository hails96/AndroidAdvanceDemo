package lsh.framgia.com.androidadvancedemo.githubdemo.source;

import lsh.framgia.com.androidadvancedemo.githubdemo.model.UsersResponse;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.SearchUserAsyncTask;
import retrofit2.Callback;

public interface UserDataSource {

    interface LocalDataSource extends UserDataSource {
    }

    interface RemoteDataSource extends UserDataSource {
        void getUsersViaHttpUrlConnection(String query, SearchUserAsyncTask.OnResponseListener listener);

        void getUsersViaRetrofit(String query, Callback<UsersResponse> callback);
    }
}
