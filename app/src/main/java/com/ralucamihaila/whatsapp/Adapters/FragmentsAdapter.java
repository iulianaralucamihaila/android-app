package com.ralucamihaila.whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ralucamihaila.whatsapp.Fragments.ChatsFragment;
import com.ralucamihaila.whatsapp.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ChatsFragment();
            case 1: return new StatusFragment();
//            case 2: return new SettingsFragment();
            default: return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if (position == 0) {
            title = "CHATS";
        }
        if (position == 1) {
            title = "STATUS";
        }
//        if (position == 2) {
//            title = "SETTINGS";
//        }
        return title;
    }
}
