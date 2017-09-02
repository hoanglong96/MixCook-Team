package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by quyntg94 on 10/08/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private List<FoodModel> foodModelList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FoodAdapter(List<FoodModel> foodModelList, Context context) {
        this.foodModelList = foodModelList;
        this.context = context;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_see_more, viewGroup, false);
        view.setOnClickListener(onClickListener);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder foodViewHolder, int position) {
        foodViewHolder.setData(foodModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFood;
        TextView tvRating,tvAuthor,tvNameFood,tvLevel;
        View view;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.iv_food);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvNameFood = itemView.findViewById(R.id.tv_name_food);
            tvRating = itemView.findViewById(R.id.num_rate);
            tvLevel = itemView.findViewById(R.id.tv_level);

            view = itemView;
        }

        public void setData(FoodModel foodModel){
            if(foodModelList!= null){
                Picasso.with(context).load(foodModel.getImageShow()).resize(500,500).into(ivFood);
                tvAuthor.setText(foodModel.getAuthorName());
                tvNameFood.setText(foodModel.getName());
                String level = foodModel.getLevel();
                if(level.equals("Dễ")){
                    tvLevel.setText("Easy");
                }else if(level.equals("Trung bình")){
                    tvLevel.setText("Medium");
                }else if(level.equals("Khó")){
                    tvLevel.setText("Hard");
                }else {
                    tvLevel.setText(foodModel.getLevel());
                }
                String rating = String.valueOf(foodModel.getRating());
                tvRating.setText(rating);
            }

            view.setTag(foodModel);
        }
    }
}
