package smt.ort.houses.ui.favorite;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
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
import smt.ort.houses.model.ListLayoutView;
import smt.ort.houses.ui.HomeFragment;
import smt.ort.houses.ui.adapter.HouseListAdapter;
import smt.ort.houses.ui.adapter.OnHouseListListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements OnHouseListListener {

    private HomeFragment.OnHouseSelectedListener listener;
    private RecyclerView recyclerView;
    private FavoritesViewModel houseViewModel;
    private ListLayoutView listLayoutView = ListLayoutView.LIST;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();

        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (HomeFragment.OnHouseSelectedListener) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        setHasOptionsMenu(true);

        FragmentActivity activity = getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);
        final HouseListAdapter adapter = new HouseListAdapter(activity, this, listLayoutView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        houseViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        houseViewModel.getFavorites().observe(this, favoritesResource -> {
            switch (favoritesResource.getStatus()) {
                case SUCCESS:
                    List<House> favorites = favoritesResource.getData();
                    if (favorites != null && favorites.size() > 0) {
                        adapter.setHouses(favorites);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                    }
                    break;
                case LOADING:
                    break;
                case ERROR:
                    recyclerView.setVisibility(View.GONE);
                    break;
            }
        });


        return view;
    }

    @Override
    public void onHouseSelected(House house) {
        this.listener.onHouseSelected(house);
    }
}
