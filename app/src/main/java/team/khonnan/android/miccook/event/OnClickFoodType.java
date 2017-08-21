package team.khonnan.android.miccook.event;

import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 12/08/2017.
 */

public class OnClickFoodType {
    private FoodModel foodModel;

    public OnClickFoodType(FoodModel foodModel){
        this.foodModel = foodModel;
    }

    public FoodModel getFoodModel(){
        return  foodModel;
    }

    public void setFoodModel(FoodModel foodModel){
        this.foodModel = foodModel;
    }
}
