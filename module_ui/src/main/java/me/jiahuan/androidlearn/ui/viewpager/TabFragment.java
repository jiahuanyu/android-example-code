package me.jiahuan.androidlearn.ui.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class TabFragment extends Fragment {
    private static final String TAG = "TabFragment";
    private static final String ARGUMENT_KET_TITLE = "ARGUMENT_KET_TITLE";

    private String mTitle = "";

    public static TabFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_KET_TITLE, title);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach " + mTitle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ARGUMENT_KET_TITLE);
        Log.d(TAG, "onCreate " + mTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView " + mTitle);
        TextView textView = new TextView(getActivity());
        textView.setText(mTitle);
//        WebView webView = new WebView(getActivity());
//        webView.loadUrl("http://www.baidu.com");
        return textView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint " + mTitle + "    " + isVisibleToUser);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView " + mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy " + mTitle);
    }
}
