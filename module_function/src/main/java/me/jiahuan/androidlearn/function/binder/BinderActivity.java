package me.jiahuan.androidlearn.function.binder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.R;

public class BinderActivity extends BaseActivity {
    private static final String TAG = "BinderSample";

    private IBookManager mBookManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mBookManager = IBookManager.Stub.asInterface(service);
//            service.linkToDeath();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            unbindService(this);
            mBookManager = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_function_layout_binder_activity);
    }

    public void onBindButtonClicked(View v) {
        Log.d(TAG, "onBindButtonClicked");
        Intent intent = new Intent(this, BookRemoteService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }


    public void onGetBookListButtonClicked(View v) throws RemoteException {
        Log.d(TAG, "onGetBookListButtonClicked");
        if (mBookManager != null) {
            List<Book> books = mBookManager.getBookList();
            for (Book book : books) {
                Log.d(TAG, "bookId = " + book.bookId + ", bookName = " + book.bookName);
            }
        }
    }

    public void onAddBookButtonClicked(View v) throws RemoteException {
        Log.d(TAG, "onAddBookButtonClicked");
        if (mBookManager != null) {
            int id = (int) (Math.random() * 100);
            Book book = new Book(id, id + "");
            mBookManager.addBook(book);
        }
    }

    public void onUnbindButtonClicked(View v) {
        Log.d(TAG, "onUnbindButtonClicked");
        unbindService(mServiceConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(mServiceConnection);
    }
}

