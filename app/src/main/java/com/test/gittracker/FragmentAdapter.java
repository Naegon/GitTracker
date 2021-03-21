package com.test.gittracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class FragmentAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 2;
    private Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RepositoryFragment.newInstance(0, context.getString(R.string.repositories));
            case 1:
                return UserFragment.newInstance(1, context.getString(R.string.following));
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return context.getString(R.string.repositories);
            case 1: return context.getString(R.string.following);
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
