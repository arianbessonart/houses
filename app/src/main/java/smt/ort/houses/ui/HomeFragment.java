package smt.ort.houses.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.network.Resource;
import smt.ort.houses.ui.adapter.HouseListAdapter;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HouseViewModel houseViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentActivity activity = getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);

        final HouseListAdapter adapter = new HouseListAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
        houseViewModel.getHouses().observe(this, new Observer<Resource<List<House>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<House>> houses) {
                adapter.setHouses(houses.getData());
            }
        });

        return view;
    }

}
