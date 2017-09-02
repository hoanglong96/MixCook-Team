package team.khonnan.android.miccook.fragment.ShopFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import team.khonnan.android.miccook.MainActivity;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.RealmHandle;
import team.khonnan.android.miccook.adapters.ShopItemAdapter;
import team.khonnan.android.miccook.event.OnClickShopItem;
import team.khonnan.android.miccook.event.onClickAddToShop;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.model.RealmFoodModel;
import team.khonnan.android.miccook.model.RealmMaterialModel;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/25/17.
 */

public class FragmentShopping extends Fragment {

    private FoodModel foodModel;
    //    private List<FoodModel> listShop = new ArrayList<>();
    private RecyclerView rvShop;
    private ShopItemAdapter shopItemAdapter;
    private RelativeLayout rlShopNull;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        setupUI(view);
        return view;
    }

    @Subscribe(sticky = true)
    public void onEvent(onClickAddToShop onClickAddToShop) {
        foodModel = onClickAddToShop.getFoodModel();
    }

    private void setupUI(View view) {
        EventBus.getDefault().register(this);
        rlShopNull = view.findViewById(R.id.rn_shop_null);
        toolbar = view.findViewById(R.id.toolbar_shop);
        toolbar.setTitle("Shopping List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                toolbar.setTitle("Cook Mix");
            }
        });
        if(foodModel != null){
            rvShop = view.findViewById(R.id.rv_shopping);

            RealmFoodModel realmFoodModel = new RealmFoodModel(foodModel.get_id(), foodModel.getName(), foodModel.getAuthorName(), foodModel.getImageShow(), foodModel.getSets());
//            List<MaterialModel> materialModelList = foodModel.getMaterial();
//            List<String> listMaterials = new ArrayList<>();
//            for(MaterialModel materialModel: materialModelList){
//                listMaterials.add(materialModel.getMatQuantum()+" "+materialModel.getMatName());
//            }
            RealmList<MaterialModel> listMaterials = new RealmList<>();
            for(MaterialModel materialModel: foodModel.getMaterial()){
                listMaterials.add(materialModel);
            }
            RealmMaterialModel realmMaterialModel = new RealmMaterialModel(listMaterials);
            RealmHandle.addFood(realmFoodModel);
            RealmHandle.addMaterial(realmMaterialModel);

            List<FoodModel> listShop= new ArrayList<>();
            for(int i=0; i<RealmHandle.getFoods().size(); i++){
                RealmFoodModel currentRealmFood = RealmHandle.getFoods().get(i);
                RealmMaterialModel currentRealmMaterial = RealmHandle.getMaterials().get(i);
                FoodModel foodModel = new FoodModel();
                foodModel.set_id(currentRealmFood.get_id());
                foodModel.setName(currentRealmFood.getName());
                foodModel.setAuthorName(currentRealmFood.getAuthorName());
                foodModel.setImageShow(currentRealmFood.getImageShow());
                foodModel.setSets(currentRealmFood.getSets());
                foodModel.setMaterial(currentRealmMaterial.getListMaterials());
                listShop.add(foodModel);
            }

            shopItemAdapter = new ShopItemAdapter(listShop,getContext());
            rvShop.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
            rvShop.setAdapter(shopItemAdapter);
            shopItemAdapter.notifyDataSetChanged();

            shopItemAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FoodModel foodModel = (FoodModel) view.getTag();
                    EventBus.getDefault().postSticky(new OnClickShopItem(foodModel));
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailShop(),R.id.drawer_layout);
                }
            });
        }else if(RealmHandle.getFoods().size()!=0) {
            rvShop = view.findViewById(R.id.rv_shopping);
            List<FoodModel> listShop= new ArrayList<>();
            for(int i=0; i<RealmHandle.getFoods().size(); i++){
                RealmFoodModel currentRealmFood = RealmHandle.getFoods().get(i);
                RealmMaterialModel currentRealmMaterial = RealmHandle.getMaterials().get(i);
                FoodModel foodModel = new FoodModel();
                foodModel.set_id(currentRealmFood.get_id());
                foodModel.setName(currentRealmFood.getName());
                foodModel.setAuthorName(currentRealmFood.getAuthorName());
                foodModel.setImageShow(currentRealmFood.getImageShow());
                foodModel.setSets(currentRealmFood.getSets());
                foodModel.setMaterial(currentRealmMaterial.getListMaterials());
                listShop.add(foodModel);
            }
            shopItemAdapter = new ShopItemAdapter(listShop,getContext());
            rvShop.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
            rvShop.setAdapter(shopItemAdapter);
            shopItemAdapter.notifyDataSetChanged();

            shopItemAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FoodModel foodModel = (FoodModel) view.getTag();
                    EventBus.getDefault().postSticky(new OnClickShopItem(foodModel));
                    ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailShop(),R.id.drawer_layout);
                }
            });
        }else {
            rlShopNull.setVisibility(View.VISIBLE);
        }

    }
}
