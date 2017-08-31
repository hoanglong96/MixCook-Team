package team.khonnan.android.miccook.event;

import java.util.List;

import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 8/31/17.
 */

public class EventFoodUser {
    List<FoodModel> listFoodUser;

    public EventFoodUser(List<FoodModel> listFoodUser) {
        this.listFoodUser = listFoodUser;
    }

    public List<FoodModel> getListFoodUser() {
        return listFoodUser;
    }

    public void setListFoodUser(List<FoodModel> listFoodUser) {
        this.listFoodUser = listFoodUser;
    }
}
