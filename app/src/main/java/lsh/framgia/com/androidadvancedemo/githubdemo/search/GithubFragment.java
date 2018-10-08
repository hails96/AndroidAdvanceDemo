package lsh.framgia.com.androidadvancedemo.githubdemo.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.githubdemo.adapter.UserAdapter;
import lsh.framgia.com.androidadvancedemo.githubdemo.model.User;
import lsh.framgia.com.androidadvancedemo.githubdemo.source.remote.UserRemoteDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class GithubFragment extends Fragment implements GithubContract.View,
        SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private RecyclerView mRecyclerUser;

    private GithubContract.Presenter mPresenter;
    private UserRemoteDataSource mUserRemoteDataSource;
    private UserAdapter mUserAdapter = new UserAdapter();

    public GithubFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRemoteDataSource = UserRemoteDataSource.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSearchView(view);
        setupRecyclerView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mPresenter.getUsers(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void setPresenter(GithubContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void searchUserSuccessfully(List<User> users) {
        mUserAdapter.setUsers(users);
        mUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchUserFailed(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setupSearchView(View view) {
        mSearchView = view.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);
    }

    private void setupRecyclerView(View view) {
        mRecyclerUser = view.findViewById(R.id.recycler_user);
        mRecyclerUser.setHasFixedSize(true);
        mRecyclerUser.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerUser.setAdapter(mUserAdapter);
    }
}
