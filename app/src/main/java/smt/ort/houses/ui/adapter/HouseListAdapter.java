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

import java.text.NumberFormat;
import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.House;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseViewHolder> {

    private final LayoutInflater mInflater;
    OnHouseListListener listener;
    private List<House> houses;

    public HouseListAdapter(Context context, OnHouseListListener listener) {
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.house_list_item, parent, false);
        return new HouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House hCurrent = houses.get(position);
        holder.titleView.setText(hCurrent.getTitle());
        String formattedPrice = hCurrent.getPrice();

        holder.priceTextView.setText(formattedPrice);
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


    class HouseViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView priceTextView;
        private ImageView imageView;

        HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.houseTitleTextView);
            this.imageView = itemView.findViewById(R.id.imageCardView);
            this.priceTextView = itemView.findViewById(R.id.housePriceTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onHouseSelected(houses.get(getAdapterPosition()));
                }
            });
        }
    }

}
