package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Quadrant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.StackCardView.TouristSpot;
import team.khonnan.android.miccook.StackCardView.TouristSpotCardAdapter;
import team.khonnan.android.miccook.adapters.CatalogAdapter;
import team.khonnan.android.miccook.adapters.NewRecipesAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.model.Type;
import team.khonnan.android.miccook.networks.apis.FoodApi;
import team.khonnan.android.miccook.networks.apis.GetFoodByType;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.CookModel;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

import static com.facebook.FacebookSdk.getApplicationContext;
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
    private List<FoodModel> newRecipesList = new ArrayList<>();

    //catalog
    private CatalogAdapter catalogAdapter;
    private List<Type> typeList = new ArrayList<>();

    //List food type
    List<FoodModel> foodMonChinh = new ArrayList<>();
    List<FoodModel> foodAnSang = new ArrayList<>();
    List<FoodModel> foodAnVat = new ArrayList<>();
    List<FoodModel> foodMonBanh = new ArrayList<>();
    List<FoodModel> foodDoUong = new ArrayList<>();

    //Stack card view
    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupUI(view);
        loadData();
        setup(view);
        reload();


        catalogAdapter = new CatalogAdapter(typeList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
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

        Type monBanh = new Type("Cake",R.drawable.monbanh1);
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
                    FoodModel food = new FoodModel();
                    food.setName(list.get(i).getName());
                    food.setAuthor(list.get(i).getAuthor());
                    food.setImageShow(list.get(i).getImageShow());
                    food.setType(Integer.parseInt(list.get(i).getType()));
                    food.setTime(list.get(i).getTime());
                    food.setSets(list.get(i).getSets());
                    food.setLevel(list.get(i).getLevel());
                    food.setRating(list.get(i).getRating());
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
                    newRecipesList.add(food);
                    newRecipesAdapter.notifyDataSetChanged();
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


    //Stack card view
    private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        spots.add(new TouristSpot("Yasaka Shrine", "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/400x300"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "Kyoto", "https://source.unsplash.com/NYyCqdBOKwc/400x300"));
        spots.add(new TouristSpot("Bamboo Forest", "Kyoto", "https://source.unsplash.com/buF62ewDLcQ/400x300"));
        spots.add(new TouristSpot("Brooklyn Bridge", "New York", "https://source.unsplash.com/THozNzxEP3g/400x300"));
        spots.add(new TouristSpot("Empire State Building", "New York", "https://source.unsplash.com/USrZRcRS2Lw/400x300"));
        spots.add(new TouristSpot("The statue of Liberty", "New York", "https://source.unsplash.com/PeFk7fzxTdk/400x300"));
        spots.add(new TouristSpot("Louvre Museum", "Paris", "https://source.unsplash.com/LrMWHKqilUw/400x300"));
        spots.add(new TouristSpot("Eiffel Tower", "Paris", "https://source.unsplash.com/HN-5Z6AmxrM/400x300"));
        spots.add(new TouristSpot("Big Ben", "London", "https://source.unsplash.com/CdVAUADdqEc/400x300"));
        spots.add(new TouristSpot("Great Wall of China", "China", "https://source.unsplash.com/AWh9C-QjhE4/400x300"));
        return spots;
    }

    private TouristSpotCardAdapter createTouristSpotCardAdapter() {
        final TouristSpotCardAdapter adapter = new TouristSpotCardAdapter(getApplicationContext());
        adapter.addAll(createTouristSpots());
        return adapter;
    }

    private void setup(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.activity_main_progress_bar);

        cardStackView = (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(Quadrant quadrant) {
                Log.d("CardStackView", "onCardSwiped: " + quadrant.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });
    }

    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createTouristSpotCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(createTouristSpots());
        adapter.notifyDataSetChanged();
    }


}
