package team.khonnan.android.miccook.fragment.DetailFoodFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.MainActivity;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.networks.UpdateFavoriteModel;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.apis.UpdateFavorite;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getUserProfileModels.UserProfileModel;

/**
 * Created by apple on 8/22/17.
 */

public class FragmentDetailFood extends Fragment {

    private TextView tvNameFood, tvTypeFood;
    private ImageView ivFood, ivFavorite;
    private LinearLayout lnRating;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    private FoodModel foodModel;

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
        }


    List<String> listeFavorite = new ArrayList<>();
    boolean isFavorited = false;

    public void setupUI(View view) {
        EventBus.getDefault().register(this);

        tvNameFood = view.findViewById(R.id.toolbar_title);
        tvTypeFood = view.findViewById(R.id.type_food);
        ivFood = view.findViewById(R.id.header);

        ivFavorite = view.findViewById(R.id.iv_favorite_button);
        listeFavorite = MainActivity.userProfileModel.getListFavorite();
        for (int i = 0; i < listeFavorite.size(); i++) {
            if (listeFavorite.get(i).equals(foodModel.get_id())) {
                isFavorited = true;
                ivFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                break;
            }
        }


        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorited) {
                    Iterator<String> i = listeFavorite.iterator();
                    while (i.hasNext()) {
                        String s = i.next();
                        if (s.equals(foodModel.get_id())) {
                            i.remove();
                            break;
                        }
                    }
                    ivFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    isFavorited = false;
                } else {
                    listeFavorite.add(foodModel.get_id());
                    ivFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    isFavorited = true;
                }

                UpdateFavoriteModel updateFavoriteModel = new UpdateFavoriteModel(listeFavorite);
                final UpdateFavorite updateFavorite = RetrofitFactory.getInstance().create(UpdateFavorite.class);
                Call<UserProfileModel> call = updateFavorite.updateFavorite(MainActivity.userProfileModel.getIdFb(), updateFavoriteModel);
                call.enqueue(new Callback<UserProfileModel>() {

                    @Override
                    public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                        MainActivity.userProfileModel = response.body();
                        }

                    @Override
                    public void onFailure(Call<UserProfileModel> call, Throwable t) {

                    }
                });
            }
        });


        toolbar = view.findViewById(R.id.toolbar_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        tvNameFood.setText(foodModel.getName());
        int typeNumber = foodModel.getType();
        String type = null;
        if (typeNumber == 1) {
            type = "Main dishes";
        } else if (typeNumber == 2) {
            type = "Breakfast";
        } else if (typeNumber == 3) {
            type = "Snacks";
        } else if (typeNumber == 4) {
            type = "Cake";
        } else if (typeNumber == 5) {
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
        tabOne.setText("Ingredients");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ingredients_detail_food, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("How");
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
