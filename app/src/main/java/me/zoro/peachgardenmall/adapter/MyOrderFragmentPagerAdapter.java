package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.zoro.peachgardenmall.fragment.OrderTabFragment;

/**
 * Created by dengfengdecao on 17/4/9.
 */

public class MyOrderFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private String tabTitles[] = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};

    public MyOrderFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return OrderTabFragment.newInstance();
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
