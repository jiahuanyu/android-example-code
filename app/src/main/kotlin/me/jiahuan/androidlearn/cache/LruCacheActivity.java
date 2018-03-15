package me.jiahuan.androidlearn.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.jiahuan.androidlearn.R;


public class LruCacheActivity extends AppCompatActivity {

    private static final String TAG = "LruCacheActivity";

    private ImageView mImageView;

    private LruCache<String, Bitmap> mLruCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lru_cache_activity);
        initialize();
    }

    private void initialize() {
        mImageView = findViewById(R.id.id_layout_lru_cache_activity_image_view);
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        findViewById(R.id.id_id_layout_lru_cache_activity_reload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap();
            }
        });
        loadMap();
    }

    private void loadMap() {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {

                Log.d(TAG, "lrucache size = " + mLruCache.size());

                String urlStr = strings[0];
                Bitmap bitmap = mLruCache.get(urlStr);

                if (bitmap == null) {

                    HttpURLConnection connection = null;
                    InputStream inputStream = null;

                    try {
                        URL url = new URL(urlStr);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(5000);
                        connection.setRequestMethod("GET");
                        connection.connect();
                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            inputStream = connection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            mLruCache.put(urlStr, bitmap);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        }.execute("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png");
    }
}
