package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class GoodsDetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mToolbar.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.goods_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_home:
                msg += "Click edit";
                break;
            case R.id.action_share:
                msg += "Click share";
                break;
        }

        if (!msg.equals("")) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
