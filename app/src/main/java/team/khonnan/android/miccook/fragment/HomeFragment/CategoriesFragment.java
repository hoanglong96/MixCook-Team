package team.khonnan.android.miccook.fragment.HomeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.CatalogAdapter;
import team.khonnan.android.miccook.fragment.SeeMoreFragment;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.model.Type;
import team.khonnan.android.miccook.networks.apis.GetFoodByType;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by apple on 8/28/17.
 */

public class CategoriesFragment extends Fragment {

    //catalog
    private List<Type> typeList = new ArrayList<>();
    private CatalogAdapter catalogAdapter;
    private RecyclerView rvCatalog;

    //List food type
    List<FoodModel> foodMonChinh = new ArrayList<>();
    List<FoodModel> foodAnSang = new ArrayList<>();
    List<FoodModel> foodAnVat = new ArrayList<>();
    List<FoodModel> foodMonBanh = new ArrayList<>();
    List<FoodModel> foodDoUong = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        setupUI(view);

        rvCatalog = view.findViewById(R.id.rv_categories);

        //Categories
        catalogAdapter = new CatalogAdapter(typeList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        rvCatalog.setLayoutManager(linearLayoutManager);
        rvCatalog.setAdapter(catalogAdapter);
        catalogAdapter.notifyDataSetChanged();
        loadType();
        catalogAdapter.setOnItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type type = (Type) view.getTag();

                if (type.getType().equals("Main dishes")){
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),
                            new SeeMoreFragment(), R.id.drawer_layout,foodMonChinh,type.getType());
                }else if(type.getType().equals("Breakfast")){
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),
                            new SeeMoreFragment(), R.id.drawer_layout,foodAnSang,type.getType());
                }else if(type.getType().equals("Snacks")){
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),
                            new SeeMoreFragment(), R.id.drawer_layout,foodAnVat,type.getType());
                }else if(type.getType().equals("Drink")){
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),
                            new SeeMoreFragment(), R.id.drawer_layout,foodDoUong,type.getType());
                }else if(type.getType().equals("Cake")){
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),
                            new SeeMoreFragment(), R.id.drawer_layout,foodMonBanh,type.getType());
                }

            }
        });
        return view;
    }

    public void loadType(){

        Type monBanh = new Type("Cake",R.drawable.cake);
        typeList.add(monBanh);

        Type douong = new Type("Drink",R.drawable.drink);
        typeList.add(douong);

        Type monanvat = new Type("Snacks",R.drawable.snack);
        typeList.add(monanvat);

        Type monsang = new Type("Breakfast",R.drawable.breakfast);
        typeList.add(monsang);

        Type monchinh = new Type("Main dishes",R.drawable.mainfood);
        typeList.add(monchinh);

    }

    public void setupUI(View view){
        final GetFoodByType getFoodByType = RetrofitFactory.getInstance().create(GetFoodByType.class);
        getFoodByType.getFoodByType("1").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodMonChinh = response.body().getFood();
                Log.d("gaugau", "foodMonChinh: " + foodMonChinh);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("2").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodAnSang = response.body().getFood();
                Log.d("gaugau", "foodAnSang: " + foodAnSang);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("3").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodAnVat = response.body().getFood();
                Log.d("gaugau", "foodAnVat: " + foodAnVat);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("4").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodMonBanh = response.body().getFood();
                Log.d("gaugau", "foodMonBanh: " + foodMonBanh);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("5").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodDoUong = response.body().getFood();
                Log.d("gaugau", "foodDoUong: " + foodDoUong);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });

    }
}
