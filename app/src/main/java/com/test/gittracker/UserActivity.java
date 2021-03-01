package com.test.gittracker;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserActivity extends AppCompatActivity {
    private ListView listView;
    private UserAdapter userAdapter = new UserAdapter();
    private String target = "Naegon";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listView = findViewById(R.id.listview);
        listView.setAdapter(userAdapter);
        ViewCompat.setNestedScrollingEnabled(listView, true);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.settings:
                    Log.i("UI", "Settings");
                    return true;
                case R.id.search:
                    Log.i("UI", "Search");
                    return true;
                default:
                    Log.i("UI", "Unknow button");
                    return false;
            }
        });

        handleIntent(getIntent());

        AsyncGitJSONDataForList task = new AsyncGitJSONDataForList(new WeakReference<>(userAdapter));
        String url = "https://api.github.com/users/BenRoecker/followers";
        task.execute(url);
//        Refresh();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("Search", "Intent called");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
            Log.i("Search", "Search action on " + query);
            target = query;
        }
    }

    private void Refresh() {
        new Thread(() -> {
            URL url;
            try {
                url = new URL("https://api.github.com/users/BenRoecker/followers");
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
                        userAdapter.add(result.get(i));
                    }

                    runOnUiThread(() -> userAdapter.notifyDataSetChanged());
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