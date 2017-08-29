package team.khonnan.android.miccook.fragment.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.khonnan.android.miccook.R;

/**
 * Created by apple on 8/27/17.
 */

class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        return view;
    }
}
