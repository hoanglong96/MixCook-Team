package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.SearchModel;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.apis.Searching;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by tungthanh.1497 on 08/31/2017.
 */

public class SearchFragment extends Fragment {

    SearchView svSearch;
    TextView tvText, tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        svSearch = view.findViewById(R.id.sv_search);

        svSearch.setQueryHint("Search...");

        tvText = view.findViewById(R.id.tv_text);
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
                                Log.d(TAG, "onResponse: " + response.body().getFood());
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
