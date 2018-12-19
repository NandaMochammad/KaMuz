package com.example.nandamochammad.kamuz.DB;

import android.provider.BaseColumns;

public class SQLiteHelper {

    static  String TABLE_NAME = "kamus";
    static  final class kamus implements BaseColumns{
        static String id = "id";
        static String kata = "kata";
        static String terjemah = "terjemah";

    }

}
