package lsh.framgia.com.androidadvancedemo.githubdemo.search;

import java.util.List;

import lsh.framgia.com.androidadvancedemo.githubdemo.model.User;
import lsh.framgia.com.androidadvancedemo.githubdemo.repository.UserRepository;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.SearchUserAsyncTask;

public class GithubPresenter implements GithubContract.Presenter,
        SearchUserAsyncTask.OnResponseListener {

    private GithubContract.View mView;

    private UserRepository mUserRepository;

    public GithubPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(GithubContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getUsers(String query) {
        mUserRepository.getUsers(query, this);
    }

    @Override
    public void onSuccess(List<User> users) {
        mView.searchUserSuccessfully(users);
    }

    @Override
    public void onError(Exception e) {
        mView.searchUserFailed(e);
    }
}
