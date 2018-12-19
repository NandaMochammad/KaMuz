package com.example.nandamochammad.kamuz.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.nandamochammad.kamuz.model.KamusModel;

import java.util.ArrayList;

public class KamusHelper {

    private static String ENGLISH = DBHelper.TABLE_ENGLISH;
    private static String INDONESIA = DBHelper.TABLE_INDONESIA;

    private Context context;
    private DBHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DBHelper(context);
        database = databaseHelper.getWritableDatabase();
        Log.d("masuk", "open: ");
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor searchQueryByName(String query, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        Log.d("masuk", "searchQueryByName: ");
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + DBHelper.FIELD_WORD + " LIKE '%" + query.trim() + "%'", null);
    }

    public ArrayList<KamusModel> getDataByName(String search, boolean isEnglish) {
        KamusModel kamusModel;

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = searchQueryByName(search, isEnglish);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.FIELD_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_WORD)));
                kamusModel.setTerjemah(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_TRANSLATE)));
                arrayList.add(kamusModel);
                Log.d("masukk", "getDataByName: "+arrayList.get(0).getKata());
//                Log.d("masukk", "getDataByName: "+arrayList.get(0));

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public String getData(String search, boolean isEnglish) {
        String result = "";
        Cursor cursor = searchQueryByName(search, isEnglish);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            result = cursor.getString(2);
            for (; !cursor.isAfterLast(); cursor.moveToNext()) {
                result = cursor.getString(2);
            }
        }
        cursor.close();
        return result;
    }

    public Cursor queryAllData(boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY " + DBHelper.FIELD_ID + " ASC", null);
    }

    public ArrayList<KamusModel> getAllData(boolean isEnglish) {
        KamusModel kamusModel;

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData(isEnglish);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.FIELD_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_WORD)));
                kamusModel.setTerjemah(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_TRANSLATE)));
                arrayList.add(kamusModel);
                Log.d("masukk", "getAll: "+arrayList.get(0).getKata());


                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(KamusModel kamusModel, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBHelper.FIELD_WORD, kamusModel.getKata());
        initialValues.put(DBHelper.FIELD_TRANSLATE, kamusModel.getTerjemah());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void insertTransaction(ArrayList<KamusModel> kamusModels, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        String sql = "INSERT INTO " + DATABASE_TABLE + " (" +
                DBHelper.FIELD_WORD + ", " +
                DBHelper.FIELD_TRANSLATE + ") VALUES (?, ?)";

        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < kamusModels.size(); i++) {
            stmt.bindString(1, kamusModels.get(i).getKata());
            stmt.bindString(2, kamusModels.get(i).getTerjemah());
            stmt.execute();
            stmt.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void update(KamusModel kamusModel, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        ContentValues args = new ContentValues();
        args.put(DBHelper.FIELD_WORD, kamusModel.getKata());
        args.put(DBHelper.FIELD_TRANSLATE, kamusModel.getTerjemah());
        database.update(DATABASE_TABLE, args, DBHelper.FIELD_ID + "= '" + kamusModel.getId() + "'", null);
    }

    public void delete(int id, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        database.delete(DATABASE_TABLE, DBHelper.FIELD_ID + " = '" + id + "'", null);
    }
}