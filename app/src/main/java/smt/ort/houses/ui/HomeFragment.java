package smt.ort.houses.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.model.HouseFilters;
import smt.ort.houses.ui.adapter.HouseListAdapter;
import smt.ort.houses.ui.adapter.OnHouseListListener;
import smt.ort.houses.ui.dialog.FilterDialog;


public class HomeFragment extends Fragment implements OnHouseListListener, FilterDialog.NoticeDialogListener {

    private RecyclerView recyclerView;
    private HouseViewModel houseViewModel;
    private OnHouseSelectedListener listener;
    private SearchView searchView;
    private HouseFilters houseFilters;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (OnHouseSelectedListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentActivity activity = getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);
        final HouseListAdapter adapter = new HouseListAdapter(activity, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);

        houseViewModel.getHouses().observe(this, houses -> adapter.setHouses(houses.getData()));

        houseViewModel.setFilters(new HouseFilters());
        houseViewModel.getFilters().observe(this, filters -> houseFilters = filters);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_items_home, menu);
        searchView = (SearchView) menu.findItem(R.id.search_action_item)
                .getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                houseFilters.setTitle(s);
                houseViewModel.setFilters(houseFilters);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        MenuItem item = menu.findItem(R.id.search_action_item);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_action_item:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);

                FilterDialog filterDialog = new FilterDialog();
                filterDialog.show(ft, "dialog");
                filterDialog.setTargetFragment(HomeFragment.this, 1);
                return true;
            case R.id.search_action_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onHouseSelected(House house) {
        this.listener.onHouseSelected(house);
    }

    @Override
    public void onDialogPositiveClick(HouseFilters filters) {
        Log.d("OnApply", "" + filters.getPrice());
        houseViewModel.setFilters(filters);
    }

    @Override
    public void onDialogNegativeClick() {
    }

    public interface OnHouseSelectedListener {
        void onHouseSelected(House house);
    }

}
