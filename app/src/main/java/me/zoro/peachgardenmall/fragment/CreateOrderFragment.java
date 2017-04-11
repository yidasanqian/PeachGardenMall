package me.zoro.peachgardenmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zoro.peachgardenmall.R;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class CreateOrderFragment extends Fragment {

    public static CreateOrderFragment newInstance() {

        Bundle args = new Bundle();

        CreateOrderFragment fragment = new CreateOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_order, container, false);

        return root;

    }
}
