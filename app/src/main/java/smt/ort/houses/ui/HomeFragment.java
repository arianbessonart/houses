package smt.ort.houses.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.model.HouseFilters;
import smt.ort.houses.model.ListLayoutView;
import smt.ort.houses.ui.adapter.HousesAdapter;
import smt.ort.houses.ui.adapter.OnRecyclerObjectClickListener;
import smt.ort.houses.ui.dialog.FilterDialog;


public class HomeFragment extends ListHouseFragment implements FilterDialog.NoticeDialogListener {

    ProgressBar progressBar;
    TextView errorTextView;
    private OnRecyclerObjectClickListener listener;
    private RecyclerView recyclerView;
    private HouseViewModel houseViewModel;
    private SearchView searchView;
    private HouseFilters houseFilters;
    private ListLayoutView listLayoutView = ListLayoutView.LIST;

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (OnRecyclerObjectClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progressbar);
        errorTextView = view.findViewById(R.id.houses_error);

        FragmentActivity activity = getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new HousesAdapter(activity, this, listLayoutView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);

        houseViewModel.getHouses().observe(this, housesResource -> {
            switch (housesResource.getStatus()) {
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    List<House> houses = housesResource.getData();
                    if (houses != null && houses.size() > 0) {
                        adapter.setItems(houses);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        errorTextView.setText(getResources().getString(R.string.error_no_results));
                        errorTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    break;
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    errorTextView.setVisibility(View.GONE);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    errorTextView.setText(housesResource.getException().getMessage());
                    break;
            }
        });

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
                houseFilters.setTitle(null);
                houseViewModel.setFilters(houseFilters);
                return true;
            }
        });

        MenuItem itemView = menu.findItem(R.id.view_action_item);
        itemView.setIcon(R.drawable.baseline_view_module_24);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_action_item:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                FilterDialog filterDialog = FilterDialog.newInstance(houseFilters);
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
    public void onDialogPositiveClick(HouseFilters filters) {
        Log.d("OnApply", "" + filters.getPrice());
        houseViewModel.setFilters(filters);
    }

    @Override
    public void onDialogNegativeClick() {
    }

}
