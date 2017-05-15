package me.zoro.peachgardenmall.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.OrderDatasource;
import me.zoro.peachgardenmall.datasource.OrderRepository;
import me.zoro.peachgardenmall.datasource.domain.Order;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.OrderRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.view.AutoDrawableRadioButton;

/**
 * Created by dengfengdecao on 17/5/11.
 */

public class PublishCommentActivity extends AppCompatActivity {
    private static final String TAG = "PublishCommentActivity";
    @BindView(R.id.tv_publish)
    TextView mTvPublish;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;


    private UserInfo mUserInfo;
    private Order mOrder;
    private OrderRepository mOrderRepository;
    private JsonArray mCommentArray;
    /**
     * true,表示用户未输入评论，false，表示用户已输入评论内容
     */
    private boolean mIsEmptyContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_comment);
        ButterKnife.bind(this);

        mUserInfo = CacheManager.getUserInfoFromCache(this);

        mOrder = (Order) getIntent().getSerializableExtra(OrderDetailActivity.ORDER_EXTRA);

        mOrderRepository = OrderRepository.getInstance(OrderRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        mCommentArray = new JsonArray();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GoodsesInOrderAdapter adapter = new GoodsesInOrderAdapter(mOrder.getGoodsInfo());
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.tv_publish)
    public void onViewClicked(View view) {
        if (mIsEmptyContent) {
            setLoadingIndicator(true, getString(R.string.publishing));
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put("userId", mUserInfo.getUserId());
            reqParams.put("orderId", mOrder.getId());
            reqParams.put("mCommentArray", mCommentArray);
            mOrderRepository.evaluateGoodses(reqParams, new OrderDatasource.EvaluateGoodsesCallback() {
                @Override
                public void onEvaluateSuccess(String msg) {
                    showMessage(msg);
                    setLoadingIndicator(false, getString(R.string.publishing));
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onEvaluateFailure(String msg) {
                    showMessage(msg);
                    setLoadingIndicator(false, getString(R.string.publishing));
                }
            });
        } else {
            Snackbar.make(findViewById(R.id.ll_evaluation_container), "请输入评论内容", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void setLoadingIndicator(boolean active, String title) {
        if (mProgressBarContainer != null) {
            if (active) {
                // 设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }


    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    static class GoodsesInOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Order.GoodsInfo> mGoodsInfos;

        public GoodsesInOrderAdapter(List<Order.GoodsInfo> goodsInfo) {
            mGoodsInfos = goodsInfo;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.publish_comment_item, parent, false);

            return RecyclerItemViewHolder.newInstance(viewItem);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            Order.GoodsInfo goodsInfo = mGoodsInfos.get(position);
            viewHolder.mEtEvaluationContent.setTag(goodsInfo.getGoodsId());
        }

        @Override
        public int getItemCount() {
            return mGoodsInfos.size();
        }

        static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.rb_praise_evaluation)
            AutoDrawableRadioButton mRbPraise;
            @BindView(R.id.rb_moderate_evaluation)
            AutoDrawableRadioButton mRbModerate;
            @BindView(R.id.rb_bad_evaluation)
            AutoDrawableRadioButton mRbBad;
            @BindView(R.id.et_evaluation_content)
            EditText mEtEvaluationContent;
            @BindView(R.id.cb_is_anon)
            CheckBox mCbIsAnon;
            @BindView(R.id.tv_name)
            TextView mTvName;
            @BindView(R.id.tv_anon_desc)
            TextView mTvAnonDesc;

            private WeakReference<Context> mTarget;

            private JsonObject mComment;
            /**
             * 评论类型 0好评 1中评 2差评
             */
            private int mType;
            /**
             * 评论内容
             */
            private String mContent;
            /**
             * 是否匿名 0不匿名 1匿名
             */
            private int anonymous;

            @OnTextChanged(R.id.et_evaluation_content)
            void onTextChanged(CharSequence text) {
                if (mTarget.get() != null) {
                    PublishCommentActivity target = (PublishCommentActivity) mTarget.get();
                    if (TextUtils.isEmpty(text)) {
                        target.mIsEmptyContent = false;
                    } else {
                        target.mIsEmptyContent = true;
                        mContent = text.toString().trim();
                        setCommentArray(target);
                    }
                }
            }

            @OnCheckedChanged({R.id.cb_is_anon, R.id.rb_praise_evaluation,
                    R.id.rb_moderate_evaluation, R.id.rb_bad_evaluation})
            void onChecked(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.cb_is_anon:
                        PublishCommentActivity target = (PublishCommentActivity) mTarget.get();
                        if (target != null) {
                            if (isChecked) {
                                anonymous = 1;
                                mTvName.setText("匿名");
                            } else {
                                anonymous = 0;
                                mTvName.setText(target.mUserInfo.getNickname());
                            }

                            setCommentArray(target);
                        }
                        break;
                    case R.id.rb_praise_evaluation:
                        target = (PublishCommentActivity) mTarget.get();
                        if (target != null) {
                            if (isChecked) {
                                mType = 0;
                                Log.d(TAG, "onCheckedChanged: mType ==> " + mType);
                            }

                            setCommentArray(target);
                        }
                        break;
                    case R.id.rb_moderate_evaluation:
                        target = (PublishCommentActivity) mTarget.get();
                        if (target != null) {
                            if (isChecked) {
                                mType = 1;
                                Log.d(TAG, "onCheckedChanged: mType ==> " + mType);
                            }

                            setCommentArray(target);
                        }

                        break;
                    case R.id.rb_bad_evaluation:
                        target = (PublishCommentActivity) mTarget.get();
                        if (target != null) {
                            if (isChecked) {
                                mType = 2;
                                Log.d(TAG, "onCheckedChanged: mType ==> " + mType);
                            }

                            setCommentArray(target);
                        }
                        break;
                }

            }

            private void setCommentArray(PublishCommentActivity target) {
                if (mComment.get("goodsId") != null) {
                    target.mCommentArray.remove(mComment);
                }
                int goodsId = (int) mEtEvaluationContent.getTag();
                mComment.addProperty("type", mType);
                mComment.addProperty("content", mContent);
                mComment.addProperty("goodsId", goodsId);
                mComment.addProperty("anonymous", anonymous);
                target.mCommentArray.add(mComment);
            }


            public RecyclerItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                mTarget = new WeakReference<Context>(itemView.getContext());

                mComment = new JsonObject();
            }

            public static RecyclerView.ViewHolder newInstance(View viewItem) {
                return new RecyclerItemViewHolder(viewItem);
            }
        }
    }
}
