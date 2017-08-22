package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/22/17.
 */

public class FragmentDetailFood extends Fragment {

    private TextView tvNameFood , tvTypeFood, tvSet, tvLevel, tvNumIngredient, tvMin;
    private ImageView ivFood;


    private FoodModel foodModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_food, container, false);

        setupUI(view);
        loadData();

        return view;
    }


    @Subscribe(sticky = true)
    public void onEvent(OnClickFood onClickFood) {
        foodModel = onClickFood.getFoodModel();
        Log.d(TAG, "onEvent: " + foodModel);
    }

    public void setupUI(View view){
        EventBus.getDefault().register(this);

        tvNameFood = view.findViewById(R.id.toolbar_title);
        tvTypeFood = view.findViewById(R.id.type_food);
        ivFood = view.findViewById(R.id.header);
        tvLevel = view.findViewById(R.id.tv_level);
        tvSet = view.findViewById(R.id.tv_set);
        tvNumIngredient = view.findViewById(R.id.tv_num_ingredients);
        tvMin = view.findViewById(R.id.tv_time);

        Log.d(TAG, "onCreateView: " + foodModel.getName());
        tvNameFood.setText(foodModel.getName());
        int typeNumber = foodModel.getType();
        String type = null;
        if(typeNumber == 1){
            type = "Main dishes";
        }else if(typeNumber == 2){
            type = "Breakfast";
        }else if(typeNumber == 3){
            type = "Snacks";
        }else if(typeNumber == 4){
            type = "Cake";
        }else if(typeNumber == 5){
            type = "Drink";
        }

        tvTypeFood.setText(type);

        Picasso.with(getContext()).load(foodModel.getImageShow()).into(ivFood);

        tvLevel.setText(foodModel.getLevel());
        String set = String.valueOf(foodModel.getSets());
        tvSet.setText(set);
        tvNumIngredient.setText(String.valueOf(foodModel.getMaterial().size()));
        tvMin.setText(foodModel.getTime());
    }

    public void loadData(){

    }
}
