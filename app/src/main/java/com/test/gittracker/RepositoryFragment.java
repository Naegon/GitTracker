package com.test.gittracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.readStream;

public class RepositoryFragment extends Fragment {
    private ListView listView;
    private RepositoryAdapter repositoryAdapter = new RepositoryAdapter();

    public RepositoryFragment() {
        // Required empty public constructor
    }

    public static RepositoryFragment newInstance(int pageNumber, String title) {
        RepositoryFragment fragment = new RepositoryFragment();
        Bundle args = new Bundle();
        args.putInt("PageNumber", pageNumber);
        args.putString("Title", title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo, container, false);

        listView = view.findViewById(R.id.listview);
        listView.setAdapter(repositoryAdapter);

        Refresh();
        return view;
    }

    private void Refresh() {
        new Thread(() -> {
            URL url;
            try {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                String login = sharedPreferences.getString("login", null);
                String token = sharedPreferences.getString("token", null);

                if (login == null || token == null) {
                    Log.i("Error", "login or token missing in sharedPreferences");
                    return;
                }

                url = new URL("https://api.github.com/user/repos");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                String basicAuth = "Basic " + Base64.encodeToString((login + ":" + token).getBytes(), Base64.NO_WRAP);
                urlConnection.setRequestProperty("Authorization", basicAuth);
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String s = readStream(in);

                    JSONArray result = new JSONArray(s);

                    for (int i = 0; i < result.length(); i++) {
                        Log.i("Git_API", result.get(i).toString());
                        repositoryAdapter.add(new Repository(result.getJSONObject(i)));
                    }

                    getActivity().runOnUiThread(() -> repositoryAdapter.notifyDataSetChanged());

                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}