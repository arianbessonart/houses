package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import smt.ort.houses.R;
import smt.ort.houses.model.Favorite;
import smt.ort.houses.model.ListLayoutView;

public class FavoritesAdapter extends GenericRecyclerViewAdapter<Favorite, OnRecyclerObjectClickListener, FavoriteViewHolder> {

    public FavoritesAdapter(Context context, OnRecyclerObjectClickListener listener, ListLayoutView layoutView) {
        super(context, listener, layoutView);
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int i) {
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
        return new FavoriteViewHolder(inflate(layout, parent), getListener());
    }
}
