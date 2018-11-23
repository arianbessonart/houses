package smt.ort.houses.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.HashMap;

import smt.ort.houses.R;
import smt.ort.houses.model.ListLayoutView;
import smt.ort.houses.ui.adapter.HousesAdapter;
import smt.ort.houses.ui.adapter.OnRecyclerObjectClickListener;

public class ListHouseFragment extends Fragment implements OnRecyclerObjectClickListener {

    protected  adapter;
    protected HashMap<ListLayoutView, ListLayoutView> viewStates = new HashMap<ListLayoutView, ListLayoutView>() {{
        put(ListLayoutView.LIST, ListLayoutView.GRID);
        put(ListLayoutView.GRID, ListLayoutView.LIST_ITEM);
        put(ListLayoutView.LIST_ITEM, ListLayoutView.LIST);
    }};
    private OnRecyclerObjectClickListener listener;
    private RecyclerView recyclerView;
    private ListLayoutView listLayoutView = ListLayoutView.LIST;

    public ListHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (OnRecyclerObjectClickListener) context;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_action_item:
                ListLayoutView nextLayoutView = viewStates.get(listLayoutView);
                switch (nextLayoutView) {
                    case LIST:
                        adapter.setLayoutView(ListLayoutView.LIST);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.removeItemDecorationAt(0);
                        recyclerView.setAdapter(adapter);
                        listLayoutView = ListLayoutView.LIST;
                        item.setIcon(R.drawable.baseline_view_module_24);
                        break;
                    case GRID:
                        adapter.setLayoutView(ListLayoutView.GRID);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(adapter);
                        listLayoutView = ListLayoutView.GRID;
                        item.setIcon(R.drawable.baseline_view_list_24);
                        break;
                    case LIST_ITEM:
                        adapter.setLayoutView(ListLayoutView.LIST_ITEM);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(adapter);
                        listLayoutView = ListLayoutView.LIST_ITEM;
                        item.setIcon(R.drawable.baseline_view_headline_24);
                        break;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(Object item) {

    }
}
