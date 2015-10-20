package com.example.marce.dhbw_minigame.ServerCommunication;

import android.os.AsyncTask;

import com.example.marce.dhbw_minigame.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by marce on 08.10.2015.
 */
public class RegisterPlayerTask extends AsyncTask<String, Void, String> {

    private MainActivity mainActivity;
    public RegisterPlayerTask(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            System.out.println(params[0]);
            System.out.println(params[1]);

            InputStream inputStream = null;
            URL url = new URL("http://space-labs.appspot.com/repo/465001/player_add.sjs" + "?name=" + params[0] + "&level=" + params[1] + "&client=myClient");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            inputStream = connection.getInputStream();
            String bodyContent = convertStreamToString(inputStream);

            try {
                JSONObject jsonObject = new JSONObject(bodyContent);
                return jsonObject.getString("PlayerID");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(connection.getResponseCode());
            connection.disconnect();

        } catch (MalformedURLException e) {
            System.out.println("wrong url");
            e.printStackTrace();
            return e.toString();
        } catch (ProtocolException e) {
            System.out.println("wrong protocol");
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            System.out.println("wrong IO");
            e.printStackTrace();
            return e.toString();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mainActivity.playerRegistrationFinished(result);
    }

    private String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
