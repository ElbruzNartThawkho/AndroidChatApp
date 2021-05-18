package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Request request = new Request();
                return request ;
            case 1:
                Chat chat = new Chat();
                return chat ;
            case 2:
                Friends friends = new Friends();
                return friends ;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position)
    {
        switch (position){
            case 0:
                return "Request" ;
            case 1:
                return "Chat" ;
            case 2:
                return "Friends" ;
            default:
                return null;
        }
    }
}
