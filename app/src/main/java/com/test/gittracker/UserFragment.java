package com.test.gittracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class UserFragment extends Fragment {
    private ListView listView;
    private UserAdapter userAdapter = new UserAdapter();
    private String target = "Naegon";

    public UserFragment() {
        // Required empty public constructor
    }

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
        listView.setAdapter(userAdapter);
        ViewCompat.setNestedScrollingEnabled(listView, true);

        MainActivity mainActivity = (MainActivity)getActivity();
        String target = mainActivity.getTarget();

        AsyncGitJSONDataForList task = new AsyncGitJSONDataForList(new WeakReference<>(userAdapter));
        String url = "https://api.github.com/search/users?q=" + target + "&order=desc&per_page=15";
        task.execute(url);

        return view;
    }
}