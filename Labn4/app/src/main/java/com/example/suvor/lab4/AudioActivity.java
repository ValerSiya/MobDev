package com.example.suvor.lab4;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AudioActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    Button startButton, pauseButton, stopButton;
    SeekBar volumeControl;
    SeekBar progressControl;
    AudioManager audioManager;
    Runnable runnable;


    Handler handler;
    Button chooseButton;
    Spinner spinner;
    Boolean changed = false;
    //String[] cities = {"Москва", "Самара", "Вологда", "Волгоград", "Саратов", "Воронеж"};
    TextView selection;
    int d= R.raw.stumblin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        final String[] music = getResources().getStringArray(R.array.audio);
        selection = (TextView) findViewById(R.id.selection);
        spinner = (Spinner) findViewById(R.id.music);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, music);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        progressControl = (SeekBar) findViewById(R.id.progressControl);
        volumeControl = (SeekBar) findViewById(R.id.volumeControl);
        handler=new Handler();
        chooseButton=(Button) findViewById(R.id.choose);
        startButton = (Button) findViewById(R.id.start);
        pauseButton = (Button) findViewById(R.id.pause);
        stopButton = (Button) findViewById(R.id.stop);
        //Для управления громкостью звука применяется класс AudioManager.
        // А в с помощью вызова audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        // в качестве второго параметра можно передать нужное значение громкости.
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);
        progressControl.setProgress(curValue);

        progressControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if (input) {
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);


        mPlayer = MediaPlayer.create(this, d);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                int key;
                switch ((String)parent.getItemAtPosition(position)){
                    case "Roxette - How do you do":  changed =true;d = R.raw.howdoyoudo;
                        break;
                    case "Savage - Only you":  changed =true;d = R.raw.onlyyou;
                        break;
                    case "Radiorama - Yeti": changed =true;d=R.raw.yeti;
                        break;
                    case "Chris Norman,Suzi Quatro - Stumblin": changed =true;d=R.raw.stumblin;
                        break;
                    case "Afric Simone - Hafanana": changed =true; d=R.raw.shala;
                        break;

                }

                String item = (String)parent.getItemAtPosition(position);
                selection.setText(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }


    //метод playCycle для поиска и времени
    public void playCycle() {
        progressControl.setProgress(mPlayer.getCurrentPosition());
        if (mPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            //Handler может использоваться для планирования выполнения кода в некоторый момент в будущем.
            // Также класс может использоваться для передачи кода, который должен выполняться в другом программном потоке.
            //Как и сообщения, Runnable может быть выполнен с задержкой (postDelayed)
            handler.postDelayed(runnable, 1000);
        }
    }
    private void stopPlay(){
        mPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            startButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void play(View view){
        //для обновления когда песня закончилась
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        mPlayer.start();
        progressControl.setMax(mPlayer.getDuration());
        playCycle();
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }
    public void pause(View view){

        mPlayer.pause();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
    public void stop(View view){
        stopPlay();
    }
    public void choose(View view){
        stopPlay();
        mPlayer = MediaPlayer.create(this, d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }
}