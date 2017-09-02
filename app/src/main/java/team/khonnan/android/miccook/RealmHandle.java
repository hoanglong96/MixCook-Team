package team.khonnan.android.miccook;

import java.util.List;

import io.realm.Realm;
import team.khonnan.android.miccook.model.RealmFoodModel;
import team.khonnan.android.miccook.model.RealmMaterialModel;

/**
 * Created by tungthanh.1497 on 08/09/2017.
 */

public class RealmHandle {
    public static Realm realmFood = Realm.getDefaultInstance();
    public static Realm realmMaterial = Realm.getDefaultInstance();

    public static void addFood(RealmFoodModel realmFoodModel){
        realmFood.beginTransaction();
        realmFood.copyToRealm(realmFoodModel);
        realmFood.commitTransaction();
    }
    public static void addMaterial(RealmMaterialModel realmMaterialModel){
        realmMaterial.beginTransaction();
        realmMaterial.copyToRealm(realmMaterialModel);
        realmMaterial.commitTransaction();
    }

    public static List<RealmFoodModel> getFoods(){
        return realmFood.where(RealmFoodModel.class).findAll();
    }
    public static List<RealmMaterialModel> getMaterials(){
        return realmMaterial.where(RealmMaterialModel.class).findAll();
    }

}
