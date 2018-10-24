package smt.ort.houses.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smt.ort.houses.R;
import smt.ort.houses.model.House;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseDetailFragment extends Fragment {


    public HouseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        House house;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            house = bundle.getParcelable("selected");
            Log.d("HOUSES", house.getTitle());
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_house_detail, container, false);
    }

}
