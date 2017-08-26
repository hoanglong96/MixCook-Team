package team.khonnan.android.miccook.networks.updateRatingModels;

/**
 * Created by tungthanh.1497 on 08/26/2017.
 */

public class UpdateRatingModel {
    private float rating;
    private int rateNum;

    @Override
    public String toString() {
        return "UpdateRatingModel{" +
                "rating=" + rating +
                ", rateNum=" + rateNum +
                '}';
    }

    public UpdateRatingModel(float rating, int rateNum) {
        this.rating = rating;
        this.rateNum = rateNum;
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
}
