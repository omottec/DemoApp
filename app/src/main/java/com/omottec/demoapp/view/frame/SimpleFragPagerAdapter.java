package com.omottec.demoapp.view.frame;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.omottec.demoapp.fragment.SimpleTextFragment;

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
        return SimpleTextFragment.newInstance(mNames.get(position));
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
