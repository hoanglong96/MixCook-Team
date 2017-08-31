package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 8/31/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>  {

    private List<FoodModel> foodModels = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;


    public SearchAdapter(List<FoodModel> foodModels, Context context) {
        this.foodModels = foodModels;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_shopping, viewGroup, false);
        view.setOnClickListener(onClickListener);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.setData(foodModels.get(position));
    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFoodShop;
        TextView tvFoodShop;
        View view;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ivFoodShop = itemView.findViewById(R.id.iv_shop_food);
            tvFoodShop = itemView.findViewById(R.id.tv_name_shop_food);
            view = itemView;
        }

        public void setData(FoodModel foodModel) {
            if(foodModel != null){
                Picasso.with(context).load(foodModel.getImageShow()).resize(80,80).into(ivFoodShop);
                tvFoodShop.setText(foodModel.getName());
                view.setTag(foodModel);}
        }
    }
}
