package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.House;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseViewHolder> {

    private final LayoutInflater mInflater;
    private List<House> houses;

    public HouseListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new HouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        if (houses != null) {
            House current = houses.get(position);
            holder.houseItemView.setText(current.getTitle());
        } else {
            holder.houseItemView.setText("No word");
        }
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (houses != null) {
            return houses.size();
        }
        return 0;
    }


    class HouseViewHolder extends RecyclerView.ViewHolder {
        private final TextView houseItemView;

        private HouseViewHolder(View itemView) {
            super(itemView);
            houseItemView = itemView.findViewById(R.id.textView);
        }
    }

}
