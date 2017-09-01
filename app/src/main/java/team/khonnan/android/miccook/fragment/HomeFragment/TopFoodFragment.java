package team.khonnan.android.miccook.fragment.HomeFragment;

import android.os.Bundle;
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
import team.khonnan.android.miccook.adapters.TopFoodAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.fragment.DetailFoodFragment.FragmentDetailFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.apis.GetTopFood;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.CookModel;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/28/17.
 */

public class TopFoodFragment extends Fragment {

    private RecyclerView rvTopFood;
    private TopFoodAdapter topFoodAdapter;

    List<FoodModel> topFoods = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_food, container, false);
        setupUI(view);
        loadData();
        return view;
    }

    private void setupUI(View view) {
        rvTopFood = view.findViewById(R.id.rv_top_food);
    }

    private void loadData() {
        final GetTopFood getTopFood = RetrofitFactory.getInstance().create(GetTopFood.class);
        getTopFood.getTopFood().enqueue(new Callback<GetFoodRespondModel>() {

            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                List<FoodModel> list = response.body().getFood();
//                topFoods = response.body().getFood();
                Log.d(TAG, "onResponsexxx: "+topFoods);

                for (int i = 0; i < list.size(); i++) {
                    FoodModel food = new FoodModel();
                    food.set_id(list.get(i).get_id());
                    food.setName(list.get(i).getName());
                    food.setAuthor(list.get(i).getAuthor());
                    food.setImageShow(list.get(i).getImageShow());
                    food.setType(list.get(i).getType());
                    food.setTime(list.get(i).getTime());
                    food.setSets(list.get(i).getSets());
                    food.setLevel(list.get(i).getLevel());
                    food.setRating(list.get(i).getRating());
                    food.setRateNum(list.get(i).getRateNum());
                    List<MaterialModel> materials = list.get(i).getMaterial();
                    RealmList<MaterialModel> materialList = new RealmList<>();
                    for (int j = 0; j < materials.size(); j++) {
                        MaterialModel material = new MaterialModel();
                        material.setMatName(materials.get(j).getMatName());
                        material.setMatQuantum(materials.get(j).getMatQuantum());
                        materialList.add(material);
                    }
                    food.setMaterial(materialList);

                    List<CookModel> cooks = list.get(i).getCook();

                    RealmList<CookModel> cookList = new RealmList<>();
                    for (int j = 0; j < cooks.size(); j++) {
                        CookModel cook = new CookModel();
                        cook.setImage(cooks.get(j).getImage());
                        cook.setNote(cooks.get(j).getNote());
                        cookList.add(cook);

                    }

                    food.setCook(cookList);
                    Log.d("ahihi top food", food.toString());
                    topFoods.add(food);
                }
                topFoodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {

            }
        });

        Log.d(TAG, "loadDataABC: " + topFoods);

        topFoodAdapter = new TopFoodAdapter(topFoods,getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager
                (getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvTopFood.setLayoutManager(gridLayoutManager);
        rvTopFood.hasFixedSize();
        rvTopFood.setAdapter(topFoodAdapter);

        topFoodAdapter.setOnClickListener(new View.OnClickListener() {
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
