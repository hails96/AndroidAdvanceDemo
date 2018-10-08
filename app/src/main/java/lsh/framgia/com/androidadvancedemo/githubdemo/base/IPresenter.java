package lsh.framgia.com.androidadvancedemo.githubdemo.base;

public interface IPresenter<V> {

    void setView(V view);

    void onStart();

    void onStop();
}
