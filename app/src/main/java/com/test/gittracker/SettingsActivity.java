package com.test.gittracker;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            findPreference("darkTheme").setOnPreferenceChangeListener((preference, newValue) -> {
                boolean darkTheme = (Boolean)newValue;
                if (darkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                requireActivity().recreate();

                return true;
            });

            findPreference("language").setOnPreferenceChangeListener((preference, newValue) -> {
                Toast.makeText(getContext(), (CharSequence) newValue, Toast.LENGTH_SHORT).show();

                Locale myLocale = new Locale((String) newValue); // Set Selected Locale
                Locale.setDefault(myLocale); // set new locale as default
                Configuration config = new Configuration(); // get Configuration
                config.setLocale(myLocale); // set config locale as selected locale
                getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics()); // Update the config

                requireActivity().recreate();
                return true;
            });
        }
    }
}