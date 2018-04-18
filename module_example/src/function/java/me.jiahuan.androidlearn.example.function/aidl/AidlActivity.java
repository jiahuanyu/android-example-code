package me.jiahuan.androidlearn.example.function.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenenyu.router.annotation.Route;

import me.jiahuan.androidlearn.base.BaseActivity;


@Route("module_example/function/aidl_activity")
public class AidlActivity extends BaseActivity {

    private IBookService mBookService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("AidlActivity", "onServiceConnected");

            mBookService = IBookService.Stub.asInterface(service);
            try {
                Log.e("AidlActivity", "now the list is : " + mBookService.getBooks().toString());


                Book book = new Book();
                book.setPrice(2);
                book.setName("第二本书");
                mBookService.addBook(book);


                Log.e("AidlActivity", "now the list is : " + mBookService.getBooks().toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("AidlActivity", "onServiceDisconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent intent = new Intent(this, Class.forName("me.jiahuan.androidlearn.example.function.aidl.RemoteOrLocalService"));
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
