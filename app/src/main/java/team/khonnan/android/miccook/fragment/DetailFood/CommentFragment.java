package team.khonnan.android.miccook.fragment.DetailFood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/25/17.
 */

public class CommentFragment extends Fragment {

    LinearLayout lnRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        setupUI(view);
        return view;
    }

    boolean isFirstTime = true;
    private FoodModel foodModel;

    @Subscribe(sticky = true)
    public void onEvent(OnClickFood onClickFood) {
        foodModel = onClickFood.getFoodModel();
        Log.d(TAG, "onEventOneFragment: " + foodModel);
    }

    public void setupUI(View view){
        if (isFirstTime) {
            EventBus.getDefault().register(this);
            isFirstTime = false;
        }
        lnRating = view.findViewById(R.id.ln_rating);
        RatingBar ratingBar = lnRating.findViewById(R.id.rb_inner);
        ratingBar.setRating(foodModel.getRating());
        lnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentRating(),R.id.drawer_layout);
                Log.d(TAG, "onClick: Click rating");
            }
        });
    }
}
