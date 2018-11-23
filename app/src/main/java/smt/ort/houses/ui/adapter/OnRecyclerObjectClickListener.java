package smt.ort.houses.ui.adapter;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {
    /**
     * Item has been clicked.
     *
     * @param item object associated with the clicked item.
     */
    void onItemClicked(T item);
}