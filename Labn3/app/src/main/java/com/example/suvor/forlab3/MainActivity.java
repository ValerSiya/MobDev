package com.example.suvor.forlab3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button delButton;
    Button saveButton;
    EditText editText;
    RadioGroup radGrp;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        radGrp = (RadioGroup) findViewById(R.id.rdGr);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
    }

    public void save(View view){

        String txtv;
        txtv =  editText.getText().toString();
        String col="RED";
        int color= Color.RED;
        //тут задала
        //выбираем цвет

        switch (radGrp.getCheckedRadioButtonId()) {
            case R.id.rbRed:  col="red"; color=Color.RED;
                break;
            case R.id.rbBlue: col="Blue"; color=Color.BLUE;
                break;
            case R.id.rbBlack: col="Black"; color=Color.BLACK;
                break;
            case R.id.rbYellow: col="Yellow"; color=Color.YELLOW;
                break;
            case R.id.rbPurple: col="Purple"; color=Color.MAGENTA;
                break;
            case R.id.rbGreen: col="Green"; color=Color.GREEN;
                break;
        }
        //Для добавления или обновления нам надо создать объект ContentValues.
        // Данный объект представляет словарь, который содержит набор пар "ключ-значение".
        // Для добавления в этот словарь нового объекта применяется метод put.
        // Первый параметр метода - это ключ, а второй - значение
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, editText.getText().toString());
        cv.put(DatabaseHelper.COLUMN_COLOR, color);
        db.insert(DatabaseHelper.TABLE, null, cv);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Запись успешно добавлена в базу данных!",
                Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void watch(View view){
        Cursor cur = db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        if (cur != null) {
            cur.moveToFirst();                       // Always one row returned.
            if (cur.getCount()==0) {               // Zero count means empty table.
                Toast toast = Toast.makeText(getApplicationContext(),
                        "База данных пуста",
                        Toast.LENGTH_SHORT);
                toast.show();
                }
                else {
                goToDB();
            }

            }



        }

    public void delete(View view){
        db.delete(DatabaseHelper.TABLE, null, null);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Все записи удалены",
                Toast.LENGTH_SHORT);
        toast.show();

    }
    private void goToDB(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}