package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.CatalogAdapter;
import team.khonnan.android.miccook.adapters.NewRecipesAdapter;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.model.Cook;
import team.khonnan.android.miccook.model.Food;
import team.khonnan.android.miccook.model.Material;
import team.khonnan.android.miccook.model.Type;
import team.khonnan.android.miccook.networks.apis.FoodApi;
import team.khonnan.android.miccook.networks.apis.GetFoodByType;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/21/17.
 */

public class HomeFragment extends Fragment {

    private RecyclerView rvNewRecipes;
    private RecyclerView rvCatalog;
    private RetrofitFactory retrofitFactory;

    List<FoodApi.FoodList> list = new ArrayList<>();

    //new recipes
    private NewRecipesAdapter newRecipesAdapter;
    private List<Food> newRecipesList = new ArrayList<>();

    //catalog
    private CatalogAdapter catalogAdapter;
    private List<Type> typeList = new ArrayList<>();

    //List food type
    List<FoodModel> foodMonChinh = new ArrayList<>();
    List<FoodModel> foodAnSang = new ArrayList<>();
    List<FoodModel> foodAnVat = new ArrayList<>();
    List<FoodModel> foodMonBanh = new ArrayList<>();
    List<FoodModel> foodDoUong = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupUI(view);
        loadData();


        catalogAdapter = new CatalogAdapter(typeList);
        rvCatalog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        rvCatalog.setAdapter(catalogAdapter);
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

        //fab add food
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getFragmentManager(),new FragmentNewRecipes(),R.id.drawer_layout);
            }
        });

        return view;
    }

    public void setupUI(View view){
        rvNewRecipes = view.findViewById(R.id.rv_new_recipes);
        rvCatalog = view.findViewById(R.id.rv_catalog);



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

    public void loadType(){

        Type monBanh = new Type("Cake",R.drawable.monbanh);
        typeList.add(monBanh);

        Type douong = new Type("Drink",R.drawable.douong_home);
        typeList.add(douong);

        Type monanvat = new Type("Snacks",R.drawable.anvat_home);
        typeList.add(monanvat);

        Type monsang = new Type("Breakfast",R.drawable.ansang_home);
        typeList.add(monsang);

        Type monchinh = new Type("Main dishes",R.drawable.monchinh_home);
        typeList.add(monchinh);

        catalogAdapter.notifyDataSetChanged();

    }

    private void loadData() {
        retrofitFactory = new RetrofitFactory("http://cookmix.herokuapp.com/getFood/");
        FoodApi service = retrofitFactory.create(FoodApi.class);
        Call<FoodApi.Food> call = service.callFood();
        call.enqueue(new Callback<FoodApi.Food>() {
            @Override
            public void onResponse(Call<FoodApi.Food> call, Response<FoodApi.Food> response) {
                list = response.body().getFoodList();

                for (int i = 0; i < list.size(); i++) {
                    Food food = new Food();
                    food.setName(list.get(i).getName());
                    food.setAuthor(list.get(i).getAuthor());
                    food.setImageShow(list.get(i).getImageShow());
                    food.setType(list.get(i).getType());
                    food.setTime(list.get(i).getTime());
                    food.setSets(list.get(i).getSets());
                    food.setLevel(list.get(i).getLevel());
                    food.setRating(list.get(i).getRating());
                    List<FoodApi.Material> materials = list.get(i).getMaterials();
                    RealmList<Material> materialList = new RealmList<>();
                    for (int j = 0; j < materials.size(); j++) {
                        Material material = new Material();
                        material.setMatName(materials.get(j).getMatName());
                        material.setMatQuantum(materials.get(j).getMatQuantum());
                        materialList.add(material);
                    }

                    List<FoodApi.Cook> cooks = list.get(i).getCooks();

                    RealmList<Cook> cookList = new RealmList<>();
                    for (int j = 0; j < cooks.size(); j++) {
                        Cook cook = new Cook();
                        cook.setImage(cooks.get(j).getImage());
                        cook.setNote(cooks.get(j).getNote());
                        cookList.add(cook);

                    }
                    Log.d("ahihi", food.toString());
                    newRecipesList.add(food);
                }
//                EventBus.getDefault().post(new EventReady());
                Log.d(TAG, "onResponse: " + newRecipesList);

            }

            @Override
            public void onFailure(Call<FoodApi.Food> call, Throwable t) {
                Log.d("No No ", t.toString());

            }
        });

        newRecipesAdapter = new NewRecipesAdapter(newRecipesList,getContext());
        rvNewRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        rvNewRecipes.setAdapter(newRecipesAdapter);

    }


}
