package com.github.jiahuanyu.example;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by doom on 15/7/11.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    protected View mContent;
    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected void initializeFragment(int layoutId) {
        mContent = getActivity().getLayoutInflater().inflate(layoutId, null);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mContent;
    }

    protected void showProgressDialog() {
        dismissProgressDialog();
        mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getString(R.string.common_loading), true, false);
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
