package team.khonnan.android.miccook.fragment.Shop;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.ItemIngredientAdapter;
import team.khonnan.android.miccook.event.OnClickShopItem;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/26/17.
 */

public class FragmentDetailShop extends Fragment {

    private ImageView ivFoodShop;
    private TextView tvNumIngredient,tvName;
    private RecyclerView rvIngredient;
    private FoodModel foodModel;
    private ItemIngredientAdapter itemIngredientAdapter;
    private List<MaterialModel> materialModels = new ArrayList<>();
    private ImageView ivCheck;
    private boolean isCheck;
    private Toolbar toolbar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_shop, container, false);
        setupUI(view);
        loadData();


        toolbar = view.findViewById(R.id.toolbar_detail_shop);
        toolbar.setTitle(foodModel.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        return view;
    }

    @Subscribe(sticky = true)
    public void onEvent(OnClickShopItem onClickShopItem) {
        foodModel = onClickShopItem.getFoodModel();
        Log.d(TAG, "onEventOneFragment: " + foodModel);
    }

    private void setupUI(View view) {
        EventBus.getDefault().register(this);
        ivFoodShop = view.findViewById(R.id.iV_food_item);
        tvNumIngredient = view.findViewById(R.id.num_ingredient);
        rvIngredient = view.findViewById(R.id.rv_ingredient);
        tvName = view.findViewById(R.id.tv_name_item);
    }

    private void loadData(){


        Picasso.with(getContext()).load(foodModel.getImageShow()).into(ivFoodShop);
        tvName.setText(foodModel.getName());
        tvNumIngredient.setText(String.valueOf(foodModel.getMaterial().size()));

        materialModels = foodModel.getMaterial();
        List<MaterialModel> materialModelList = new ArrayList<>();

        for(int i= materialModels.size()-1;i>=0;i--){
            materialModelList.add(materialModels.get(i));
        }

        itemIngredientAdapter = new ItemIngredientAdapter(materialModelList,getContext());
        rvIngredient.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        rvIngredient.setAdapter(itemIngredientAdapter);
        itemIngredientAdapter.notifyDataSetChanged();

        itemIngredientAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCheck){

                }
            }
        });
    }
}
