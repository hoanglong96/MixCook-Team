package team.khonnan.android.miccook.Dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.khonnan.android.miccook.R;

/**
 * Created by apple on 8/16/17.
 */

public class DialogType extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_choose_type, container,
                false);
        getDialog().setTitle("Loại món ăn");
        // Do something else
        return rootView;
    }
}
