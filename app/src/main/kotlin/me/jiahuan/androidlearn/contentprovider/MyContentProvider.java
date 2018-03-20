package me.jiahuan.androidlearn.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    private static final String TAG = "MyContentProvider";

    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // SQLITE
    private DatabaseSQLiteOpenHelper mDatabaseSQLiteOpenHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        mUriMatcher.addURI("me.jiahuan.androidlearn", "user", 1000);
        mDatabaseSQLiteOpenHelper = new DatabaseSQLiteOpenHelper(getContext(), "testDB", null, 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query");
        SQLiteDatabase database = mDatabaseSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query("USER", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");
        switch (mUriMatcher.match(uri)) {
            case 1000:
                SQLiteDatabase database = mDatabaseSQLiteOpenHelper.getWritableDatabase();
                database.insert("USER", null, values);
                break;
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    static class DatabaseSQLiteOpenHelper extends SQLiteOpenHelper {

        public DatabaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE USER(NAME TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
