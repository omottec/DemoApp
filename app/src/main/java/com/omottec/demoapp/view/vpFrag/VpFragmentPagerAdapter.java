package com.omottec.demoapp.view.vpFrag;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by omottec on 04/11/2017.
 */

public class VpFragmentPagerAdapter extends FragmentPagerAdapter {
    public VpFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return VpFragment.newInstance("frag" + position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
