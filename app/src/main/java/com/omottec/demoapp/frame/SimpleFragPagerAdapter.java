package com.omottec.demoapp.frame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.omottec.demoapp.fragment.SimpleFragment;

import java.util.List;

/**
 * Created by qinbingbing on 16/03/2017.
 */

public class SimpleFragPagerAdapter extends FragmentPagerAdapter {
    private List<String> mNames;

    public SimpleFragPagerAdapter(FragmentManager fm, List<String> names) {
        super(fm);
        mNames = names;
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleFragment.newInstance(mNames.get(position));
    }

    @Override
    public int getCount() {
        return mNames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNames.get(position);
    }
}
