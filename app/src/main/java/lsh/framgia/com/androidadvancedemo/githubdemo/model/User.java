package lsh.framgia.com.androidadvancedemo.githubdemo.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String mId;
    @SerializedName("login")
    private String mName;
    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("score")
    private double mScore;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public double getScore() {
        return mScore;
    }

    public void setScore(double score) {
        mScore = score;
    }
}
