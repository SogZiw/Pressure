package com.pressure.record.pro;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecordProviderNew extends ContentProvider {

    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uri == null || !uri.toString().endsWith("/directories")) return null;
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"accountName", "accountType", "displayName", "typeResourceId", "exportSupport", "shortcutSupport", "photoSupport"});
        matrixCursor.addRow(new Object[]{getContext().getPackageName(), getContext().getPackageName(), getContext().getPackageName(), 0, 1, 1, 1});
        return matrixCursor;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}