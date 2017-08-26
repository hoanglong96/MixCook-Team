package team.khonnan.android.miccook.networks.createFoodModels;

/**
 * Created by tungthanh.1497 on 08/17/2017.
 */

public class MaterialModel {
    private String matName;
    private String matQuantum;

    @Override
    public String toString() {
        return "MaterialModel{" +
                "matName='" + matName + '\'' +
                ", matQuantum='" + matQuantum + '\'' +
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

    public MaterialModel(String matName, String matQuantum) {

        this.matName = matName;
        this.matQuantum = matQuantum;
    }
}
