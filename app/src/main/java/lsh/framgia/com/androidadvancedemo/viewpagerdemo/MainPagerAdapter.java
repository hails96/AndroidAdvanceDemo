package lsh.framgia.com.androidadvancedemo.viewpagerdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.recyclerviewdemo.HeroFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final int TAB_NUMBER = 3;
    private Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        return new HeroFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(R.string.label_tab_place_holder, (position + 1));
    }

    @Override
    public int getCount() {
        return TAB_NUMBER;
    }
}
