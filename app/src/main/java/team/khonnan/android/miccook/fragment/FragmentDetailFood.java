package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/22/17.
 */

public class FragmentDetailFood extends Fragment {

    private TextView tvNameFood , tvTypeFood;
    private ImageView ivFood;
    private LinearLayout lnRating;

    private TabLayout tabLayout;
    private ViewPager viewPager;


    private FoodModel foodModel;
    private int[] tabIcons = {
            R.drawable.cart,
            R.drawable.ic_favorite_black_24dp,
            R.drawable.ic_chat_black_24dp
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_food, container, false);

        setupUI(view);
        setupTabIcons();
        return view;
    }


    @Subscribe(sticky = true)
    public void onEvent(OnClickFood onClickFood) {
        foodModel = onClickFood.getFoodModel();
        Log.d(TAG, "onEvent: " + foodModel);
    }

    public void setupUI(View view){
        EventBus.getDefault().register(this);

        tvNameFood = view.findViewById(R.id.toolbar_title);
        tvTypeFood = view.findViewById(R.id.type_food);
        ivFood = view.findViewById(R.id.header);
//        lnRating = view.findViewById(R.id.rating_food);
//
//
//        lnRating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: abc");
//                ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentRating(),R.id.drawer_layout );
//            }
//        });


        Log.d(TAG, "onCreateView: " + foodModel.getName());
        tvNameFood.setText(foodModel.getName());
        int typeNumber = foodModel.getType();
        String type = null;
        if(typeNumber == 1){
            type = "Main dishes";
        }else if(typeNumber == 2){
            type = "Breakfast";
        }else if(typeNumber == 3){
            type = "Snacks";
        }else if(typeNumber == 4){
            type = "Cake";
        }else if(typeNumber == 5){
            type = "Drink";
        }

        tvTypeFood.setText(type);
        Picasso.with(getContext()).load(foodModel.getImageShow()).into(ivFood);


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.icon_unselected),
                getResources().getColor(R.color.icon_selected)
        );
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText("Nguyên liệu");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ingredients, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Cách làm");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cook, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText("Comment");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_chat_black_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NguyenLieuFragment(), "Nguyên liệu");
        adapter.addFragment(new CookFragment(), "Cách làm");
        adapter.addFragment(new CommentFragment(), "Comment");
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
