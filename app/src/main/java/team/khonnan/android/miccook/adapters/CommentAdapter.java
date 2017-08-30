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
import team.khonnan.android.miccook.firebases.CommentModel;

/**
 * Created by apple on 8/30/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    List<CommentModel> commentModelList = new ArrayList<>();
    CommentModel commentModel;
    Context context;

    public CommentAdapter(List<CommentModel> commentModelList, Context context) {
        this.commentModelList = commentModelList;
        this.context = context;
    }

    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder holder, int position) {
        commentModel = commentModelList.get(position);
        Picasso.with(context).load("https://graph.facebook.com/" + commentModel.getIdUser()
                + "/picture?type=large").into(holder.ivUser);
        holder.nameUser.setText(commentModel.getName());
        holder.commentUser.setText(commentModel.getComment());
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUser;
        TextView nameUser;
        TextView realTime;
        TextView commentUser;

        public CommentViewHolder(View itemView) {
            super(itemView);

            ivUser = itemView.findViewById(R.id.iv_user);
            nameUser = itemView.findViewById(R.id.name_user);
            realTime = itemView.findViewById(R.id.real_time);
            commentUser = itemView.findViewById(R.id.comment_user);
        }
    }
}
