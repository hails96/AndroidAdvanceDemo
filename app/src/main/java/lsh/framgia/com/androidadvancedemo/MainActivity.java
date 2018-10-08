package lsh.framgia.com.androidadvancedemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import lsh.framgia.com.androidadvancedemo.githubdemo.search.GithubFragment;
import lsh.framgia.com.androidadvancedemo.githubdemo.search.GithubPresenter;
import lsh.framgia.com.androidadvancedemo.githubdemo.repository.UserRepository;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.local.UserLocalDataSource;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.UserRemoteDataSource;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GithubFragment githubFragment = new GithubFragment();
        GithubPresenter githubPresenter = new GithubPresenter(
                UserRepository.getInstance(UserRemoteDataSource.getInstance(),
                        UserLocalDataSource.getInstance()));
        githubPresenter.setView(githubFragment);
        replaceFragment(githubFragment, false);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }
    }
}
