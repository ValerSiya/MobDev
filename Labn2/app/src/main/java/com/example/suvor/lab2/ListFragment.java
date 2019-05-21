package com.example.suvor.lab2;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class ListFragment extends Fragment {
    Button btnOk;
    Button btnCancel;
    EditText editText;
    /*Фрагменты не могут напрямую взаимодействовать между собой. Для этого надо обращаться
    к контексту, в качестве которого выступает класс Activity. Для обращения к activity,
    как правило, создается вложенный интерфейс. В данном случае он называется OnFragmentInteractionListener
    с одним методом.*/
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        // найдем View-элементы
        editText = (EditText) view.findViewById(R.id.editText);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        final RadioGroup radGrp = (RadioGroup) view.findViewById(R.id.rdGr);
        // задаем обработчик кнопки
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                String txtv;
                txtv =  editText.getText().toString();
                int col=Color.RED;
                //тут задала
                //выбираем цвет
                switch (radGrp.getCheckedRadioButtonId()) {
                    case R.id.rbRed:  col=Color.RED;
                        break;
                    case R.id.rbBlue: col=Color.BLUE;
                        break;
                    case R.id.rbBlack: col=Color.BLACK;
                        break;
                    case R.id.rbYellow: col=Color.YELLOW;
                        break;
                    case R.id.rbPurple: col=Color.rgb(128, 0, 128);
                        break;
                    case R.id.rbGreen: col=Color.rgb(0, 128, 0);
                        break;
                }
                mListener.onFragmentInteraction(txtv, col);

            }
        }
        );
        btnCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mListener.onFragmentInteraction("", 0);
            }
                                     }
        );
        return view;
    }

    interface OnFragmentInteractionListener {

        void onFragmentInteraction(String link, int col);
    }
/*Но чтобы взаимодействовать с другим фрагментом через activity, нам надо прикрепить текущий фрагмент
к activity. Для этого в классе фрагмента определен метод onAttach(Context context). В нем происходит
установка объекта OnFragmentInteractionListener:*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }
}