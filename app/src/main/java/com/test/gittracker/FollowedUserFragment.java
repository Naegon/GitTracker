package com.test.gittracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.Set;

public class FollowedUserFragment extends Fragment {
    private ListView listView;
    private FollowedUserAdapter followedUserAdapter = new FollowedUserAdapter();
    private String target = "Naegon";

    public FollowedUserFragment() {
        // Required empty public constructor
    }

    public static FollowedUserFragment newInstance(int pageNumber, String title) {
        FollowedUserFragment fragment = new FollowedUserFragment();
        Bundle args = new Bundle();
        args.putInt("PageNumber", pageNumber);
        args.putString("Title", title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        listView = view.findViewById(R.id.listview);
        listView.setAdapter(followedUserAdapter);
        ViewCompat.setNestedScrollingEnabled(listView, true);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> followedUser = sharedPreferences.getStringSet("followed_user", null);
        if (followedUser == null) {
            Log.i("GitTracker", "No followed user yet");
            return view;
        }

        for (String string: followedUser) {
            AsyncFollowedUser task = new AsyncFollowedUser(new WeakReference<>(followedUserAdapter));
            task.execute(string);
        }

//        AsyncGitJSONDataForList task = new AsyncGitJSONDataForList(new WeakReference<>(userAdapter));
//        String url = "https://api.github.com/search/users?q=" + target + "&order=desc&per_page=15";
//        task.execute(url);

        return view;
    }
}