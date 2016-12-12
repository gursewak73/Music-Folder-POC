package com.musicfolderpoc;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Gursewak on 12/6/2016.
 */

public class NavigatorPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
