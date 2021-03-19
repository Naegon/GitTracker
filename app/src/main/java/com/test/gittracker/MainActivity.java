package com.test.gittracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSettings();
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPreferences.getString("login", null) == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            Intent intent;
            switch (id) {
                case R.id.settings:
                    intent = new Intent(this, SettingsActivity.class);
                    break;
                case R.id.search:
                    intent = new Intent(this, SearchActivity.class);
                    break;
                default:
                    Log.i("UI", "Unknow button");
                    return false;
            }
            startActivity(intent);
            return true;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        Toast.makeText(this, "Welcome back " + sharedPreferences.getString("login", null), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }

    public void loadSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sp.getBoolean("darkTheme", false);
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}