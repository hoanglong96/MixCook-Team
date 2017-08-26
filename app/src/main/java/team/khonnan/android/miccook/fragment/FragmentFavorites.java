package team.khonnan.android.miccook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import team.khonnan.android.miccook.R;

/**
 * Created by apple on 8/26/17.
 */

public class FragmentFavorites extends Fragment {

    Toolbar toolbar;
    ImageView ivBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        toolbar = view.findViewById(R.id.toolbar_fav);
        toolbar.setTitle("Món ăn yêu thích");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                toolbar.setTitle("Cook Mix");
            }
        });
        return view;
    }
}
