package com.test.gittracker;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;

public class SearchActivity extends AppCompatActivity {
    private String target = "Naegon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextInputEditText textInputEditSearch = findViewById(R.id.textInputEditSearch);
        textInputEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                target = textInputEditSearch.getEditableText().toString();
                refresh();
                return true;
            }
        });
    }

    private void refresh() {
        TextView nothingToShow = findViewById(R.id.nothingToShow);
        nothingToShow.setVisibility(View.GONE);
        AppCompatImageView logo = findViewById(R.id.logo);
        logo.setVisibility(View.GONE);

        UserAdapter userAdapter = new UserAdapter();
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(userAdapter);
        listView.setVisibility(View.VISIBLE);

        AsyncGitJSONDataForList task = new AsyncGitJSONDataForList(new WeakReference<>(userAdapter));
        String url = "https://api.github.com/search/users?q=" + target + "&order=desc&per_page=15";
        task.execute(url);
    }
}