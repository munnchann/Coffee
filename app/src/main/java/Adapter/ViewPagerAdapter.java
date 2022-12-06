package Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uipart1.Activity.CartActivity;
import com.example.uipart1.Activity.MainActivity;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
//                return new Home();
            case 1:
//                return new MainActivity();
            case 2:
//                return new CartActivity();
            case 3:
//                return new Setting();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
