package utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rinsta.HomeFragment;
import com.example.rinsta.NotificationFragment;
import com.example.rinsta.ProfileFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new NotificationFragment();
        }
        if (position == 1) {
            fragment = new HomeFragment();
        }
        if (position == 2) {
            fragment = new ProfileFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Notifications";
            case 1: return "Home";
            case 2: return "Profile";
            default: return null;
        }
    }
}
