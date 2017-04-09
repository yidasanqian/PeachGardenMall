package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.fragment.MyShoppingCartFragment;
import me.zoro.peachgardenmall.utils.ActivityUtils;

public class MyShoppingCartActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_right_txt)
    TextView mToolbarRightTxt;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_cart);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        MyShoppingCartFragment fragment = (MyShoppingCartFragment) getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (fragment == null) {
            fragment = MyShoppingCartFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.main_content, Const.MY_SHOPPING_CART_FRAG_TAG);
        }

    }


    @OnClick(R.id.toolbar_right_txt)
    public void onViewClicked() {
        // TODO: 17/4/9 编辑购物车
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
