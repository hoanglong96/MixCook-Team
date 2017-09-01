package team.khonnan.android.miccook.networks.getUserProfileModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungthanh.1497 on 09/01/2017.
 */

public class UserProfileModel {
    String _id;
    String idFb;
    String avaFb;
    String nameFb;
    String emailFb;
    float ratePoint;
    int rateNum;
    List<String> listFavorite = new ArrayList<>();

    @Override
    public String toString() {
        return "UserProfileModel{" +
                "_id='" + _id + '\'' +
                ", idFb='" + idFb + '\'' +
                ", avaFb='" + avaFb + '\'' +
                ", nameFb='" + nameFb + '\'' +
                ", emailFb='" + emailFb + '\'' +
                ", ratePoint=" + ratePoint +
                ", rateNum=" + rateNum +
                ", listFavorite=" + listFavorite +
                '}';
    }

    public UserProfileModel(String _id, String idFb, String avaFb, String nameFb, String emailFb, float ratePoint, int rateNum, List<String> listFavorite) {
        this._id = _id;
        this.idFb = idFb;
        this.avaFb = avaFb;
        this.nameFb = nameFb;
        this.emailFb = emailFb;
        this.ratePoint = ratePoint;
        this.rateNum = rateNum;
        this.listFavorite = listFavorite;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdFb() {
        return idFb;
    }

    public void setIdFb(String idFb) {
        this.idFb = idFb;
    }

    public String getAvaFb() {
        return avaFb;
    }

    public void setAvaFb(String avaFb) {
        this.avaFb = avaFb;
    }

    public String getNameFb() {
        return nameFb;
    }

    public void setNameFb(String nameFb) {
        this.nameFb = nameFb;
    }

    public String getEmailFb() {
        return emailFb;
    }

    public void setEmailFb(String emailFb) {
        this.emailFb = emailFb;
    }

    public float getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(float ratePoint) {
        this.ratePoint = ratePoint;
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    public List<String> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(List<String> listFavorite) {
        this.listFavorite = listFavorite;
    }
}
