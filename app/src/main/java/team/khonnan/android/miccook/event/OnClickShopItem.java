package team.khonnan.android.miccook.event;

import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 8/26/17.
 */

public class OnClickShopItem {
    FoodModel foodModel;

    public OnClickShopItem(FoodModel foodModel) {
        this.foodModel = foodModel;
    }

    public FoodModel getFoodModel() {
        return foodModel;
    }

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
    }
}
