package com.omottec.demoapp.frame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.omottec.demoapp.fragment.SimpleFragment;

import java.util.List;

/**
 * Created by qinbingbing on 17/03/2017.
 */

public class SimpleFragStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mNames;

    public SimpleFragStatePagerAdapter(FragmentManager fm, List<String> names) {
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
