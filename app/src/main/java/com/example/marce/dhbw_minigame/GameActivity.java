package com.example.marce.dhbw_minigame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.dhbw_minigame.db.GameDBHelper;

import java.util.Random;

/**
 * Created by marce on 01.09.2015.
 */
public class GameActivity extends AppCompatActivity  {

    Button button_topLeft;
    Button button_topRight;
    Button button_bottomLeft;
    Button button_bottomRight;
    int score;
    String playername;
    int level;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playername = extras.getString("PLAYER_NAME");
            level = extras.getInt("LEVEL");
        }
        initButtons();
        startTimer(5);
    }

    private void startTimer(int seconds) {
        CountDownTimer counterTimer = new CountDownTimer(seconds * 1000, 1000) {
            public void onFinish() {
                //code to execute when time finished
                TextView gameCounter = (TextView)findViewById(R.id.startCounter_value);
                gameCounter.setText("" + "0" + ":" + "00");
                Toast toast = Toast.makeText(getApplicationContext(), "Game started", Toast.LENGTH_SHORT);
                toast.show();
                enableButtons(true);
                startGame();
            }

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                TextView gameCounter = (TextView)findViewById(R.id.startCounter_value);

                if (seconds < 10) {
                    gameCounter.setText("" + minutes + ":0" + seconds);
                } else {
                    gameCounter.setText("" + minutes + ":" + seconds);
                }
            }
        };
        counterTimer.start();
    }

    private void initButtons() {
        Button button_topLeft       = (Button)findViewById(R.id.button_TopLeft);
        Button button_topRight      = (Button)findViewById(R.id.button_TopRight);
        Button button_bottomLeft    = (Button)findViewById(R.id.button_BottomLeft);
        Button button_bottomRight   = (Button)findViewById(R.id.button_BottomRight);

        enableButtons(false);

        button_topLeft.setOnClickListener(onClickListener);
        button_topRight.setOnClickListener(onClickListener);
        button_bottomLeft.setOnClickListener(onClickListener);
        button_bottomRight.setOnClickListener(onClickListener);

        button_topLeft.setBackgroundColor(Color.GRAY);
        button_topRight.setBackgroundColor(Color.GRAY);
        button_bottomLeft.setBackgroundColor(Color.GRAY);
        button_bottomRight.setBackgroundColor(Color.GRAY);
    }
    private void enableButtons(boolean value){
        Button button_topLeft       = (Button)findViewById(R.id.button_TopLeft);
        Button button_topRight      = (Button)findViewById(R.id.button_TopRight);
        Button button_bottomLeft    = (Button)findViewById(R.id.button_BottomLeft);
        Button button_bottomRight   = (Button)findViewById(R.id.button_BottomRight);

        button_topLeft.setEnabled(value);
        button_topRight.setEnabled(value);
        button_bottomLeft.setEnabled(value);
        button_bottomRight.setEnabled(value);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            Button button;
            TextView textView_score = (TextView)findViewById(R.id.game_Score);
            switch(v.getId()){
                case R.id.button_TopLeft:
                    button = (Button) findViewById(R.id.button_TopLeft);
                    if (button.getText().toString().equals("Click me")){
                        System.out.println("geht");
                        highlightRandomButton();
                        score++;
                        textView_score.setText(String.valueOf(score));
                    } else {
                        stopGame();
                    }
                    break;
                case R.id.button_TopRight:
                    button = (Button) findViewById(R.id.button_TopRight);
                    if (button.getText().toString().equals("Click me")){
                        System.out.println("geht");
                        highlightRandomButton();
                        score++;
                        textView_score.setText(String.valueOf(score));
                    } else {
                        stopGame();
                    }
                    break;
                case R.id. button_BottomLeft:
                    button = (Button) findViewById(R.id.button_BottomLeft);
                    if (button.getText().toString().equals("Click me")){
                        highlightRandomButton();
                        score++;
                        textView_score.setText(String.valueOf(score));
                    } else {
                        stopGame();
                    }
                    break;
                case R.id. button_BottomRight:
                    button = (Button) findViewById(R.id.button_BottomRight);
                    if (button.getText().toString().equals("Click me")){
                        System.out.println("geht");
                        highlightRandomButton();
                        score++;
                        textView_score.setText(String.valueOf(score));
                    } else {
                        stopGame();
                    }
                    break;
            }
        }
    };

    private void stopGame(){
        Toast toastScore = Toast.makeText(getApplicationContext(), "You scored " + score, Toast.LENGTH_SHORT);
        toastScore.show();
        storeHighscore();
        enableButtons(false);
    }
    private void storeHighscore(){
        GameDBHelper dbHelper = new GameDBHelper(getApplicationContext());
        dbHelper.addHighscore(playername, level, score);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startGame(){
        score = 0;
        highlightRandomButton();

    }
    private void highlightRandomButton(){
        int randomNumber = new Random().nextInt(4);


        Button button_topLeft       = (Button)findViewById(R.id.button_TopLeft);
        Button button_topRight      = (Button)findViewById(R.id.button_TopRight);
        Button button_bottomLeft    = (Button)findViewById(R.id.button_BottomLeft);
        Button button_bottomRight   = (Button)findViewById(R.id.button_BottomRight);

        switch (randomNumber){
            case (0):
                button_topLeft.setBackgroundColor(Color.GREEN);
                button_topRight.setBackgroundColor(Color.GRAY);
                button_bottomLeft.setBackgroundColor(Color.GRAY);
                button_bottomRight.setBackgroundColor(Color.GRAY);

                button_topLeft.setText("Click me");
                button_topRight.setText("NOO!!!");
                button_bottomLeft.setText("NOO!!!");
                button_bottomRight.setText("NOO!!!");
                break;
            case 1:
                button_topLeft.setBackgroundColor(Color.GRAY);
                button_topRight.setBackgroundColor(Color.RED);
                button_bottomLeft.setBackgroundColor(Color.GRAY);
                button_bottomRight.setBackgroundColor(Color.GRAY);

                button_topLeft.setText("NOO!!!");
                button_topRight.setText("Click me");
                button_bottomLeft.setText("NOO!!!");
                button_bottomRight.setText("NOO!!!");
                break;
            case 2:
                button_topLeft.setBackgroundColor(Color.GRAY);
                button_topRight.setBackgroundColor(Color.GRAY);
                button_bottomLeft.setBackgroundColor(Color.BLUE);
                button_bottomRight.setBackgroundColor(Color.GRAY);

                button_topLeft.setText("NOO!!!");
                button_topRight.setText("NOO!!!");
                button_bottomLeft.setText("Click me");
                button_bottomRight.setText("NOO!!!");
                break;
            case 3:
                button_topLeft.setBackgroundColor(Color.GRAY);
                button_topRight.setBackgroundColor(Color.GRAY);
                button_bottomLeft.setBackgroundColor(Color.GRAY);
                button_bottomRight.setBackgroundColor(Color.YELLOW);

                button_topLeft.setText("NOO!!!");
                button_topRight.setText("NOO!!!");
                button_bottomLeft.setText("NOO!!!");
                button_bottomRight.setText("Click me");
                break;
        }
    }
}
