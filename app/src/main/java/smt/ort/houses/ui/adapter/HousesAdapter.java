package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.model.ListLayoutView;

public class HousesAdapter extends GenericRecyclerViewAdapter<House, OnRecyclerObjectClickListener, HouseViewHolder> {

    public HousesAdapter(Context context, OnRecyclerObjectClickListener listener, ListLayoutView layoutView) {
        super(context, listener, layoutView);
    }

    @Override
    public HouseViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        int layout;
        switch (layoutView) {
            case GRID:
                layout = R.layout.house_list_grid_item;
                break;
            case LIST:
                layout = R.layout.house_list_item;
                break;
            case LIST_ITEM:
                layout = R.layout.house_list_item_left;
                break;
            default:
                layout = R.layout.house_list_item;
        }
        return new HouseViewHolder(inflate(layout, parent), getListener());
    }
}
