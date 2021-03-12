package com.test.gittracker;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

public class SearchActivity extends AppCompatActivity {
    private String target;
    private String searchFor = "user";

    private TextInputEditText textInputEditSearch;
    private TextInputLayout editTextSearch;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textInputEditSearch = findViewById(R.id.textInputEditSearch);
        radioGroup = findViewById(R.id.radioGroup);
        editTextSearch = findViewById(R.id.editTextSearch);

        textInputEditSearch.setOnEditorActionListener(search);
        radioGroup.setOnCheckedChangeListener(switchSearchType);
    }

    private final TextView.OnEditorActionListener search = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            target = textInputEditSearch.getEditableText().toString();
            refresh();
            return true;
        }
    };

    private final RadioGroup.OnCheckedChangeListener switchSearchType = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            editTextSearch.setHint("Search for a" + ((checkedId == R.id.radio_User)?"n user":" repository"));
            editTextSearch.setStartIconDrawable((checkedId == R.id.radio_User)?R.drawable.ic_followers:R.drawable.ic_repository);
        }
    };

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