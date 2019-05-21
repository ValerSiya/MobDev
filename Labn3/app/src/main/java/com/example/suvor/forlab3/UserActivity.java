package com.example.suvor.forlab3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    //В методе onCreate() происходит создание объекта SQLiteOpenHelper. Сама инициализация
    // объектов для работы с базой данных происходит в методе onResume(), который срабатывает
    // после метода onCreate().
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userList = (ListView)findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //В метод delete() передается название таблицы, а также столбец, по которому
                // происходит удаление, и его значение. В качестве критерия можно выбрать
                // несколько столбцов, поэтому третьим параметром идет массив.
                // Знак вопроса ? обозначает параметр, вместо которого подставляется значение
                // из третьего параметра.
                db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(id)});
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        //Чтобы получить объект базы данных, надо использовать метод getReadableDatabase()
        // (получение базы данных для чтения) или getWritableDatabase(). Так как в данном
        // случае мы будет только считывать данные из бд, то воспользуемся первым методом:
        db = databaseHelper.getReadableDatabase();
        //получаем данные из бд в виде курсора
        //После выполнения запроса rawQuery() возвращает объект Cursor, который
        // хранит результат выполнения SQL-запроса:
        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_COLOR};
        // создаем адаптер, передаем в него курсор

        //Дополнительно для управления курсором в Android имеется класс CursorAdapter.
        // Он позволяет адаптировать полученный с помощью курсора набор к отображению в
        // списковых элементах наподобие ListView. Как правило, при работе с курсором
        // используется подкласс CursorAdapter - SimpleCursorAdapter.
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                // Initialize a TextView for ListView each Item
                TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView tv2 = (TextView) view.findViewById(android.R.id.text2);
                    String tvValue = tv2.getText().toString();
                        int num1 = Integer.parseInt(tvValue);
                // Set the text color of TextView (ListView Item)
                tv1.setTextColor(num1);
                    //tv2.setVisibility(View.GONE);
                    //tv2.setTextSize(TypedValue.COMPLEX_UNIT_IN,0.07f);
                // Generate ListView Item using TextView
                return view;
            }
        };
        userList.setAdapter(userAdapter);
    }
    // по нажатию на кнопку запускаем MainActivity
    public void add(View view){
        Intent intent = new Intent(this, MainActivity.class);startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}