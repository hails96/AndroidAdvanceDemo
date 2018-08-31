package lsh.framgia.com.androidadvancedemo.viewpagerdemo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lsh.framgia.com.androidadvancedemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public ViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        setupReferences(view);
        setupViewPager();
        return view;
    }

    private void setupReferences(View view) {
        mViewPager = view.findViewById(R.id.view_pager);
        mTabLayout = view.findViewById(R.id.tab_layout);
    }

    private void setupViewPager() {
        mViewPager.setAdapter(new MainPagerAdapter(getContext(), getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(R.drawable.ic_launcher_foreground);
        }
    }
}
