package team.khonnan.android.miccook.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

/**
 * Created by tungthanh.1497 on 09/02/2017.
 */

public class RealmMaterialModel  extends RealmObject {
    RealmList<MaterialModel> listMaterials;

    public RealmMaterialModel() {
    }

    @Override
    public String toString() {
        return "RealmMaterialModel{" +
                "listMaterials=" + listMaterials +
                '}';
    }

    public RealmMaterialModel(RealmList<MaterialModel> listMaterials) {
        this.listMaterials = listMaterials;
    }

    public List<MaterialModel> getListMaterials() {

        return listMaterials;
    }

    public void setListMaterials(RealmList<MaterialModel> listMaterials) {
        this.listMaterials = listMaterials;
    }
}
