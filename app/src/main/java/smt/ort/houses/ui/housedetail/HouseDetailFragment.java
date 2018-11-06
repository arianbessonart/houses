package smt.ort.houses.ui.housedetail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.network.Resource;
import smt.ort.houses.ui.adapter.HousePhotosAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseDetailFragment extends Fragment {

    private House house;
    private HouseDetailViewModel viewModel;
    private Menu menu;
    private Boolean itemLoaded = false;

    public HouseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_house_detail, container, false);

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            house = bundle.getParcelable("selected");
            Log.d("HOUSES", house.getTitle());
        }
        final ViewPager viewPager = view.findViewById(R.id.viewPagerHouseDetailPhotos);

        HouseDetailViewModel.Factory factory = new HouseDetailViewModel.Factory(
                getActivity().getApplication(), house.getId());

        viewModel = ViewModelProviders.of(this, factory).get(HouseDetailViewModel.class);

        viewModel.getHouse().observe(this, new Observer<Resource<House>>() {
            @Override
            public void onChanged(@Nullable Resource<House> resourceHouse) {
                if (resourceHouse.getData() != null) {
                    itemLoaded = true;
                    getActivity().invalidateOptionsMenu();
                    House newHouse = resourceHouse.getData();
                    if (newHouse.getPhotos().size() > 0) {
                        viewPager.setAdapter(new HousePhotosAdapter(getActivity(), newHouse.getPhotos()));
                    }
                    house = resourceHouse.getData();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (itemLoaded) {
            MenuItem item = menu.findItem(R.id.favorite_action_item);
            if (house.getFavorite()) {
                item.setIcon(R.drawable.baseline_favorite_24);
            } else {
                item.setIcon(R.drawable.baseline_favorite_border_24);
            }
            menu.setGroupVisible(R.id.main_menu_group, true);
        } else {
            menu.setGroupVisible(R.id.main_menu_group, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_action_item:
                house.setFavorite(!house.getFavorite());
                viewModel.toggleFavorite(house);
                return true;
            case R.id.search_action_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
