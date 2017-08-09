package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.FoodApi;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.managers.EventReady;
import team.khonnan.android.miccook.managers.RealmHandler;
import team.khonnan.android.miccook.model.Cook;
import team.khonnan.android.miccook.model.Food;
import team.khonnan.android.miccook.model.Material;
import team.khonnan.android.miccook.networks.RetrofitFactory;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private RetrofitFactory retrofitFactory;
    private RealmHandler realmHandler;
    private Food food;
    private List<Food> foods = RealmHandler.getInstance().getFoodFromRealm();
    private GridLayoutManager layoutManager;

    private SliderLayout mDemoSlider;

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

        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "https://www.cooky.vn/imgs/Ads/mam-ruoc-thai-nam-pla-wan.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Thạch pho mai",R.drawable.thachphomai);
        file_maps.put("Cơm trộn bibibap",R.drawable.comtron);
        file_maps.put("Bánh brownie chocolate đắng",R.drawable.banh_brownie_chocolate_dang);
        file_maps.put("Bánh gạo xiên que", R.drawable.banhgao);
        file_maps.put("Matcha latte",R.drawable.matcha);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        loadData();
        return view;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
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

                for (int i = 0; i <  list.size(); i++){
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
                    for (int j = 0; j < materials.size(); j ++){
                        Material material = new Material();
                        material.setMatName(materials.get(j).getMatName());
                        material.setMatQuantum(materials.get(j).getMatQuantum());
                        materialList.add(material);
                    }

                    List<FoodApi.Cook> cooks = list.get(i).getCooks();

                    RealmList<Cook> cookList = new RealmList<>();
                    for (int j = 0; j < cooks.size(); j ++){
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
}
