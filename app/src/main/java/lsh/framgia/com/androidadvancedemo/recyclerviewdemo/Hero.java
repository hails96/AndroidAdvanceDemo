package lsh.framgia.com.androidadvancedemo.recyclerviewdemo;

import android.content.Context;

public class Hero {

    private String mName;
    private int mAvatarResId;

    public static Hero newInstance(Context context, int nameResId, int avatarResId) {
        Hero hero = new Hero();
        hero.setName(context.getString(nameResId));
        hero.setAvatarResId(avatarResId);
        return hero;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getAvatarResId() {
        return mAvatarResId;
    }

    public void setAvatarResId(int avatarResId) {
        this.mAvatarResId = avatarResId;
    }
}
