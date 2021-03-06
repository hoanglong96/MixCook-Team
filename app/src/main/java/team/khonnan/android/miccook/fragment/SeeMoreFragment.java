package team.khonnan.android.miccook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.FoodAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.fragment.DetailFoodFragment.FragmentDetailFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 11/08/2017.
 */

public class SeeMoreFragment extends Fragment {

    @BindView(R.id.rv_seemore)
    RecyclerView rvSeemore;
    @BindView(R.id.toolbar_see_more)
    public Toolbar toolbar;

    private FoodAdapter foodAdapter;
    private FoodModel foodModel;
    private List<FoodModel> foodModelList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_more, container, false);
        ButterKnife.bind(this, view);
        loadData();

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                toolbar.setTitle("Cook Mix");
            }
        });
//        ImageView backSeeMore = view.findViewById(R.id.back_see_more);
//        backSeeMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPress();
//            }
//        });

        return view;
    }

    public void onBackPress(){
        getActivity().onBackPressed();
    }

    public void loadData(){

        ///Check type

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            foodModelList = (List<FoodModel>) getArguments().getSerializable("type");
            String title = getArguments().getString("title");
            toolbar.setTitle(title);
            foodAdapter = new FoodAdapter(foodModelList,getContext());
            rvSeemore.setAdapter(foodAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager
                    (getContext(), 2, GridLayoutManager.VERTICAL, false);

            rvSeemore.setLayoutManager(gridLayoutManager);

            foodAdapter.setOnItemClick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FoodModel foodModel = (FoodModel) view.getTag();
                    EventBus.getDefault().postSticky(new OnClickFood(foodModel));
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailFood(),R.id.drawer_layout);
                }
            });
        }

        toolbar.setTitleTextColor(Color.WHITE);
    }
}
