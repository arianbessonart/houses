package smt.ort.houses.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class BaseViewHolder<T, L extends BaseRecyclerListener> extends RecyclerView.ViewHolder {

    private L listener;

    BaseViewHolder(@NonNull View itemView) {
        super(itemView);

    }

    public BaseViewHolder(View itemView, L listener) {
        super(itemView);
        this.listener = listener;
    }

    public abstract void onBind(T item);

    public void onBind(T item, List<Object> payloads) {
        onBind(item);
    }

    protected L getListener() {
        return listener;
    }

}
