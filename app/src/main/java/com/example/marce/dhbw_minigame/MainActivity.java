package com.example.marce.dhbw_minigame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.dhbw_minigame.ServerCommunication.RegisterPlayerTask;
import com.example.marce.dhbw_minigame.ServerCommunication.ServerAccessTask;
import com.example.marce.dhbw_minigame.db.GameDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String USER_PREFS = "MyPrefsFile";
    boolean headerInflated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameDBHelper dbHelper = new GameDBHelper(getApplicationContext());

        restoreUserDefaults();
        restoreHighscores();
    }

    public void doNetworkCall(View view){
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("Using network: " + networkInfo.getTypeName());
        System.out.println("Subtype: " + networkInfo.getSubtypeName());
        System.out.println("Reason: " + networkInfo.getReason());

        if (networkInfo.isConnected()){
            ServerAccessTask task = new ServerAccessTask();
            task.execute("\"http://space-labs.appspot.com/repo/465001/highscore.sjs");

        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        restoreUserDefaults();
        restoreHighscores();
    }

    private void restoreUserDefaults() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFS, 0);

        String playername = sharedPreferences.getString("playername", "defaultPlayername");
        int level_value = sharedPreferences.getInt("level", 0);

        TextView username = (TextView)findViewById(R.id.textView_playername);
        username.setText("Welcome back " + playername + "!");

        EditText editText_playername = (EditText)findViewById(R.id.editText_username);
        editText_playername.setText(playername);

        SeekBar level = (SeekBar)findViewById(R.id.seekbar_level);
        level.setProgress(level_value);
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

    public void startGame(View view) {
        savePreferences();

        EditText editText_playername = (EditText)findViewById(R.id.editText_username);
        String playername = editText_playername.getText().toString();

        SeekBar level = (SeekBar)findViewById(R.id.seekbar_level);
        int level_value = level.getProgress();


       RegisterPlayerTask task = new RegisterPlayerTask(this);
       task.execute(playername, String.valueOf(level_value));

        Intent i = new Intent(getApplicationContext(), GameActivity.class);
        i.putExtra("PLAYER_NAME", playername);
        i.putExtra("LEVEL", level_value);
        startActivity(i);
    }

    public void playerRegistrationFinished(String result){

        EditText editText_playername = (EditText)findViewById(R.id.editText_username);
        String playername = editText_playername.getText().toString();

        Toast toast_preferencesSaved = Toast.makeText(getApplicationContext(), "Player " + playername + " with ID " + result + " registered.", Toast.LENGTH_SHORT);
        toast_preferencesSaved.show();
    }

    private void restoreHighscores() {
        GameDBHelper dbHelper = new GameDBHelper(getApplicationContext());

        ArrayList<Highscore> highscoreList = dbHelper.getAllHighscoreEntriesAsList();

        HighscoreListAdapter highscoreListAdapter = new HighscoreListAdapter(this, highscoreList);

        ListView listView = (ListView) findViewById(R.id.listViewHighscores);

        if (!headerInflated){
            View header = (View)getLayoutInflater().inflate(R.layout.highscore_list_header, null);
            listView.addHeaderView(header);
            headerInflated = true;
        }


        listView.setAdapter(highscoreListAdapter);
    }


    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText editText_playername = (EditText)findViewById(R.id.editText_username);
        String playername = editText_playername.getText().toString();

        SeekBar level = (SeekBar)findViewById(R.id.seekbar_level);
        int level_value = level.getProgress();

        editor.putString("playername", playername);
        editor.putInt("level", level_value);
        editor.commit();
    }
}
