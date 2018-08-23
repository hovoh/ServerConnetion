package com.example.chen.serverconnetion;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class GameProvider extends ContentProvider {
   public static final String AUTHORITY = "com.example.chen.serverconnextion.GameProvider";
   public static final Uri GAME_CONTENT_URO = Uri.parse("content://" + AUTHORITY + "/game");
   private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
   private SQLiteDatabase mDb;
   private Context mContext;
   private String table;
    static {
        mUriMatcher.addURI(AUTHORITY,"game",0);
    }


    @Override
    public boolean onCreate() {
        table = DbOpenHelper.GAME_TABLE_NAME;
        mContext = getContext();
        initProvoder();
        return false;

    }

    private void initProvoder()
    {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDb.execSQL("delete from " + DbOpenHelper.GAME_TABLE_NAME);
                mDb.execSQL("insert into game values(1,'九阴真经ol','最好玩的武侠网游');");
            }
        }).start();
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        String table = DbOpenHelper.GAME_TABLE_NAME;
        Cursor mCursor = mDb.query(table,strings,s,strings1,null,s1,null);

        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        mDb.insert(table,null,contentValues);
        mContext.getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
