package team.khonnan.android.miccook.networks;

import java.util.List;

/**
 * Created by tungthanh.1497 on 09/01/2017.
 */

public class UpdateFavoriteModel {
    List<String> listFavorite;

    @Override
    public String toString() {
        return "UpdateFavoriteModel{" +
                "listFavorite=" + listFavorite +
                '}';
    }

    public UpdateFavoriteModel(List<String> listFavorite) {
        this.listFavorite = listFavorite;
    }

    public List<String> getListFavorite() {

        return listFavorite;
    }

    public void setListFavorite(List<String> listFavorite) {
        this.listFavorite = listFavorite;
    }
}
