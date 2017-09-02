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
import team.khonnan.android.miccook.networks.getFoodModels.CookModel;

/**
 * Created by apple on 8/25/17.
 */

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.CookViewHolder> {

    private List<CookModel> cookModels = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public CookAdapter(List<CookModel> cookModels, Context context) {
        this.cookModels = cookModels;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CookAdapter.CookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_cook, viewGroup, false);
        view.setOnClickListener(onClickListener);
        return new CookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CookAdapter.CookViewHolder cookViewHolder, int i) {
        CookModel cookModel = cookModels.get(i);
        cookViewHolder.tvStep.setText(cookModel.getNote());
        cookViewHolder.tvNumStep.setText(String.valueOf(cookModels.size()-i));
        }

    @Override
    public int getItemCount() {
        return cookModels.size();
    }

    public class CookViewHolder extends RecyclerView.ViewHolder {


        TextView tvStep,tvNumStep;

        public CookViewHolder(View itemView) {
            super(itemView);

            tvNumStep = itemView.findViewById(R.id.num_step);
            tvStep = itemView.findViewById(R.id.tv_step);
        }
    }
}
