package team.khonnan.android.miccook.model;

import io.realm.RealmObject;

/**
 * Created by quyntg94 on 19/05/2017.
 */

public class Material extends RealmObject{
    private String matName;
    private String matQuantum;

    public Material() {

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

    @Override
    public String toString() {
        return "Material{" +
                "matName='" + matName + '\'' +
                ", matQuantum='" + matQuantum + '\'' +
                '}';
    }
}
