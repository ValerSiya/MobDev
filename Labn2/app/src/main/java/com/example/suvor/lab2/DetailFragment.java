package com.example.suvor.lab2;

/**
 * Created by suvor on 01.04.2019.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/*Задача этого фрагмента - вывод некоторой информации. Так как он не должен передавать
никакую информацию другому фрагменту, здесь мы модем ограничиться только переопределением
метода onCreateView(), который в качестве визуального интерфейса устанавливает разметку из
файла fragment_detail.xml*/
public class DetailFragment extends Fragment {
    TextView tvOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        return view;
    }

    // обновление текстового поля
    public void setText(String item, int col) {
        tvOut = (TextView) getView().findViewById(R.id.tvOut);
        tvOut.setText(item);
        //тут присвоила
        tvOut.setTextColor(col);
    }


}
