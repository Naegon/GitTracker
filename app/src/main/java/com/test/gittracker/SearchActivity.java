package com.test.gittracker;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchActivity extends AppCompatActivity {
    private String target;
    private String searchFor = "user";

    private TextInputEditText textInputEditSearch;
    private TextInputLayout editTextSearch;
    private RadioGroup radioGroup;
    private TextView resultCount;
    private ImageView filterIcone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textInputEditSearch = findViewById(R.id.textInputEditSearch);
        radioGroup = findViewById(R.id.radioGroup);
        editTextSearch = findViewById(R.id.editTextSearch);
        resultCount = findViewById(R.id.resultCount);
        filterIcone = findViewById(R.id.filterIcon);

        textInputEditSearch.setOnEditorActionListener(search);
        radioGroup.setOnCheckedChangeListener(switchSearchType);
    }

    private final TextView.OnEditorActionListener search = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            target = textInputEditSearch.getEditableText().toString();
            setView();
            if (radioGroup.getCheckedRadioButtonId() == R.id.radio_User) {
                searchForUser();
            } else searchForRepo();
            return true;
        }
    };

    private final RadioGroup.OnCheckedChangeListener switchSearchType = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            editTextSearch.setHint("Search for a" + ((checkedId == R.id.radio_User)?"n user":" repository"));
            editTextSearch.setStartIconDrawable((checkedId == R.id.radio_User)?R.drawable.ic_followers:R.drawable.ic_repository);
            filterIcone.setImageResource((checkedId == R.id.radio_User)?R.drawable.ic_followers:R.drawable.ic_repository);
        }
    };

    private void setView() {
        TextView nothingToShow = findViewById(R.id.nothingToShow);
        nothingToShow.setVisibility(View.GONE);
        AppCompatImageView logo = findViewById(R.id.logo);
        logo.setVisibility(View.GONE);
    }

    private void searchForUser() {
        TextView nothingToShow = findViewById(R.id.nothingToShow);
        nothingToShow.setVisibility(View.GONE);
        AppCompatImageView logo = findViewById(R.id.logo);
        logo.setVisibility(View.GONE);

        UserAdapter userAdapter = new UserAdapter();
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(userAdapter);
        listView.setVisibility(View.VISIBLE);

        AsyncGitJSONDataForList task = new AsyncGitJSONDataForList(new WeakReference<>(userAdapter), new WeakReference<>(resultCount));
        String url = "https://api.github.com/search/users?q=" + target + "&order=desc&per_page=15";
        task.execute(url);
    }

    private void searchForRepo() {
        RepoAdapter repoAdapter = new RepoAdapter();
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(repoAdapter);
        listView.setVisibility(View.VISIBLE);

        new Thread(() -> {
            URL url;
            try {
                url = new URL("https://api.github.com/users/" + target + "/repos");
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
                        repoAdapter.add(result.get(i));
                    }

                    runOnUiThread(repoAdapter::notifyDataSetChanged);

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