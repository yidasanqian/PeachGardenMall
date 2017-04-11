package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.api.PayClient;
import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.datasource.domain.PayResult;
import okhttp3.ResponseBody;
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

    public static final String RECHARGE_MONEY_EXTRA = "recharge_money_extra";
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJIFf0TZ5ZVbVj+RTmvirfWzUdi88q0jPRZqT5fMXdxQAsBy5ISv7IG/Ja/G+CDAXWyhtSPsZhDi/Ago2JcOM4NDPrZydqKm8i8aMXzLqbWYG1MlfQHu6rufP8a1voK6NlV+8A4wFAV17yQVI+IEqKc50SH1hojtk8KwwILlinSLAgMBAAECgYBUh/AkOIvqiaSFSiX+2IviJ7vi34cQ6cxsVIDdHIbdikf9hsV5dqpQdgpoFqP/ubybrYVCVZDEh9JlGtg/og35ODKF9P8icL3et9FpqYuN5asRkAw8CQMfoLdLsiTp359QI4PK8GEcqOOHSm9CKB83Kp5vZIWUo6QUDPVhAjCBMQJBAMIHrQ1znq4iwaN5lqCA4LEdY67YDaTaX9RKz+wUzBdaP58Ut6MVDaf4cN+qPEYe23K3tJOi31TNZ3VP9iSSzDMCQQDAqInMnydbiIwDN7CnL+FGqtz8z0nhw5e7xLVoFKn3mIJUuhpMtCt1PnXYhbOYPiHkhZliXlq3CIaxlqkw/t5JAkEAk3uwL5Rd3jEvDOqD8vZjVF1pguJY5KDUzJIdH27jfzCrQWlG+KAtJCs06N4GOKqF1doLWVko9tW2uTYRe9VVfwJBAJjs7zzaVE5m6+Sd7v42llYWyIVwMRAgxq+ILArq5COiDkoc00VxelF9e+Ob9XvyTcrsdV1M0isZfHk4wyIeHOkCQQCVBi2z7xfUmxZxGJBpRr7xGtR4/ddaQvFazFu2BwG6yvm2KPvsqfDZxOjK8aWtjTUW/+RkLmPkNKG1Tu7TrZyK";
    //public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJIFf0TZ5ZVbVj+RTmvirfWzUdi88q0jPRZqT5fMXdxQAsBy5ISv7IG/Ja/G+CDAXWyhtSPsZhDi/Ago2JcOM4NDPrZydqKm8i8aMXzLqbWYG1MlfQHu6rufP8a1voK6NlV+8A4wFAV17yQVI+IEqKc50SH1hojtk8KwwILlinSLAgMBAAECgYBUh/AkOIvqiaSFSiX+2IviJ7vi34cQ6cxsVIDdHIbdikf9hsV5dqpQdgpoFqP/ubybrYVCVZDEh9JlGtg/og35ODKF9P8icL3et9FpqYuN5asRkAw8CQMfoLdLsiTp359QI4PK8GEcqOOHSm9CKB83Kp5vZIWUo6QUDPVhAjCBMQJBAMIHrQ1znq4iwaN5lqCA4LEdY67YDaTaX9RKz+wUzBdaP58Ut6MVDaf4cN+qPEYe23K3tJOi31TNZ3VP9iSSzDMCQQDAqInMnydbiIwDN7CnL+FGqtz8z0nhw5e7xLVoFKn3mIJUuhpMtCt1PnXYhbOYPiHkhZliXlq3CIaxlqkw/t5JAkEAk3uwL5Rd3jEvDOqD8vZjVF1pguJY5KDUzJIdH27jfzCrQWlG+KAtJCs06N4GOKqF1doLWVko9tW2uTYRe9VVfwJBAJjs7zzaVE5m6+Sd7v42llYWyIVwMRAgxq+ILArq5COiDkoc00VxelF9e+Ob9XvyTcrsdV1M0isZfHk4wyIeHOkCQQCVBi2z7xfUmxZxGJBpRr7xGtR4/ddaQvFazFu2BwG6yvm2KPvsqfDZxOjK8aWtjTUW/+RkLmPkNKG1Tu7TrZyK";

    private static final int SDK_PAY_FLAG = 1;
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016121904425820";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088521478612514";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_recharge_money)
    TextInputEditText mEtRechargeMoney;
    @BindView(R.id.btn_ok)
    Button mBtnOk;

    private PayClient mPayClient;
    private MyHandler mHandler;
    private String mMoney;  // 要充值的金额


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        mToolbar.setTitle("付款");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);


        mHandler = new MyHandler(this);

        mPayClient = ServiceGenerator.createService(this, PayClient.class);

    }

    @OnClick(R.id.btn_ok)
    public void onClick() {

    }

    private void rechargeMoney(String money) {
        mMoney = money;

        // 业务参数
        Map<String, String> serviceParams = new HashMap<>();
        // 用户id
        //serviceParams.put("userId", String.valueOf(mCurrentUser.getId()));
        //  生成唯一商户订单号
        serviceParams.put("out_trade_no", getOutTradeNo());
        // 卖家支付宝账号
        serviceParams.put("seller_id", PID);
        serviceParams.put("product_code", "QUICK_MSECURITY_PAY");
        // 用户只能在指定渠道范围内支付当有多个渠道时用“,”分隔
        serviceParams.put("enable_pay_channels", "balance,moneyFund");
        serviceParams.put("subject", "乐助币");
        serviceParams.put("body", "充值乐助币");
        // 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
        serviceParams.put("total_amount", money);
        Gson gson = new Gson();

        // 请求参数是商户在与支付宝进行数据交互时，提供给支付宝的请求数据，以便支付宝根据这些数据进一步处理
        final Map<String, String> reqParams = new HashMap<>();
        reqParams.put("app_id", APPID);
        reqParams.put("biz_content", gson.toJson(serviceParams));
        /*reqParams.put("charset", "utf-8");
        reqParams.put("method", "alipay.trade.app.pay");
        reqParams.put("notify_url", AppConfig.SERVER_HOST + "/api/v1/anon/notify");
        reqParams.put("sign_type", "RSA");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        reqParams.put("timestamp", timestamp);
        reqParams.put("version", "1.0");
        final String orderParam = OrderInfoUtil2_0.buildOrderParam(reqParams);
        final String sign = OrderInfoUtil2_0.getSign(reqParams, RSA_PRIVATE);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                final String orderInfo = orderParam + "&" + sign;   // 订单信息
                Log.d(TAG, "orderInfo: 订单信息 <== " + orderInfo);

                PayTask payTask = new PayTask(PayActivity.this);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();*/
        Call<ResponseBody> call = mPayClient.getOrderInfo(reqParams);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {

                        final String orderInfo = response.body().string();   // 订单信息
                        Log.d(TAG, "onResponse: 订单信息 <== " + orderInfo);

                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask payTask = new PayTask(PayActivity.this);
                                Map<String, String> result = payTask.payV2(orderInfo, true);
                                Log.i("msp", result.toString());

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else {
                        mBtnOk.setEnabled(true);

                        Toast.makeText(PayActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    mBtnOk.setEnabled(true);
                    Toast.makeText(PayActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onResponse: IOException 获取支付签名异常", e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mBtnOk.setEnabled(true);
                Toast.makeText(PayActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: 获取支付签名异常", t);
            }
        });
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
                            Toast.makeText(target, "支付成功", Toast.LENGTH_LONG).show();
                            Intent data = new Intent();
                            data.putExtra(RECHARGE_MONEY_EXTRA, target.mMoney);
                            target.setResult(RESULT_OK, data);
                            target.finish();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(target, "支付失败", Toast.LENGTH_LONG).show();
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
