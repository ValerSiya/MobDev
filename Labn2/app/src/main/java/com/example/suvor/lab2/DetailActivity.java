package com.example.suvor.lab2;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity  extends AppCompatActivity {
    public static final String EXTRA_URL = "url";
    public static final  String EXTRA_COLOR=String.valueOf(Color.RED);
    /*Здесь в первую очередь проверяем конфигурацию. Так как эта activity предназначена
    только для портретного режима, то при альбомной ориентации осуществляем выход:*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String url = extras.getString(EXTRA_URL);
            int col=extras.getInt(EXTRA_COLOR);
            /*Иначе получаем через метод getFragmentManager() фрагмент DetailFragment и вызываем
            его метод setText(). В качестве аргумента в этот метод передается строковое значение,
            переданное через Intent.*/
            DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            detailFragment.setText(url, col);
        }
    }
}
