package lsh.framgia.com.androidadvancedemo.githubdemo.source.remote;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static ServiceBuilder sInstance;
    private Retrofit mRetrofit;
    private ApiService mApiService;
    private String mBaseUrl;

    private ServiceBuilder(String endPoint) {
        mBaseUrl = endPoint;
    }

    public static ServiceBuilder getInstance() {
        return sInstance;
    }

    public static void init(String endPoint) {
        sInstance = new ServiceBuilder(endPoint);
    }

    private Retrofit getRetrofit(String endPoint) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(endPoint)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }

        return mRetrofit;
    }

    public ApiService getService() {
        if (mApiService == null) {
            mApiService = getRetrofit(mBaseUrl).create(ApiService.class);
        }

        return mApiService;
    }

    public ApiService getService(String endPoint) {
        if (mApiService == null) {
            mApiService = getRetrofit(endPoint).create(ApiService.class);
        }

        return mApiService;
    }
}
