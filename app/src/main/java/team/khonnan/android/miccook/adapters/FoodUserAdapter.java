package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 9/1/17.
 */

public class FoodUserAdapter extends RecyclerView.Adapter<FoodUserAdapter.UserViewHolder> {


    private List<FoodModel> foodUserList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public FoodUserAdapter(List<FoodModel> foodUserList, Context context) {
        this.foodUserList = foodUserList;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public FoodUserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_food_user, parent, false);
        view.setOnClickListener(onClickListener);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodUserAdapter.UserViewHolder holder, int position) {
        holder.setData(foodUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvType;
        ImageView ivFood;
        RatingBar ratingBarFood;
        View view;

        public UserViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_user_food);
            tvType = itemView.findViewById(R.id.tv_type_user_food);
            ivFood = itemView.findViewById(R.id.iv_user_food);
            ratingBarFood = itemView.findViewById(R.id.rating_user_food);
            view = itemView;
        }

        public void setData(FoodModel foodModel){
            tvName.setText(foodModel.getName());
            int type = foodModel.getType();
            if(type == 1){
                tvType.setText("Main dishes");
            }else if(type == 2){
                tvType.setText("Breakfast");
            }else if(type == 3){
                tvType.setText("Snacks");
            }else if(type == 4){
                tvType.setText("Drink");
            }else if(type == 5){
                tvType.setText("Cake");
            }

            Picasso.with(context).load(foodModel.getImageShow()).resize(500,500).into(ivFood);
            ratingBarFood.setRating(foodModel.getRating());

            view.setTag(foodModel);

        }
    }
}
