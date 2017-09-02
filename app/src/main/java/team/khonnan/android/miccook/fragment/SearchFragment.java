package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.SearchAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.fragment.DetailFoodFragment.FragmentDetailFood;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.SearchModel;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.apis.Searching;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by tungthanh.1497 on 08/31/2017.
 */

public class SearchFragment extends Fragment {

    SearchView svSearch;
    TextView tvText;
    RecyclerView rvResultSearch;
    SearchAdapter resultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        svSearch = view.findViewById(R.id.sv_search);
        rvResultSearch = view.findViewById(R.id.rv_result_search);

        svSearch.setQueryHint("Search...");

        RxSearchView.queryTextChanges(svSearch)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        SearchModel searchModel = new SearchModel(charSequence.toString());
                        final Searching searching = RetrofitFactory.getInstance().create(Searching.class);
                        Call<GetFoodRespondModel> call = searching.searching(searchModel);
                        call.enqueue(new Callback<GetFoodRespondModel>() {

                            @Override
                            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                                //TODO: add searching
                                List<FoodModel> list = response.body().getFood();
                                resultAdapter = new SearchAdapter(list,getContext());
                                GridLayoutManager gridLayoutManager = new GridLayoutManager
                                        (getContext(), 1, GridLayoutManager.VERTICAL, false);
                                rvResultSearch.setLayoutManager(gridLayoutManager);
                                rvResultSearch.hasFixedSize();
                                rvResultSearch.setAdapter(resultAdapter);
                                resultAdapter.notifyDataSetChanged();
//                                list.clear();
                                resultAdapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        FoodModel foodModel = (FoodModel) view.getTag();
                                        EventBus.getDefault().postSticky(new OnClickFood(foodModel));
                                        ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailFood(),R.id.drawer_layout);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {

                            }
                        });
                    }
                });
        return view;
    }

    private static final String TAG = "SearchFragment";
}
