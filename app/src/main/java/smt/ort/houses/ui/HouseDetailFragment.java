package smt.ort.houses.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.ui.adapter.HousePhotosAdapter;

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

        View view = inflater.inflate(R.layout.fragment_house_detail, container, false);

        House house;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            house = bundle.getParcelable("selected");
            Log.d("HOUSES", house.getTitle());

            FragmentActivity activity = getActivity();
            ViewPager viewPager = view.findViewById(R.id.viewPagerHouseDetailPhotos);
            viewPager.setAdapter(new HousePhotosAdapter(activity, house.getPhotos()));
        }

        // Inflate the layout for this fragment
        return view;
    }

}
