package com.test.gittracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class FragmentAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 2;

    public FragmentAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RepoFragment.newInstance(0, "Repositories");
            case 1:
                return UserFragment.newInstance(1, "Users");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Repositories";
            case 1: return "Users";
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
