package me.zoro.peachgardenmall.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.fragment.HomeFragment;

public class AdActivity extends AppCompatActivity {

    private static final String TAG = "AdActivity";
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);

        final String url = getIntent().getStringExtra(HomeFragment.AD_URL_EXTRA);
        mToolbar.setTitle(url);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        final String cacheDirPath = getCacheDir().getPath();

        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);    // 启用js
        settings.setJavaScriptCanOpenWindowsAutomatically(true);    // js和android交互
        mWebView.addJavascriptInterface(new JavaScriptInterface(), "android"); // js调原生

        settings.setAppCachePath(cacheDirPath); // 设置缓存的指定路径
        settings.setAllowFileAccess(true);      // 允许访问文件
        settings.setAppCacheEnabled(true);      // 设置H5的缓存打开,默认关闭
        settings.setUseWideViewPort(true);      // 设置webview自适应屏幕大小
        settings.setLoadWithOverviewMode(true); // 设置webview自适应屏幕大小
        settings.setDomStorageEnabled(true);    // 设置可以使用localStorage
        settings.setSupportZoom(false);         // 关闭zoom按钮
        settings.setBuiltInZoomControls(false); // 关闭zoom

        // 告诉WebView先不要自动加载图片，等页面finish后再发起图片加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        // 解决硬件加速导致页面渲染闪烁
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        /**
         * webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);这个属性
         可以让webview只显示一列，也就是自适应页面大小 不能左右滑动，但在使用中发现，只针对4.4以下有效，
         因为4.4的webview内核改了，Google也在api中说了，要么改html样式，要么改变WebView；
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        }


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading: url <== " + url);
                //parserURL(url); // 解析url,如果存在有跳转原生界面的url规则，则跳转原生。
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!settings.getLoadsImagesAutomatically()) {
                    settings.setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });

        mWebView.loadUrl(url);
    }

    private class JavaScriptInterface {

        @JavascriptInterface
        public void runJs() {
            Toast.makeText(AdActivity.this, "runJs", Toast.LENGTH_SHORT).show();
        }
    }

    // android原生调用js：
    // webView.loadUrl("javascript:wave()");


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ad_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reload:
                Log.d(TAG, "onOptionsItemSelected: 刷新");
                mWebView.reload();
                break;
           /* case R.id.menu_select_all:
                Log.d(TAG, "onOptionsItemSelected: 选择所有");
                mWebView.setSelected(true);
                break;
            case R.id.menu_copy:
                Log.d(TAG, "onOptionsItemSelected: 复制");
                break;
            case R.id.menu_paste:
                Log.d(TAG, "onOptionsItemSelected: 粘贴");
                break;
            case R.id.menu_share:
                Log.d(TAG, "onOptionsItemSelected: 分享");
                break;
            case R.id.menu_websearch:
                Log.d(TAG, "onOptionsItemSelected: 搜索");
                break;*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
