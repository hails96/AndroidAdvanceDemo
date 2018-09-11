package lsh.framgia.com.androidadvancedemo.githubdemo.source.remote;

import android.os.AsyncTask;

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

import lsh.framgia.com.androidadvancedemo.githubdemo.Constant;
import lsh.framgia.com.androidadvancedemo.githubdemo.model.User;

public class SearchUserAsyncTask extends AsyncTask<String, Void, List<User>> {

    private OnResponseListener mOnResponseListener;

    public SearchUserAsyncTask(OnResponseListener onResponseListener) {
        mOnResponseListener = onResponseListener;
    }

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
            if (mOnResponseListener != null) mOnResponseListener.onError(e);
        }
        return users;
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        if (mOnResponseListener != null) mOnResponseListener.onSuccess(users);
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

    public interface OnResponseListener {
        void onSuccess(List<User> users);

        void onError(Exception e);
    }
}
