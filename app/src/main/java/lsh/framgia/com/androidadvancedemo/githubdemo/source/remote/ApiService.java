package lsh.framgia.com.androidadvancedemo.githubdemo.source.remote;

import lsh.framgia.com.androidadvancedemo.githubdemo.model.UsersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/users")
    Call<UsersResponse> searchUsers(@Query("q") String query);
}
