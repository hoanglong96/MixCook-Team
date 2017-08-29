package team.khonnan.android.miccook.event;

import java.util.List;

import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 8/28/17.
 */

public class EventRecentFood {
    List<FoodModel> listRecentFood;

    public EventRecentFood(List<FoodModel> listRecentFood) {
        this.listRecentFood = listRecentFood;
    }

    public List<FoodModel> getListRecentFood() {
        return listRecentFood;
    }

    public void setListRecentFood(List<FoodModel> listRecentFood) {
        this.listRecentFood = listRecentFood;
    }
}
