package team.khonnan.android.miccook.networks.getFoodModels;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by tungthanh.1497 on 08/11/2017.
 */

public class MaterialModel extends RealmObject {
    private String matName;
    private String matQuantum;
    private String _id;

    public MaterialModel() {
    }

    public MaterialModel(String matName, String matQuantum, String _id) {
        this.matName = matName;
        this.matQuantum = matQuantum;
        this._id = _id;
    }

    @Override
    public String toString() {
        return "MaterialModel{" +
                "matName='" + matName + '\'' +
                ", matQuantum='" + matQuantum + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getMatQuantum() {
        return matQuantum;
    }

    public void setMatQuantum(String matQuantum) {
        this.matQuantum = matQuantum;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
