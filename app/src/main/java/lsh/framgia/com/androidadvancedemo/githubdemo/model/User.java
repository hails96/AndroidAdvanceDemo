package lsh.framgia.com.androidadvancedemo.githubdemo.model;

public class User {
    private String mId;
    private String mName;
    private String mAvatarUrl;
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
