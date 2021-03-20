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

        UserListAsyncTask task = new UserListAsyncTask(new WeakReference<>(UserAdapter), login, token);
        task.execute();

        return view;
    }
}