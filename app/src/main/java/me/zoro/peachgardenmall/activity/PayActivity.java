package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.api.PayClient;
import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.common.AppConfig;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.domain.Order;
import me.zoro.peachgardenmall.datasource.domain.PayResult;
import me.zoro.peachgardenmall.utils.OrderInfoUtil2_0;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.zoro.peachgardenmall.utils.OrderInfoUtil2_0.getOutTradeNo;


/**
 * @see <a href=https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.f5tmX8&treeId=59&articleId=103927&docType=1>
 * 签名机制</a>
 */
public class PayActivity extends AppCompatActivity {

    private static final String TAG = "PayActivity";

    public static final String ORDER_DETAIL_EXTRA = "order_detail";

    private static final int SDK_PAY_FLAG = 1;
    /**
     * 支付宝支付业务：入参app_id
     */
    private static final String APPID = "2017050607142602";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    private static final String PID = "2088621928157904";
    /**
     * 支付宝服务器主动通知商户服务器里指定的页面
     */
    private static final String NOTIFY_URL = AppConfig.SERVER_HOST.concat("/Home/Order/alipay_notify");
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_ok)
    Button mBtnOk;

    private PayClient mPayClient;
    private MyHandler mHandler;

    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mHandler = new MyHandler(this);

        mPayClient = ServiceGenerator.createService(this, PayClient.class);


        mOrder = (Order) getIntent().getSerializableExtra(CreateOrderActivity.ORDER_EXTRA);

    }

    @OnClick(R.id.btn_ok)
    public void onClick() {
        if (mOrder != null) {
            Gson gson = new Gson();

            Map<String, Object> orderReqParams = new HashMap<>();
            // 用户id
            orderReqParams.put("userId", mOrder.getUserId());
            orderReqParams.put("addressId", mOrder.getAddressId());
            orderReqParams.put("freight", mOrder.getFreight());
            orderReqParams.put("promotionMoney", mOrder.getPromotionMoney());
            orderReqParams.put("promotionIds", mOrder.getPromotionIds());
            orderReqParams.put("totalMoney", mOrder.getFactPayMoney());
            orderReqParams.put("payType", 1);

            List<Order.GoodsInfo> goodsInfoList = mOrder.getGoodsInfo();
            // JsonArray jsonArray = new JsonParser().parse(gson.toJson(goodsInfoList)).getAsJsonArray();
            JsonArray jsonArray = new JsonArray();
            JsonObject jo = new JsonObject();
            for (int i = 0; i < goodsInfoList.size(); i++) {
                Order.GoodsInfo goodsInfo = goodsInfoList.get(i);
                jo.addProperty("specKey", goodsInfo.getSpecKey());
                jo.addProperty("goodsId", goodsInfo.getGoodsId());
                jo.addProperty("number", goodsInfo.getGoodsNum());
                jsonArray.add(jo);
            }
            orderReqParams.put("goodsInfos", jsonArray);
            String outTradeNo = getOutTradeNo();
            mOrder.setOutTraceNo(outTradeNo);
            orderReqParams.put("out_trade_no", outTradeNo);
            // 业务参数
            Map<String, String> serviceParams = new HashMap<>();
            serviceParams.put("timeout_express", "30m");
            // 卖家支付宝账号
            serviceParams.put("seller_id", PID);
            serviceParams.put("product_code", "QUICK_MSECURITY_PAY");
            // 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
            // TODO: 17/5/11 支付金额
            serviceParams.put("total_amount", String.valueOf(0.01));
            serviceParams.put("subject", getString(R.string.app_name));
            //serviceParams.put("body", "充值人民币");
            //  生成唯一商户订单号
            serviceParams.put("out_trade_no", outTradeNo);

            // 请求参数是商户在与支付宝进行数据交互时，提供给支付宝的请求数据，以便支付宝根据这些数据进一步处理
            final Map<String, String> reqParams = new HashMap<>();
            reqParams.put("app_id", APPID);
            reqParams.put("biz_content", gson.toJson(serviceParams));
            reqParams.put("charset", "utf-8");
            reqParams.put("format", "json");
            reqParams.put("method", "alipay.trade.app.pay");
            reqParams.put("notify_url", NOTIFY_URL);
            reqParams.put("sign_type", "RSA2");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String dateStr = sdf.format(Calendar.getInstance().getTime());
            reqParams.put("timestamp", dateStr);
            reqParams.put("version", "1.0");
            // 用户只能在指定渠道范围内支付当有多个渠道时用“,”分隔
            reqParams.put("enable_pay_channels", "balance,moneyFund");

            // 首先构造原始请求字符串
            String srcReqParams = OrderInfoUtil2_0.buildOrderParam(reqParams, false);
            Log.d(TAG, "onClick: 原始字符串 <== " + srcReqParams);
            // 再对请求字符串的所有一级value（biz_content作为一个value）进行encode
            final String encodeSignInfo = OrderInfoUtil2_0.buildOrderParam(reqParams, true);

            orderReqParams.put("orderInfo", srcReqParams);

            Call<JsonObject> call = mPayClient.getOrderInfo(orderReqParams);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject bodyJson = response.body();
                    if (bodyJson == null) {
                        showMessage(Const.SERVER_UNAVAILABLE);
                        mBtnOk.setEnabled(true);

                    } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                        showMessage(bodyJson.get(Const.MESSAGE).getAsString());
                        mBtnOk.setEnabled(true);
                    } else {
                        JsonObject resultJson = bodyJson.get(Const.RESULT).getAsJsonObject();
                        final String returnSign = resultJson.get("returnSign").getAsString();   // 签名信息
                        // 最后对原始字符串进行签名
                        final String signedOrderInfo = encodeSignInfo.concat("&sign=").concat(returnSign);
                        Log.d(TAG, "onResponse: 编码并签名后的订单信息 <== " + signedOrderInfo);

                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask payTask = new PayTask(PayActivity.this);
                                Map<String, String> result = payTask.payV2(signedOrderInfo, true);
                                Log.d("支付结果 <== ", result.toString());

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mBtnOk.setEnabled(true);
                    Log.e(TAG, "onFailure: 获取支付签名异常", t);
                    showMessage(Const.SERVER_UNAVAILABLE);
                }
            });
        } else {
            showMessage(getString(R.string.create_order_error));
        }
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<PayActivity> mTarget;

        public MyHandler(PayActivity target) {
            mTarget = new WeakReference<PayActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayActivity target = mTarget.get();
                    if (target != null) {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         返回结果需要通过resultStatus以及result字段的值来综合判断并确定支付结果。在resultStatus=9000，
                         并且success=“true”以及sign=“xxx”校验通过的情况下，证明支付成功，其它情况归为失败。
                         较低安全级别的场合，也可以只通过检查resultStatus以及success=“true”来判定支付结果。
                         */
                        String resultInfo = payResult.getResult();  // 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(target, target.getString(R.string.payment_success), Toast.LENGTH_LONG).show();
                            Intent data = new Intent(target, PaymentSuccessActivity.class);
                            data.putExtra(ORDER_DETAIL_EXTRA, target.mOrder);
                            target.startActivity(data);
                            target.finish();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(target, target.getString(R.string.payment_fail), Toast.LENGTH_LONG).show();
                            target.setResult(RESULT_CANCELED);
                            target.finish();
                        }
                    }

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
