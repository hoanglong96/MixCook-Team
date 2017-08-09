package team.khonnan.android.miccook.managers;

import java.util.List;

import io.realm.Realm;
import team.khonnan.android.miccook.model.Food;

/**
 * Created by quyntg94 on 20/05/2017.
 */

public class RealmHandler {
    private static RealmHandler instance;
    private Realm realm;

    private RealmHandler(){
        this.realm = Realm.getDefaultInstance();
    }

    public static RealmHandler getInstance() {
        if (instance == null)
            instance = new RealmHandler();
        return instance;
    }

    public List<Food> getFoodFromRealm(){
        return realm.where(Food.class).findAll();
    }

    public void addFoodToRealm(Food food){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(food);
        realm.commitTransaction();
    }

    public void clearFoodInRealm(){
        realm.beginTransaction();
        realm.delete(Food.class);
        realm.commitTransaction();
    }
}
