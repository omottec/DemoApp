package com.omottec.demoapp.view.frame;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.omottec.demoapp.fragment.SimpleTextFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 17/03/2017.
 */

public class SimpleFragStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mNames = new ArrayList<>();

    public SimpleFragStatePagerAdapter(FragmentManager fm, List<String> names) {
        super(fm);
        mNames.addAll(names);
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

    public void setData(List<String> names) {
        mNames.clear();
        mNames.addAll(names);
        notifyDataSetChanged();
    }
}
