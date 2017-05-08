package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dengfengdecao on 17/4/9.
 */

public class MyOrderFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mFragments;
    private String tabTitles[] = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};

    public MyOrderFragmentPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        mContext = context;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
