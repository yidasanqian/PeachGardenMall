package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;

/**
 * @see <a href=http://blog.csdn.net/brokge/article/details/8532662>ProgressBar 相关设置讲解</a>
 * Created by dengfengdecao on 16/11/4.
 */

public class ImageShowerActivity extends AppCompatActivity {

    @BindView(R.id.image_shower)
    ImageView mImageShower;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_shower);
        ButterKnife.bind(this);

        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);

        if (getIntent() != null) {
            String imageUrl = getIntent().getStringExtra(Const.IMAGE_URL_EXTRA);

            if (!TextUtils.isEmpty(imageUrl)) {

                // 加载发生错误会重复三次请求，三次都失败才会显示error Place holder
                Picasso.with(this).load(imageUrl)
                        .placeholder(R.drawable.ic_image_downloading_placeholder)
                        .error(R.drawable.ic_image_error_placeholder)
                        .into(mImageShower, new Callback() {
                            @Override
                            public void onSuccess() {
                                mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(ImageShowerActivity.this, "显示图片失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }
}
