package com.example.suvor.lab4;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView videoPlayer;
    Uri myVideoUri;
    Button startButton;
    Button pauseButton;
    Button stopButton;
    RadioGroup radGrp;
    EditText editText;
    Spinner spinner;
    int d=R.raw.lily;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //массив фильмов
        final String[] films = getResources().getStringArray(R.array.films);
        startButton = (Button) findViewById(R.id.start);
        editText=(EditText)  findViewById(R.id.editText);
        pauseButton = (Button) findViewById(R.id.pause);
        stopButton = (Button) findViewById(R.id.stop);
        //Во-первых, чтобы управлять потоком воспроизведения, нам надо получить объект VideoView
        videoPlayer =  (VideoView)findViewById(R.id.videoPlayer);
        radGrp = (RadioGroup) findViewById(R.id.rbGr);
        spinner = (Spinner) findViewById(R.id.films);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, films);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект

                switch ((String)parent.getItemAtPosition(position)){
                    case "Simons Cat":
                        d= R.raw.cat;
                        stopPlay();
                        break;
                    case "Alien":   d=R.raw.alien; stopPlay();
                        break;
                    case "Lily and Snowman":  d=  R.raw.lily; stopPlay();
                        break;
                    case "Hedgehog":  d=  R.raw.hadgehog; stopPlay();
                        break;
                    case "Gift":  d=  R.raw.gift; stopPlay();
                        break;

                }
                //Чтобы указать источник воспроизведения, необходим объект Uri.
                //Строка URI имеет ряд частей: сначала идет Uri-схема (http:// или как здесь android.resource://), \
                // затем название пакета, получаемое через метод getPackageName(), и далее непосредственно название
                // ресурса видео из папки res/raw, которое совпадает с названием файла.
                //Затем этот Uri устанавливается у videoPlayerа
                myVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + d);
                videoPlayer.setVideoURI(myVideoUri);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
        //выбор ресурса
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                TextView selection = (TextView) findViewById(R.id.selection);
                switch(id) {
                    case R.id.rbList:
                        stopPlay();
                        myVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + d);
                        videoPlayer.setVideoURI(myVideoUri);
                        spinner.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.rbInternet:
                        stopPlay();
                        String selector;
                        selector = String.valueOf(editText.getText());
                        videoPlayer.setVideoPath(selector);
                        editText.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }});
        //добавляем контроллер
        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
    }

    private void stopPlay(){
        videoPlayer.stopPlayback();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            videoPlayer.resume();
            //videoPlayer.seekTo(0);
            startButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void play(View view) {
        videoPlayer.start();
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);

    }
    public void pause(View view){
        videoPlayer.pause();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
    public void stop(View view){
        stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoPlayer.isPlaying()) {
            stopPlay();
        }
    }
}