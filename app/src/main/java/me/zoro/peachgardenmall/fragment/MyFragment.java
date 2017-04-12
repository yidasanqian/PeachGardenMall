package me.zoro.peachgardenmall.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.CommonQuestionActivity;
import me.zoro.peachgardenmall.activity.EditUserInfoActivity;
import me.zoro.peachgardenmall.activity.LoginActivity;
import me.zoro.peachgardenmall.activity.MyCollectionActivity;
import me.zoro.peachgardenmall.activity.MyOrderActivity;
import me.zoro.peachgardenmall.activity.MyShoppingCartActivity;
import me.zoro.peachgardenmall.activity.SettingsActivity;
import me.zoro.peachgardenmall.activity.VipActivity;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.utils.PreferencesUtil;
import me.zoro.peachgardenmall.view.RichText;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class MyFragment extends Fragment {
    public static final int LOGIN_REQUEST_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_avatar)
    CircleImageView mUserAvatar;
    @BindView(R.id.edit_user_info_tv)
    RichText mEditUserInfoTv;
    @BindView(R.id.my_shopping_cart)
    RichText mMyShoppingCart;
    @BindView(R.id.my_orders)
    RichText mMyOrders;
    @BindView(R.id.my_collection)
    RichText mMyCollection;
    @BindView(R.id.vip_central)
    RichText mVipCentral;
    @BindView(R.id.settings)
    RichText mSettings;
    @BindView(R.id.common_questions)
    RichText mCommonQuestions;
    Unbinder unbinder;
    @BindView(R.id.tv_login)
    TextView mTvLogin;

    private OnFragmentInteractionListener mListener;

    private UserInfo mUserInfo;
    private String mToken;

    public static MyFragment newInstance(String s) {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(mToken)) {
            mToken = PreferencesUtil.getDefaultPreferences(getContext(), Const.PREF_TOKEN)
                    .getString(Const.TOKEN_KEY, null);
        }

        if (mUserInfo == null) {
            String userStr = PreferencesUtil.getDefaultPreferences(getContext(), Const.PREF_USER_INFO)
                    .getString(Const.USERINFO_KEY, null);
            if (userStr != null) {
                mUserInfo = new GsonBuilder().setLenient().create().fromJson(userStr, UserInfo.class);
            }
        }

        updateUserInfo(mUserInfo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, root);

        // 如果用户未登录，则显示"请登录"按钮
        if (mUserInfo == null) {
            mTvLogin.setVisibility(View.VISIBLE);
        }

        return root;
    }


    @OnClick({R.id.user_avatar, R.id.edit_user_info_tv, R.id.my_shopping_cart, R.id.my_orders,
            R.id.my_collection, R.id.vip_central, R.id.settings, R.id.common_questions
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_avatar:
                break;
            case R.id.edit_user_info_tv:
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.my_shopping_cart:
                intent = new Intent(getActivity(), MyShoppingCartActivity.class);
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
                intent = new Intent(getActivity(), VipActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.common_questions:
                intent = new Intent(getActivity(), CommonQuestionActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            UserInfo userInfo = (UserInfo) data.getSerializableExtra(LoginActivity.USERINFO_EXTRA);
            updateUserInfo(userInfo);
        }
    }

    public interface OnFragmentInteractionListener {
        void onUserInfoLoaded(UserInfo userInfo);
    }

    /**
     * 更新用户信息
     *
     * @param userInfo
     */
    public void updateUserInfo(UserInfo userInfo) {
        if (mListener != null) {
            mListener.onUserInfoLoaded(userInfo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
