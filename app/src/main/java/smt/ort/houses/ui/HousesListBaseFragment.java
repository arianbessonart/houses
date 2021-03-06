package smt.ort.houses.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.HashMap;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.model.ListLayoutView;
import smt.ort.houses.ui.adapter.HouseListAdapter;
import smt.ort.houses.ui.adapter.OnClickItemListener;

public class HousesListBaseFragment extends Fragment implements OnClickItemListener {

    public final static String VIEW_LAYOUT_KEY = "viewLayout";

    protected HouseListAdapter adapter;
    protected RecyclerView recyclerView;
    protected ListLayoutView listLayoutView = ListLayoutView.LIST;
    protected Boolean isLandscape = false;
    private OnHouseSelectedListener listener;
    private HashMap<ListLayoutView, ListLayoutView> viewStates = new HashMap<ListLayoutView, ListLayoutView>() {{
        put(ListLayoutView.LIST, ListLayoutView.GRID);
        put(ListLayoutView.GRID, ListLayoutView.LIST_ITEM);
        put(ListLayoutView.LIST_ITEM, ListLayoutView.LIST);
    }};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getApplication().getSharedPreferences("general", Context.MODE_PRIVATE);
        listLayoutView = ListLayoutView.getFromString(sharedPreferences.getString(VIEW_LAYOUT_KEY, ListLayoutView.LIST.getName()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (OnHouseSelectedListener) context;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem itemView = menu.findItem(R.id.view_action_item);
        if (itemView != null) {
            itemView.setIcon(R.drawable.baseline_view_module_24);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_action_item:
                ListLayoutView nextLayoutView = viewStates.get(listLayoutView);
                switch (nextLayoutView) {
                    case LIST:
                        adapter.setLayout(ListLayoutView.LIST);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.removeItemDecorationAt(0);
                        recyclerView.setAdapter(adapter);
                        listLayoutView = ListLayoutView.LIST;
                        item.setIcon(R.drawable.baseline_view_module_24);
                        storeLayoutView();
                        break;
                    case GRID:
                        adapter.setLayout(ListLayoutView.GRID);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(adapter);
                        listLayoutView = ListLayoutView.GRID;
                        item.setIcon(R.drawable.baseline_view_list_24);
                        storeLayoutView();
                        break;
                    case LIST_ITEM:
                        adapter.setLayout(ListLayoutView.LIST_ITEM);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(adapter);
                        listLayoutView = ListLayoutView.LIST_ITEM;
                        item.setIcon(R.drawable.baseline_view_headline_24);
                        storeLayoutView();
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void storeLayoutView() {
        SharedPreferences sharedPreferences = getActivity().getApplication().getSharedPreferences("general", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(VIEW_LAYOUT_KEY, listLayoutView.getName());
    }


    @Override
    public void onClick(House house) {
        this.listener.onHouseSelected(house);
    }

    public interface OnHouseSelectedListener {
        void onHouseSelected(House house);
    }


}
