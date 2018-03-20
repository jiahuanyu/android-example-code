package me.jiahuan.androidlearn.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import me.jiahuan.androidlearn.R;

/**
 * Created by vendor on 2018/3/20.
 */

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG = "ContentProviderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_provider_activity);
        final ContentResolver contentResolver = getContentResolver();

        findViewById(R.id.id_layout_content_provider_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "x000");
                contentResolver.insert(Uri.parse("content://me.jiahuan.androidlearn/user"), contentValues);


                Cursor cursor = contentResolver.query(Uri.parse("content://me.jiahuan.androidlearn/user"), null, null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    Log.d(TAG, name);
                }
                cursor.close();
            }
        });
    }

}
