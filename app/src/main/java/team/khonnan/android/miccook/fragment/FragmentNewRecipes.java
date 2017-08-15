package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.khonnan.android.miccook.R;

/**
 * Created by apple on 8/15/17.
 */

public class FragmentNewRecipes extends Fragment {

    public FragmentNewRecipes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_recipes, container, false);
        return view;
    }
}
