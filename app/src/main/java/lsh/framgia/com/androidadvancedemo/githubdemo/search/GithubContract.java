package lsh.framgia.com.androidadvancedemo.githubdemo.search;

import java.util.List;

import lsh.framgia.com.androidadvancedemo.githubdemo.base.IPresenter;
import lsh.framgia.com.androidadvancedemo.githubdemo.base.IView;
import lsh.framgia.com.androidadvancedemo.githubdemo.model.User;

public class GithubContract {
    interface View extends IView<Presenter> {
        void searchUserSuccessfully(List<User> users);

        void searchUserFailed(Exception e);
    }

    interface Presenter extends IPresenter<View> {
        void getUsers(String query);
    }
}
