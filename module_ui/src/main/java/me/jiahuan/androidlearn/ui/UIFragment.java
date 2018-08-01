package me.jiahuan.androidlearn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jiahuan.androidlearn.base.BaseFragment;
import me.jiahuan.androidlearn.ui.recyclerview.RecyclerViewActivity;
import me.jiahuan.androidlearn.ui.viewpager.ViewPagerActivity;

public class UIFragment extends BaseFragment {

    public static final String TAG = "UIFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.module_ui_layout_ui_fragment, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        view.findViewById(R.id.module_ui_layout_ui_fragment_recycler_view_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
            }
        });

        view.findViewById(R.id.module_ui_layout_ui_fragment_view_pager_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewPagerActivity.class));
            }
        });
    }
}
