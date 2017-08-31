package team.khonnan.android.miccook.fragment.DetailFoodFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.apis.UpdateRating;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.updateRatingModels.UpdateRatingModel;

/**
 * Created by apple on 8/23/17.
 */

public class FragmentRating extends Fragment implements View.OnClickListener {

    private RelativeLayout rlFive,rlFour,rlThree,rlTwo,rlOne;
    private Toolbar toolbarRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        setupUI(view);
        setupListener();
        return view;
    }

    float newRate=0;

    private void setupListener() {

        rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.d(TAG, "onRatingChanged: vote: " + v + "\n - id: " + foodModel.get_id() + "\n - rating: " + foodModel.getRating() + "\n - rateNum: " + foodModel.getRateNum());
                float currentPoint = foodModel.getRating()*foodModel.getRateNum();
                float newPoint = currentPoint + v;
                newRate = newPoint/(foodModel.getRateNum()+1);
                Log.d(TAG, "onRatingChanged: currentpoint: "+currentPoint+"\n - newpoint: "+newPoint+"\n - newrate: "+newRate);
            }
        });
        btSubmit.setOnClickListener(this);
    }

    boolean isFirstTime = true;
    RatingBar rbRatingBar;
    Button btSubmit;
    private FoodModel foodModel;


    private void setupUI(View view) {
        if (isFirstTime) {
            EventBus.getDefault().register(this);
            isFirstTime = false;
        }
        rbRatingBar = view.findViewById(R.id.rb_rating_bar);
        btSubmit = view.findViewById(R.id.bt_submit);


        rlFive = view.findViewById(R.id.rating_five);
        rlFour = view.findViewById(R.id.rating_four);
        rlThree = view.findViewById(R.id.rating_three);
        rlTwo = view.findViewById(R.id.rating_two);
        rlOne = view.findViewById(R.id.rating_one);

        rlFive.setOnClickListener(this);
        rlFour.setOnClickListener(this);
        rlThree.setOnClickListener(this);
        rlTwo.setOnClickListener(this);
        rlOne.setOnClickListener(this);

        toolbarRating = view.findViewById(R.id.toolbar_rating);
        toolbarRating.setTitle("Đánh giá món ăn");
        toolbarRating.setTitleTextColor(Color.WHITE);
        toolbarRating.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarRating.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


    }

    private static final String TAG = "FragmentRating";

    @Subscribe(sticky = true)
    public void onEvent(OnClickFood onClickFood) {
        foodModel = onClickFood.getFoodModel();
        Log.d(TAG, "onEventOneFragment: " + foodModel);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.bt_submit:
                if (newRate != 0) {
                    UpdateRatingModel updateRatingModel = new UpdateRatingModel(newRate, foodModel.getRateNum() + 1);
                    final UpdateRating updateRating = RetrofitFactory.getInstance().create(UpdateRating.class);
                    Call<UpdateRatingModel> call = updateRating.updateRating(foodModel.get_id(), updateRatingModel);
                    call.enqueue(new Callback<UpdateRatingModel>() {
                        @Override
                        public void onResponse(Call<UpdateRatingModel> call, Response<UpdateRatingModel> response) {
                            Toast.makeText(getContext(), "RATE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<UpdateRatingModel> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.rating_five:
                rbRatingBar.setRating(5);
                break;
            case R.id.rating_four:
                rbRatingBar.setRating(4);
                break;
            case R.id.rating_three:
                rbRatingBar.setRating(3);
                break;
            case R.id.rating_two:
                rbRatingBar.setRating(2);
                break;
            case R.id.rating_one:
                rbRatingBar.setRating(1);
                break;
        }

    }
}
