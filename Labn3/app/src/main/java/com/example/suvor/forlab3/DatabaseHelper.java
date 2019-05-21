package com.example.suvor.forlab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "users"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COLOR = "year";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    //вызывается при попытке доступа к базе данных, но когда еще эта база данных не создана
    //Если база данных отсутствует или ее версия (которая задается в переменной SCHEMA)
    // выше текущей, то срабатывает метод onCreate().
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Для выполнения запросов к SQLite используется метод execSQL(). Он принимает sql-выражение
        // CREATE TABLE, которое создает таблицу. Здесь также при необходимости мы можем выполнить
        // и другие запросы, например, добавить какие-либо начальные данные. Так, в данном случае
        // с помощью того же метода и выражения sql INSERT добавляется один объект в таблицу.
        db.execSQL("CREATE TABLE users (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT, " + COLUMN_COLOR + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME+ ", " + COLUMN_COLOR  + ") VALUES ('Том Смит', Color.RED);");
    }

    //вызывается, когда необходимо обновление схемы базы данных. Здесь можно пересоздать
    // ранее созданную базу данных в onCreate(), установив соответствующие правила преобразования
    // от старой бд к новой
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}