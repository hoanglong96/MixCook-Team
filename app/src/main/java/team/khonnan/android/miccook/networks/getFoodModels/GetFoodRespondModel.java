package team.khonnan.android.miccook.networks.getFoodModels;

import java.util.List;

/**
 * Created by tungthanh.1497 on 08/11/2017.
 */

public class GetFoodRespondModel {
    List<FoodModel> food;

    @Override
    public String toString() {
        return "GetFoodRespondModel{" +
                "food=" + food +
                '}';
    }

    public GetFoodRespondModel(List<FoodModel> food) {
        this.food = food;
    }

    public List<FoodModel> getFood() {
        return food;
    }

    public void setFood(List<FoodModel> food) {
        this.food = food;
    }
}
