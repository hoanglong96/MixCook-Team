package team.khonnan.android.miccook.fragment.ProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.FoodUserAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.fragment.DetailFoodFragment.FragmentDetailFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.apis.GetFoodByUser;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by apple on 8/27/17.
 */

public class RecipesUserFragment extends Fragment {

    //List<FoodModel> foodUserList = new ArrayList<>();
    List<FoodModel> foodModelList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String id;
    RecyclerView rvFoodUser;
    FoodUserAdapter adapterFoodUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_user, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id","");
        setupUI(view);
        return view;
    }

    public void setupUI(View view){
        getUserFood();
        rvFoodUser = view.findViewById(R.id.rv_food_user);
    }

    private static final String TAG = "FragmentProfileAccount";
    public void getUserFood() {
        final GetFoodByUser getFoodByUser = RetrofitFactory.getInstance().create(GetFoodByUser.class);
        getFoodByUser.getFoodByUser(id).enqueue(new Callback<GetFoodRespondModel>() {


            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodModelList = response.body().getFood();
                adapterFoodUser = new FoodUserAdapter(foodModelList,getContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager
                        (getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvFoodUser.setLayoutManager(gridLayoutManager);
                rvFoodUser.hasFixedSize();
                rvFoodUser.setAdapter(adapterFoodUser);

                adapterFoodUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FoodModel foodModel = (FoodModel) view.getTag();
                        EventBus.getDefault().postSticky(new OnClickFood(foodModel));
                        ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailFood(),R.id.drawer_layout);
                    }
                });
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {

            }
        });
    }
}
