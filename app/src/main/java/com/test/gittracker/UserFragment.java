package com.test.gittracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class UserFragment extends Fragment {
    private ListView listView;
    private UserAdapter UserAdapter = new UserAdapter();

    public UserFragment() { }

    public static UserFragment newInstance(int pageNumber, String title) {
        UserFragment fragment = new UserFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        listView = view.findViewById(R.id.listview);
        listView.setAdapter(UserAdapter);
        ViewCompat.setNestedScrollingEnabled(listView, true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String login = sharedPreferences.getString("login", null);
        String token = sharedPreferences.getString("token", null);

        UserAsyncTask task = new UserAsyncTask(new WeakReference<>(UserAdapter), login, token);
        task.execute();

        return view;
    }

//    private void get() {
//        new Thread(() -> {
//            URL url;
//            try {
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
//                String login = sharedPreferences.getString("login", null);
//                String token = sharedPreferences.getString("token", null);
//
//                if (login == null || token == null) {
//                    Log.i("Error", "login or token missing in sharedPreferences");
//                    return;
//                }
//
//                url = new URL("https://api.github.com/user/repos");
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                String basicAuth = "Basic " + Base64.encodeToString((login + ":" + token).getBytes(), Base64.NO_WRAP);
//                urlConnection.setRequestProperty("Authorization", basicAuth);
//                try {
//                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                    String s = readStream(in);
//
//                    JSONArray result = new JSONArray(s);
//
//                    for (int i = 0; i < result.length(); i++) {
//                        Log.i("Git_API", result.get(i).toString());
//                        repositoryAdapter.add(new Repository(result.getJSONObject(i)));
//                    }
//
//                    getActivity().runOnUiThread(() -> repositoryAdapter.notifyDataSetChanged());
//
//                } finally {
//                    urlConnection.disconnect();
//                }
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
}