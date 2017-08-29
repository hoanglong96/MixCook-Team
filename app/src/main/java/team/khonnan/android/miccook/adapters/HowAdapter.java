package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.createFoodModels.CookModel;

/**
 * Created by apple on 8/29/17.
 */

public class HowAdapter extends RecyclerView.Adapter<HowAdapter.HowHolder> {

    List<CookModel> howModels = new ArrayList<>();
    Context context;

    public HowAdapter(List<CookModel> howModels, Context context) {
        this.howModels = howModels;
        this.context = context;
    }

    @Override
    public HowAdapter.HowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_how, parent, false);

        return new HowHolder(view);
    }

    @Override
    public void onBindViewHolder(HowAdapter.HowHolder holder, final int position) {
        CookModel howModel = howModels.get(position);
        holder.tvHow.setText(howModel.getNote());
        holder.ivHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, howModels.size());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return howModels.size();
    }

    public class HowHolder extends RecyclerView.ViewHolder {

        TextView tvHow;
        ImageView ivHow;

        public HowHolder(View itemView) {
            super(itemView);

            tvHow = itemView.findViewById(R.id.tv_how);
            ivHow = itemView.findViewById(R.id.del_how);
        }
    }
}
