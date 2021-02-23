package com.test.gittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    MyAdapter myAdapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        listView.setAdapter(myAdapter);
        Refresh();
    }

    private void Refresh() {
        new Thread(() -> {
            URL url;
            try {
                url = new URL("https://api.github.com/users/naegon/repos");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                String basicAuth = "Basic " + Base64.encodeToString(("Naegon:zFqi58Cmvw").getBytes(), Base64.NO_WRAP);
//                urlConnection.setRequestProperty ("Authorization", basicAuth);
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String s = readStream(in);
                    Log.i("Git_API", s);

                    JSONArray result = new JSONArray(s);

                    for (int i = 0; i < result.length(); i++) {
                        Log.i("Git_API", result.get(i).toString());
                        myAdapter.add(result.get(i));
                    }

                    runOnUiThread(() -> myAdapter.notifyDataSetChanged());

                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}