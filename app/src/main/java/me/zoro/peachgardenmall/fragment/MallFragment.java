package me.zoro.peachgardenmall.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.GoodsListActivity;
import me.zoro.peachgardenmall.activity.SearchGoodsActivity;
import me.zoro.peachgardenmall.adapter.GoodsCategoryGridAdapter;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.GoodsCategory;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class MallFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    public static final String GOODSES_EXTRA = "goodes";
    public static final String QUERY_EXTRA = "query";
    public static final String GOODS_CATEGORY_EXTRA = "goods_category";
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    Unbinder unbinder;

    private GoodsRepository mGoodsRepository;
    private GoodsCategoryGridAdapter mCategoryGridAdapter;
    private List<GoodsCategory> mGoodsCategories;
    /**
     * 默认获取第一页
     */
    private int mPageNum = 1;
    /**
     * 默认获取10条
     */
    private int mPageSize = 10;

    public static MallFragment newInstance(String s) {

        Bundle args = new Bundle();

        MallFragment fragment = new MallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getContext().getApplicationContext()
        ));
        mGoodsCategories = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mall, container, false);
        unbinder = ButterKnife.bind(this, root);

        // 获取到TextView的ID
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        // 获取到TextView的控件
        TextView textView = (TextView) mSearchView.findViewById(id);
        // 设置字体大小为14sp
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//14sp
        // 设置字体颜色
        //textView.setTextColor(getActivity().getResources().getColor(R.color.search_txt_color));
        // 设置提示文字颜色
        textView.setHintTextColor(getActivity().getResources().getColor(R.color.textColorSecondary));
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(this);


        mCategoryGridAdapter = new GoodsCategoryGridAdapter(getContext(), mGoodsCategories);
        mGridView.setAdapter(mCategoryGridAdapter);
        mGridView.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoodsCategories.size() < 1) {
            new FetchGoodsCategoriesTask().execute();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // 当点击搜索按钮时触发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query)) {
            searchGoodes(query);
        }
        return false;
    }

    private void searchGoodes(final String query) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", query);
        params.put("pn", mPageNum);
        params.put("ps", mPageSize);
        mGoodsRepository.searchGoodses(params, new GoodsDatasource.SearchGoodsesCallback() {
            @Override
            public void onSearchSucces(ArrayList<Goods> goodses) {
                Intent intent = new Intent(getActivity(), SearchGoodsActivity.class);
                intent.putExtra(GOODSES_EXTRA, goodses);
                intent.putExtra(QUERY_EXTRA, query);
                startActivity(intent);
            }

            @Override
            public void onSearchFailure(String msg) {
                showMessage(msg);
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsCategory category = mGoodsCategories.get(position);
        new FetchGoodsesTask().execute(category.getId());
    }

    private class FetchGoodsCategoriesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Map<String, Object> map = new HashMap<>();
            map.put("pn", mPageNum);
            map.put("ps", mPageSize);
            mGoodsRepository.getGoodsCategories(map, new GoodsDatasource.GetGoodsCategoriesCallback() {
                @Override
                public void onGoodsCategoriesLoaded(List<GoodsCategory> goodsCategories) {
                    mGoodsCategories = goodsCategories;
                    mCategoryGridAdapter.replaceData(goodsCategories);
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    showMessage(errorMsg);
                }
            });
            return null;
        }
    }

    private class FetchGoodsesTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(final Integer... params) {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryId", params[0]);
            map.put("pn", mPageNum);
            map.put("ps", mPageSize);
            mGoodsRepository.getGoodses(map, new GoodsDatasource.GetGoodsesCallback() {
                @Override
                public void onGoodsesLoaded(ArrayList<Goods> goodses) {
                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra(GOODSES_EXTRA, goodses);
                    intent.putExtra(GOODS_CATEGORY_EXTRA, params[0]);
                    startActivity(intent);
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    showMessage(errorMsg);
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
}
