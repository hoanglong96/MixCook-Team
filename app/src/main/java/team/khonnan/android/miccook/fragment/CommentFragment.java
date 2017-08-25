package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import team.khonnan.android.miccook.R;

/**
 * Created by apple on 8/25/17.
 */

public class CommentFragment extends Fragment {

    LinearLayout lnRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        return view;
    }

    public void setupUI(View view){
        
    }
}
