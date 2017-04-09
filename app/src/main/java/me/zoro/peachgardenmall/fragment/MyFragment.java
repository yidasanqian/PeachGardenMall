package me.zoro.peachgardenmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.MyCollectionActivity;
import me.zoro.peachgardenmall.activity.MyOrderActivity;
import me.zoro.peachgardenmall.activity.MyShoppingCartActivity;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class MyFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_avatar)
    CircleImageView mUserAvatar;
    @BindView(R.id.edit_user_info_tv)
    TextView mEditUserInfoTv;
    @BindView(R.id.my_shopping_cart)
    TextView mMyShoppingCart;
    @BindView(R.id.my_orders)
    TextView mMyOrders;
    @BindView(R.id.my_collection)
    TextView mMyCollection;
    @BindView(R.id.vip_central)
    TextView mVipCentral;
    @BindView(R.id.settings)
    TextView mSettings;
    @BindView(R.id.common_questions)
    TextView mCommonQuestions;
    @BindView(R.id.exchange)
    TextView mExchange;
    Unbinder unbinder;

    public static MyFragment newInstance(String s) {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, root);


        return root;
    }


    @OnClick({R.id.user_avatar, R.id.edit_user_info_tv, R.id.my_shopping_cart, R.id.my_orders, R.id.my_collection, R.id.vip_central, R.id.settings, R.id.common_questions, R.id.exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_avatar:
                break;
            case R.id.edit_user_info_tv:
                break;
            case R.id.my_shopping_cart:
                Intent intent = new Intent(getActivity(), MyShoppingCartActivity.class);
                startActivity(intent);
                break;
            case R.id.my_orders:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collection:
                intent = new Intent(getActivity(), MyCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.vip_central:
                break;
            case R.id.settings:
                break;
            case R.id.common_questions:
                break;
            case R.id.exchange:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
