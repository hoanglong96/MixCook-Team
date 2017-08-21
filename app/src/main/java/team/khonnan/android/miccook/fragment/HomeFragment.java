package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.managers.RealmHandler;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.model.Cook;
import team.khonnan.android.miccook.model.Food;
import team.khonnan.android.miccook.model.Material;
import team.khonnan.android.miccook.networks.apis.FoodApi;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.apis.GetFoodByType;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;

public class HomeFragment extends Fragment{

    private RetrofitFactory retrofitFactory;
    private RealmHandler realmHandler;
    private Food food;
    private List<Food> foods = RealmHandler.getInstance().getFoodFromRealm();
    CarouselView carouselView;
    RecyclerView rvFoodType;

    List<FoodModel> foodMonChinh = new ArrayList<>();
    List<FoodModel> foodAnSang = new ArrayList<>();
    List<FoodModel> foodAnVat = new ArrayList<>();
    List<FoodModel> foodMonBanh = new ArrayList<>();
    List<FoodModel> foodDoUong = new ArrayList<>();

    CardView cvMonChinh,cvMonSang,cvMonAnVat,cvBanh,cvDoUong;


    int[] sampleImages = {R.drawable.matcha, R.drawable.monbanh1, R.drawable.banhgao, R.drawable.bacon, R.drawable.thachphomai};
    String[] sampleTitles = {"Matcha","Bánh Donut","Bánh gạo","Thịt rang cháy cạnh","Thạch phô mai"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        realmHandler = RealmHandler.getInstance();

        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setViewListener(viewListener);

        //setup cardView
        cvMonChinh = view.findViewById(R.id.cv_mon_chinh);
        cvMonSang = view.findViewById(R.id.cv_mon_sang);
        cvMonAnVat = view.findViewById(R.id.cv_mon_an_vat);
        cvBanh = view.findViewById(R.id.cv_mon_banh);
        cvDoUong = view.findViewById(R.id.cv_do_uong);

        //fab add food
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ScreenManager.openFragment(getFragmentManager(),new FragmentNewRecipes(),R.id.drawer_layout);
            }
        });

        loadData();
        setupUI();
        onClick();
        return view;
    }

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getActivity().getLayoutInflater().inflate(R.layout.view_custom, null);
            TextView labelTextView = customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = customView.findViewById(R.id.iv_food_slide);

            fruitImageView.setImageResource(sampleImages[position]);
            labelTextView.setText(sampleTitles[position]);
            return customView;
        }
    };

    public void onClick(){
        cvMonChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getFragmentManager(),new SeeMoreFragment(),R.id.drawer_layout,foodMonChinh,"Món chính");
            }
        });

        cvMonSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getFragmentManager(),new SeeMoreFragment(),R.id.drawer_layout,foodAnSang,"Món sáng");
            }
        });

        cvMonAnVat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodAnVat.size() != 0)
                ScreenManager.openFragment(getFragmentManager(),new SeeMoreFragment(),R.id.drawer_layout,foodAnVat,"Món ăn vặt");
            }
        });

        cvBanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getFragmentManager(),new SeeMoreFragment(),R.id.drawer_layout,foodMonBanh,"Món bánh");
            }
        });

        cvDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getFragmentManager(),new SeeMoreFragment(),R.id.drawer_layout,foodDoUong,"Đồ uống");
            }
        });
    }

    private void loadData() {
        retrofitFactory = new RetrofitFactory("http://cookmix.herokuapp.com/getFood/");
        FoodApi service = retrofitFactory.create(FoodApi.class);
        Call<FoodApi.Food> call = service.callFood();
        call.enqueue(new Callback<FoodApi.Food>() {
            @Override
            public void onResponse(Call<FoodApi.Food> call, Response<FoodApi.Food> response) {
                RealmHandler.getInstance().clearFoodInRealm();
                List<FoodApi.FoodList> list = response.body().getFoodList();

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
                    RealmHandler.getInstance().addFoodToRealm(food);
                    Log.d("ahihi", food.toString());

                }
//                EventBus.getDefault().post(new EventReady());

            }

            @Override
            public void onFailure(Call<FoodApi.Food> call, Throwable t) {
                Log.d("No No ", t.toString());

            }
        });

    }

    public void setupUI(){
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
