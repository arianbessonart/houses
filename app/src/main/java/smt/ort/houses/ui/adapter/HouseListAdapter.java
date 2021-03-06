package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.model.ListLayoutView;
import smt.ort.houses.util.StringUtil;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseViewHolder> {

    private final LayoutInflater mInflater;
    OnClickItemListener listener;
    ListLayoutView layoutView;
    private List<House> houses;

    public HouseListAdapter(Context context, OnClickItemListener listener, ListLayoutView layoutView) {
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (layoutView) {
            case GRID:
                itemView = mInflater.inflate(R.layout.house_list_grid_item, parent, false);
                break;
            case LIST:
                itemView = mInflater.inflate(R.layout.house_list_item, parent, false);
                break;
            case LIST_ITEM:
                itemView = mInflater.inflate(R.layout.house_list_item_left, parent, false);
                break;
            default:
                itemView = mInflater.inflate(R.layout.house_list_item, parent, false);
        }
        return new HouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House hCurrent = houses.get(position);
        holder.titleView.setText(hCurrent.getTitle());
        holder.priceTextView.setText(StringUtil.formatCurrency(hCurrent.getPrice()));
        holder.squareMeters.setText(StringUtil.formatSquareMeters(hCurrent.getSquareMeters()));

        // Set photo
        if (hCurrent.getPhotos().size() > 0) {
            Picasso.get().load(hCurrent.getPhotos().get(0).getUrl()).placeholder(R.drawable.camera).into(holder.imageView);
        } else {
            Picasso.get().load(R.drawable.camera).into(holder.imageView);
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

    public void setLayout(ListLayoutView layoutView) {
        this.layoutView = layoutView;
    }


    class HouseViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView priceTextView;
        private ImageView imageView;
        private TextView squareMeters;

        HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.houseTitleTextView);
            this.imageView = itemView.findViewById(R.id.imageCardView);
            this.priceTextView = itemView.findViewById(R.id.housePriceTextView);
            this.squareMeters = itemView.findViewById(R.id.houseSquareMetersTextView);
            itemView.setOnClickListener(view -> listener.onClick(houses.get(getAdapterPosition())));
        }
    }

}
