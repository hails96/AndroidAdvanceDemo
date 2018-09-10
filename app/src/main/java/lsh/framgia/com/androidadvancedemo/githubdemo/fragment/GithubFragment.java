package lsh.framgia.com.androidadvancedemo.githubdemo.fragment;

import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.githubdemo.Constant;
import lsh.framgia.com.androidadvancedemo.githubdemo.adapter.UserAdapter;
import lsh.framgia.com.androidadvancedemo.githubdemo.model.User;
import lsh.framgia.com.androidadvancedemo.utils.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class GithubFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private RecyclerView mRecyclerUser;
    private UserAdapter mUserAdapter = new UserAdapter();

    public GithubFragment() {
        // Required empty public constructor
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
    public boolean onQueryTextSubmit(String s) {
        new SearchAsyncTask().execute(StringUtils.formatSearchUrl(s));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
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

    private List<User> convertInputStreamToUsers(InputStream inputStream)
            throws IOException, JSONException {
        List<User> users = new ArrayList<>();
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder usersJsonString = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            usersJsonString.append(line).append(Constant.NEW_LINE);
        }
        JSONObject mainObject = new JSONObject(usersJsonString.toString());
        JSONArray userArray = mainObject.getJSONArray(Constant.KEY_LIST_USER);
        for (int i = 0; i < userArray.length(); i++) {
            User user = new User();
            JSONObject userObject = userArray.getJSONObject(i);
            user.setId(userObject.getString(Constant.KEY_USER_ID));
            user.setName(userObject.getString(Constant.KEY_USER_NAME));
            user.setAvatarUrl(userObject.getString(Constant.KEY_USER_AVATAR_URL));
            user.setScore(userObject.getDouble(Constant.KEY_USER_SCORE));
            users.add(user);
        }
        return users;
    }

    private class SearchAsyncTask extends AsyncTask<String, Void, List<User>> {

        @Override
        protected List<User> doInBackground(String... strings) {
            List<User> users = new ArrayList<>();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty(Constant.KEY_CONTENT_TYPE, Constant.VALUE_APP_JSON);
                if (urlConnection.getResponseCode() == Constant.RESPONSE_CODE_OK) {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    users = convertInputStreamToUsers(inputStream);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            if (users == null) return;
            mUserAdapter.setUsers(users);
            mUserAdapter.notifyDataSetChanged();
        }
    }
}
