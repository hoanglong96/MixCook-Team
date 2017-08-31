package team.khonnan.android.miccook.fragment.ProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.model.UserInfo;

/**
 * Created by apple on 8/27/17.
 */

public class ProfileFragment extends Fragment {

    private UserInfo userInfo;
    private SharedPreferences sharedPreferences;

    TextView tvMail;
    TextView tvLinkFace;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        setupUI(view);
        return view;
    }

    public void setupUI(View view){
        sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        String email = sharedPreferences.getString("email","");

        tvLinkFace = view.findViewById(R.id.link);
        tvMail = view.findViewById(R.id.tv_gmail);

        tvLinkFace.setText("facebook.com/" + id);
        tvMail.setText(email);


    }
}
