package com.omottec.demoapp.view.vpFrag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
