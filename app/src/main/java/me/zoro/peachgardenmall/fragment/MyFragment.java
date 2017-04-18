package me.zoro.peachgardenmall.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.CommonQuestionActivity;
import me.zoro.peachgardenmall.activity.EditUserInfoActivity;
import me.zoro.peachgardenmall.activity.ImageShowerActivity;
import me.zoro.peachgardenmall.activity.LoginActivity;
import me.zoro.peachgardenmall.activity.MyCollectionActivity;
import me.zoro.peachgardenmall.activity.MyOrderActivity;
import me.zoro.peachgardenmall.activity.MyShoppingCartActivity;
import me.zoro.peachgardenmall.activity.SettingsActivity;
import me.zoro.peachgardenmall.activity.VipActivity;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.UserDatasource;
import me.zoro.peachgardenmall.datasource.UserRepository;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.UserRemoteDatasource;
import me.zoro.peachgardenmall.utils.PreferencesUtil;
import me.zoro.peachgardenmall.view.RichText;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class MyFragment extends Fragment {
    private static final String TAG = "MyFragment";

    public static final int LOGIN_REQUEST_CODE = 1;
    public static final String USERINFO_EXTRA = "userinfo";
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

    private UserRepository mUserRepository;

    private OnFragmentInteractionListener mListener;

    private UserInfo mUserInfo;

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(getContext().getApplicationContext()));
        mUserInfo = PreferencesUtil.getUserInfoFromPref(getContext());
        if (mUserInfo != null)
            new FetchUserInfoTask().execute(mUserInfo.getUserId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();


        invalidateUI();
    }

    @OnClick({R.id.user_avatar, R.id.edit_user_info_tv, R.id.my_shopping_cart, R.id.my_orders,
            R.id.my_collection, R.id.vip_central, R.id.settings, R.id.common_questions
    })
    public void onViewClicked(View view) {
        Intent intent;
        if (mUserInfo != null) {
            switch (view.getId()) {
                case R.id.user_avatar:
                    intent = new Intent(getActivity(), ImageShowerActivity.class);
                    intent.putExtra(Const.IMAGE_URL, mUserInfo.getHeadPic());
                    startActivity(intent);
                    break;
                case R.id.edit_user_info_tv:
                    intent = new Intent(getActivity(), EditUserInfoActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
                case R.id.my_shopping_cart:
                    intent = new Intent(getActivity(), MyShoppingCartActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
                case R.id.my_orders:
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
                case R.id.my_collection:
                    intent = new Intent(getActivity(), MyCollectionActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
                case R.id.vip_central:
                    intent = new Intent(getActivity(), VipActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
                case R.id.settings:
                    intent = new Intent(getActivity(), SettingsActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
                case R.id.common_questions:
                    intent = new Intent(getActivity(), CommonQuestionActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                    break;
            }
        } else {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
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
            UserInfo userInfo = (UserInfo) data.getSerializableExtra(USERINFO_EXTRA);
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
                    + " m      ust implement OnFragmentInteractionListener");
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

    /**
     * 获取用户信息
     */
    private class FetchUserInfoTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int userId = params[0];
            mUserRepository.fetchUserInfo(userId, new UserDatasource.GetUserInfoCallback() {
                @Override
                public void onUserInfoLoaded(UserInfo userInfo) {
                    mUserInfo = userInfo;
                    updateUserInfo(userInfo);
                    invalidateUI();
                    PreferencesUtil.persistentUserInfo(getContext(), userInfo);
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    Log.w(TAG, "onDataNotAvailable: " + errorMsg);
                    if (Const.SERVER_AVALIABLE.equals(errorMsg)) {
                        showMessage(errorMsg);
                    } else {
                        mUserInfo = null;
                        updateUserInfo(mUserInfo);
                        invalidateUI();
                    }
                }
            });

            return null;
        }
    }

    private void showMessage(String msg) {
        if (!getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void invalidateUI() {
        if (mUserInfo != null) {
            mTvLogin.setVisibility(View.GONE);

            Picasso.with(getContext())
                    .load(mUserInfo.getHeadPic())
                    .fit()
                    .into(mUserAvatar);
        } else {
            // 如果用户未登录，则显示"请登录"按钮
            mTvLogin.setVisibility(View.VISIBLE);
        }
    }
}
