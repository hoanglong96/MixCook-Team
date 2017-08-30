package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

/**
 * Created by apple on 8/26/17.
 */

public class ItemIngredientAdapter extends RecyclerView.Adapter<ItemIngredientAdapter.IngredientViewHolder>{

    private List<MaterialModel> materialModels = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;
    private MaterialModel materialModel;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ItemIngredientAdapter(List<MaterialModel> materialModels, Context context) {
        this.materialModels = materialModels;
        this.context = context;
    }

    @Override
    public ItemIngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_ingredient_shop, viewGroup, false);
        view.setOnClickListener(onClickListener);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemIngredientAdapter.IngredientViewHolder ingredientViewHolder, int i) {
        ingredientViewHolder.setData(materialModels.get(i));
    }

    @Override
    public int getItemCount() {
        return materialModels.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView soLuongNguyenLieu,tenNguyenLieu;
        View view;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            soLuongNguyenLieu = itemView.findViewById(R.id.so_luong_nguyen_lieu);
            tenNguyenLieu = itemView.findViewById(R.id.name_nguyen_lieu);
            view = itemView;
        }

        public void setData(MaterialModel materialModel) {
            soLuongNguyenLieu.setText(materialModel.getMatQuantum());
            tenNguyenLieu.setText(materialModel.getMatName());
        }
    }
}
