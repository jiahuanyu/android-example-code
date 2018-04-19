package me.jiahuan.androidlearn.example.ui.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.base.DividerGridItemDecoration;
import me.jiahuan.androidlearn.base.DividerItemDecoration;
import me.jiahuan.androidlearn.example.R;


public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<String> mStringList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityWithToolbar(R.layout.module_example_layout_recycler_view_activity, true);
        initialize();
    }

    private void initialize() {
        makeData();
        //
        mRecyclerView = findViewById(R.id.id_layout_example_activity_recycler_view);
        // 设置布局管理器
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
//         设置item增加和删除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 增加分割线
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        mAdapter = new RecyclerViewAdapter(this, mStringList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void makeData() {
        for (int i = 0; i < 30; i++) {
            mStringList.add(i + "");
        }
    }

    private static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapterViewHolder> {

        private List<String> mData;
        private Context mContext;

        public RecyclerViewAdapter(Context context, List<String> data) {
            mContext = context;
            mData = data;
        }

        public void removeData(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public RecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerViewAdapterViewHolder viewHolder = new RecyclerViewAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.module_example_layout_recycler_view_activity_recycler_view_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapterViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mData.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    private static class RecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public RecyclerViewAdapterViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_layout_example_activity_recycler_view_item_text_view);
        }


    }

}
