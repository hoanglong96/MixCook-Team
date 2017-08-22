package team.khonnan.android.miccook.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.EventUser;
import team.khonnan.android.miccook.model.UserInfo;


public class FragmentProfileAccount extends Fragment {

    private UserInfo userInfo;
    @BindView(R.id.profile_image)
    ImageView ivProfileImage;
    @BindView(R.id.txt_name)
    TextView tvName;
    @BindView(R.id.link_face)
    TextView tvLink;

    SharedPreferences sharedPreferences;
    String name,link,avatar,id;

    @BindView(R.id.close)
    ImageView close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Subscribe(sticky = true)
    public void receiveInfo(EventUser eventUser){
            this.userInfo = eventUser.getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_account, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        setupUI();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void setupUI() {
        if(userInfo != null) {
            Picasso.with(getContext()).load("http://graph.facebook.com/" + userInfo.getIdFb()
                    + "/picture?type=large").into(ivProfileImage);
            tvName.setText(userInfo.getNameFb());
            tvLink.setText("fb.com/" + userInfo.getIdFb());
        }else{

            //get sharePreference
            sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            name = sharedPreferences.getString("name","");
            id = sharedPreferences.getString("id","");
            avatar = sharedPreferences.getString("avatar","");

            Log.d("id", "setupUI: " + id);

            Picasso.with(getContext()).load("https://graph.facebook.com/" + id
                    + "/picture?type=large").into(ivProfileImage);
            tvName.setText(name);
            tvLink.setText("fb.com/" + id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
