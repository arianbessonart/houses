package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import smt.ort.houses.model.ListLayoutView;

public abstract class GenericRecyclerViewAdapter<T, L extends BaseRecyclerListener, VH extends BaseViewHolder<T, L>> extends RecyclerView.Adapter<VH> {

    L listener;
    ListLayoutView layoutView;
    private LayoutInflater layoutInflater;
    private List<T> items;

    public GenericRecyclerViewAdapter(Context context, L listener, ListLayoutView layoutView) {
        layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
        this.layoutView = layoutView;
        items = new ArrayList<>();
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int i);

//    @NonNull
//    @Override
//    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View itemView;
//        switch (layoutView) {
//            case GRID:
//                itemView = mInflater.inflate(R.layout.house_list_grid_item, parent, false);
//                break;
//            case LIST:
//                itemView = mInflater.inflate(R.layout.house_list_item, parent, false);
//                break;
//            case LIST_ITEM:
//                itemView = mInflater.inflate(R.layout.house_list_item_left, parent, false);
//                break;
//            default:
//                itemView = mInflater.inflate(R.layout.house_list_item, parent, false);
//        }
//        return new HouseViewHolder(itemView);
//    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (items.size() <= position) {
            return;
        }
        T item = items.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public void setItems(List<T> items) {
        setItems(items, true);
    }

    public void setItems(List<T> items, boolean notifyChanges) throws IllegalArgumentException {
        if (items == null) {
            throw new IllegalArgumentException("Cannot set null to items");
        }
        this.items.clear();
        this.items.addAll(items);
        if (notifyChanges) {
            notifyDataSetChanged();
        }
    }

    public L getListener() {
        return listener;
    }

    public void setListener(L listener) {
        this.listener = listener;
    }

    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
        return layoutInflater.inflate(layout, parent, attachToRoot);
    }

    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout, parent, false);
    }

    public ListLayoutView getLayoutView() {
        return layoutView;
    }

    public void setLayoutView(ListLayoutView layoutView) {
        this.layoutView = layoutView;
    }
}
