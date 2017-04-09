package me.zoro.peachgardenmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;

/**
 * Created by dengfengdecao on 17/4/9.
 */

public class MyOrderFragment extends Fragment {

    @BindView(R.id.my_order_tl)
    TabLayout mMyOrderTl;
    @BindView(R.id.my_order_vp)
    ViewPager mMyOrderVp;
    Unbinder unbinder;

    public static MyOrderFragment newInstance() {

        Bundle args = new Bundle();

        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);
        unbinder = ButterKnife.bind(this, root);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
