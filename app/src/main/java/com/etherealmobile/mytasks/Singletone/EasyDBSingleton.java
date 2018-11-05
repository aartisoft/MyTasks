package com.etherealmobile.mytasks.Singletone;

import android.content.Context;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.DataType;
import p32929.androideasysql_library.EasyDB;

public class EasyDBSingleton {
    public static EasyDB easyDB, completedListDB;

    public static void initEasydb(Context context) {
        easyDB = EasyDB.init(context, "TODODB", null, 1)
                .setTableName("TODO TABLE")
                .addColumn(new Column("TODOS", new DataType()._text_().done()))
                .doneTableColumn();
    }

    public static void initCOmpletedEasyDb(Context context) {
        completedListDB = EasyDB.init(context, "TODODBCOMPLETED", null, 1)
                .setTableName("completedListDB")
                .addColumn(new Column("COMPLETED", new DataType()._text_().notNull().done()))
                .doneTableColumn();
    }
}
