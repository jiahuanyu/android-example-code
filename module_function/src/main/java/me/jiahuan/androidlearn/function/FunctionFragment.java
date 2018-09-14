package me.jiahuan.androidlearn.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jiahuan.androidlearn.base.BaseFragment;
import me.jiahuan.androidlearn.function.binder.BinderActivity;
import me.jiahuan.androidlearn.function.hook.HookActivity;
import me.jiahuan.androidlearn.function.jni.JNIActivity;
import me.jiahuan.androidlearn.function.lru.LruCacheActivity;
import me.jiahuan.androidlearn.function.permission.PermissionActivity;
import me.jiahuan.androidlearn.function.rxjava2.RxJava2Activity;

public class FunctionFragment extends BaseFragment {
    public static final String TAG = "FunctionFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.module_function_layout_function_fragment, null);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        view.findViewById(R.id.id_module_function_layout_fragment_lru_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LruCacheActivity.class));
            }
        });
        view.findViewById(R.id.id_module_function_layout_fragment_rxjava2_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RxJava2Activity.class));
            }
        });
        view.findViewById(R.id.id_module_function_layout_fragment_binder_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BinderActivity.class));
            }
        });
        view.findViewById(R.id.id_module_function_layout_fragment_jni_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JNIActivity.class));
            }
        });
        view.findViewById(R.id.id_module_function_layout_fragment_hook_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HookActivity.class));
            }
        });
        view.findViewById(R.id.id_module_function_layout_fragment_permission_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PermissionActivity.class));
            }
        });
    }
}
