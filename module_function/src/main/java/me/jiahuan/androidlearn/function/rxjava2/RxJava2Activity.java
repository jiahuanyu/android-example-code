package me.jiahuan.androidlearn.function.rxjava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.R;

public class RxJava2Activity extends BaseActivity {

    private static final String TAG = "RxJava2Activity";


    private TextView mConsoleTextView;

    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_function_layout_rxjava2_activity);
        mConsoleTextView = findViewById(R.id.id_module_function_layout_rxjava2_activity_console_text_view);
        rxjava();
    }

    private void rxjava() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "Observable Current Thread = " + Thread.currentThread().getName() + "\n");
                mConsoleTextView.append("Observable Current Thread = " + Thread.currentThread().getName() + "\n");

                Log.d(TAG, "Observable emit 1\n");
                mConsoleTextView.append("Observable emit 1\n");
                emitter.onNext("Observable emit 1");
//                Thread.sleep(1000);

                mConsoleTextView.append("Observable emit 2\n");
                Log.d(TAG, "Observable emit 2\n");
                emitter.onNext("Observable emit 2");
//                Thread.sleep(1000);


                mConsoleTextView.append("Observable emit 3\n");
                Log.d(TAG, "Observable emit 3\n");
                emitter.onNext("Observable emit 3");
//                Thread.sleep(1000);
            }
        });


        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                mConsoleTextView.append("observer onSubscribe\n");
                Log.d(TAG, "observer onSubscribe\n");

                Log.d(TAG, "observer Current Thread = " + Thread.currentThread().getName() + "\n");
                mConsoleTextView.append("observer Current Thread = " + Thread.currentThread().getName() + "\n");
            }

            @Override
            public void onNext(String str) {
                mConsoleTextView.append("observer onNext " + str + "\n");
                Log.d(TAG, "observer onNext " + str + "\n");
            }

            @Override
            public void onError(Throwable e) {
                mConsoleTextView.append("observer onError\n");
                Log.d(TAG, "observer onError\n");
            }

            @Override
            public void onComplete() {
                mConsoleTextView.append("observer onComplete\n");
                Log.d(TAG, "observer onComplete\n");
            }
        };

        mDisposable = observable
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Integer>() {

                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.parseInt(s.substring(s.length() - 1));
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        mConsoleTextView.append("accept: " + integer + "\n");
                    }
                });
    }


    public void onDisposeButtonClicked(View v) {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
