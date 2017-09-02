package team.khonnan.android.miccook.fragment.ProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.EventUser;
import team.khonnan.android.miccook.event.EventUserProfile;
import team.khonnan.android.miccook.model.UserInfo;


public class FragmentProfileAccount extends Fragment {

    private UserInfo userInfo;


    SharedPreferences sharedPreferences;
    String name, link, avatar, id;
    Toolbar toolbar;

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
    public void receiveInfo(EventUser eventUser) {
        this.userInfo = eventUser.getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_account, container, false);
        EventBus.getDefault().register(this);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {

        EventBus.getDefault().postSticky(new EventUserProfile(userInfo));

        tvName = view.findViewById(R.id.name_user);
        ivProfileImage = view.findViewById(R.id.profile_image);
        toolbar = view.findViewById(R.id.toolbar_profile);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (userInfo != null) {
            Picasso.with(getContext()).load("https://graph.facebook.com/" + userInfo.getIdFb()
                    + "/picture?type=large").into(ivProfileImage);
            tvName.setText(userInfo.getNameFb());
            //tvLink.setText("fb.com/" + userInfo.getIdFb());
        } else {

            //get sharePreference
            sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            name = sharedPreferences.getString("name", "");
            id = sharedPreferences.getString("id", "");
            avatar = sharedPreferences.getString("avatar", "");
            Picasso.with(getContext()).load("https://graph.facebook.com/" + id
                    + "/picture?type=large").into(ivProfileImage);
            tvName.setText(name);
            }

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
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
