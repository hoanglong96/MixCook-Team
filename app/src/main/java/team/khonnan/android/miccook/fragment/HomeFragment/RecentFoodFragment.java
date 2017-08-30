package team.khonnan.android.miccook.fragment.HomeFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.NewRecipesAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.fragment.DetailFood.FragmentDetailFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.apis.FoodApi;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.CookModel;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/28/17.
 */

public class RecentFoodFragment extends Fragment {

    private RecyclerView rvRecentFood;
    private NewRecipesAdapter newRecipesAdapter;

    List<FoodApi.FoodList> list = new ArrayList<>();
    List<FoodModel> recentFoods = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_food, container, false);
        setupUI(view);
        loadData();
        return view;
    }

    private void setupUI(View view) {
        rvRecentFood = view.findViewById(R.id.rv_recent_food);
    }

    private void loadData() {
        RetrofitFactory retrofitFactory = new RetrofitFactory("http://mixcookteam.herokuapp.com/getFood/");
        FoodApi service = retrofitFactory.create(FoodApi.class);
        Call<FoodApi.Food> call = service.callFood();
        call.enqueue(new Callback<FoodApi.Food>() {
            @Override
            public void onResponse(@NonNull Call<FoodApi.Food> call, @NonNull Response<FoodApi.Food> response) {
                list = response.body().getFoodList();

                for (int i = 0; i < list.size(); i++) {
                    FoodModel food = new FoodModel();
                    food.set_id(list.get(i).get_id());
                    food.setName(list.get(i).getName());
                    food.setAuthor(list.get(i).getAuthor());
                    food.setImageShow(list.get(i).getImageShow());
                    food.setType(Integer.parseInt(list.get(i).getType()));
                    food.setTime(list.get(i).getTime());
                    food.setSets(list.get(i).getSets());
                    food.setLevel(list.get(i).getLevel());
                    food.setRating(list.get(i).getRating());
                    food.setRateNum(list.get(i).getRateNum());
                    List<FoodApi.Material> materials = list.get(i).getMaterials();
                    RealmList<MaterialModel> materialList = new RealmList<>();
                    for (int j = 0; j < materials.size(); j++) {
                        MaterialModel material = new MaterialModel();
                        material.setMatName(materials.get(j).getMatName());
                        material.setMatQuantum(materials.get(j).getMatQuantum());
                        materialList.add(material);
                    }
                    food.setMaterial(materialList);

                    List<FoodApi.Cook> cooks = list.get(i).getCooks();

                    RealmList<CookModel> cookList = new RealmList<>();
                    for (int j = 0; j < cooks.size(); j++) {
                        CookModel cook = new CookModel();
                        cook.setImage(cooks.get(j).getImage());
                        cook.setNote(cooks.get(j).getNote());
                        cookList.add(cook);

                    }

                    food.setCook(cookList);
                    Log.d("ahihi", food.toString());
                    recentFoods.add(food);
                    newRecipesAdapter.notifyDataSetChanged();
                }
//                EventBus.getDefault().post(new EventReady());
                Log.d(TAG, "onResponse: " + recentFoods);

            }

            @Override
            public void onFailure(@NonNull Call<FoodApi.Food> call, Throwable t) {
                Log.d("No No ", t.toString());

            }
        });

        Log.d(TAG, "loadDataABC: " + recentFoods);

        newRecipesAdapter = new NewRecipesAdapter(recentFoods,getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager
                (getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvRecentFood.setLayoutManager(gridLayoutManager);
        rvRecentFood.hasFixedSize();
        rvRecentFood.setAdapter(newRecipesAdapter);

        newRecipesAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodModel foodModel = (FoodModel) view.getTag();
                Log.d(TAG, "onClickNewRecipes: " + view.getTag());
                EventBus.getDefault().postSticky(new OnClickFood(foodModel));
                ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailFood(),R.id.drawer_layout);
            }
        });

    }


}
