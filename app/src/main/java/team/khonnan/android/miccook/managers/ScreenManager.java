package team.khonnan.android.miccook.managers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.Serializable;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by Admins on 7/19/2017.
 */

public class ScreenManager {
    public static void openFragment(FragmentManager fragmentManager,
                                    Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void openFragment(FragmentManager fragmentManager,
                                    Fragment fragment, int layoutID,List<FoodModel> foodModels,String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", (Serializable) foodModels);
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
        fragmentTransaction.commit();
    }
}
