package me.zoro.peachgardenmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.GoodsCategoryGridAdapter;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class MallFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    Unbinder unbinder;

    public static MallFragment newInstance(String s) {

        Bundle args = new Bundle();

        MallFragment fragment = new MallFragment();
        fragment.setArguments(args);
        return fragment;
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

        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            images.add(R.drawable.ic_gaoyuanyuan);
        }
        GoodsCategoryGridAdapter goodsAdapter = new GoodsCategoryGridAdapter(getContext(), images);
        mGridView.setAdapter(goodsAdapter);
        mGridView.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // 当点击搜索按钮时触发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO: 17/4/9 商品目录点击事件
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
    }
}
