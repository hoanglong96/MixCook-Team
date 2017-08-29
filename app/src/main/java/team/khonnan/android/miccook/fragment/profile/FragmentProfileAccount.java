package team.khonnan.android.miccook.fragment.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.EventUser;
import team.khonnan.android.miccook.model.UserInfo;

import static com.facebook.login.widget.ProfilePictureView.TAG;


public class FragmentProfileAccount extends Fragment {

    private UserInfo userInfo;


    SharedPreferences sharedPreferences;
    String name,link,avatar,id;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_account_circle_black_24dp,
            R.drawable.ic_burst_mode_black_24dp,
    };

    private TextView tvName;
    private ImageView ivProfileImage;


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
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {

        tvName = view.findViewById(R.id.name_user);
        ivProfileImage = view.findViewById(R.id.profile_image);

        if(userInfo != null) {
            Picasso.with(getContext()).load("http://graph.facebook.com/" + userInfo.getIdFb()
                    + "/picture?type=large").into(ivProfileImage);
            tvName.setText(userInfo.getNameFb());
            //tvLink.setText("fb.com/" + userInfo.getIdFb());
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
            //tvLink.setText("fb.com/" + id);
        }

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Log.d(TAG, "setupUI: " + id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ProfileFragment(), "Profile");
        adapter.addFrag(new RecipesUserFragment(), "Recipes user");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
