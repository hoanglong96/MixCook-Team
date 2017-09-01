package team.khonnan.android.miccook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.MainActivity;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.apis.GetFavoriteFood;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by apple on 8/26/17.
 */

public class FragmentFavorites extends Fragment {

    Toolbar toolbar;
    ImageView ivBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        toolbar = view.findViewById(R.id.toolbar_fav);
        toolbar.setTitle("Món ăn yêu thích");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                toolbar.setTitle("Cook Mix");
            }
        });
        Log.d(TAG, "onCreateView: jumpin");
        getFavorite();
        return view;
    }

    private static final String TAG = "FragmentFavorites";
    List<FoodModel> listFavorite;
    void getFavorite(){
        final GetFavoriteFood getFavoriteFood = RetrofitFactory.getInstance().create(GetFavoriteFood.class);
        Log.d(TAG, "getFavorite: "+MainActivity.userProfileModel.getIdFb());
        getFavoriteFood.getFavoriteFood(MainActivity.userProfileModel.getIdFb()).enqueue(new Callback<GetFoodRespondModel>() {

            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                listFavorite = response.body().getFood();
                Log.d(TAG, "onResponse: "+listFavorite);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }


}
