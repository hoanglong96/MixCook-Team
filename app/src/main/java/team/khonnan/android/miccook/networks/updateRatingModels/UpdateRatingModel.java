package team.khonnan.android.miccook.networks.updateRatingModels;

import java.util.List;

/**
 * Created by tungthanh.1497 on 08/26/2017.
 */

public class UpdateRatingModel {
    private float rating;
    private int rateNum;
    private List<String> listRate;

    @Override
    public String toString() {
        return "UpdateRatingModel{" +
                "rating=" + rating +
                ", rateNum=" + rateNum +
                ", listRate=" + listRate +
                '}';
    }

    public UpdateRatingModel(float rating, int rateNum, List<String> listRate) {
        this.rating = rating;
        this.rateNum = rateNum;
        this.listRate = listRate;
    }

    public float getRating() {

        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    public List<String> getListRate() {
        return listRate;
    }

    public void setListRate(List<String> listRate) {
        this.listRate = listRate;
    }
}
