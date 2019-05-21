package com.example.suvor.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*Для взаимодействия фрагмента ListFragment c другим фрагментом через MainActivity надо,
чтобы эта activity реализовывала интерфейс OnFragmentInteractionListener. Для этого реализуем
метод onFragmentInteraction(), который получает фрагмент DetailFragment и вызывает у него метод setText()

В итоге получится, что при нажатии кнопки на фрагменте ListFragment будет срабатывать метод
updateDetail(), который вызовет метод mListener.onFragmentInteraction(newTime). mListener
устанавливается как activity, поэтому при этом будет вызван метод setText у фрагмента DetailFragment.
Таким образом, произойдет взаимодействие между двумя фрагментами.*/
    @Override
    public void onFragmentInteraction(String link, int col) {
        DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(link, col);
        } else {
            Intent intent = new Intent(getApplicationContext(),
                    DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL, link);
            intent.putExtra(DetailActivity.EXTRA_COLOR, col);
            startActivity(intent);
        }
    }
}