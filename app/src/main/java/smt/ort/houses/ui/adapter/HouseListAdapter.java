package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.task.DownloadingImageTask;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseViewHolder> {

    private final LayoutInflater mInflater;
    private List<House> houses;

    public HouseListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.house_list_item, parent, false);
        return new HouseViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House hCurrent = houses.get(position);
        holder.titleView.setText(hCurrent.getTitle());
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String formattedPrice = format.format(hCurrent.getPrice());

        holder.priceTextView.setText(formattedPrice);
        if (hCurrent.getPhotos().size() > 0) {
            new DownloadingImageTask(holder.imageView).execute(hCurrent.getPhotos().get(0));
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

        public HouseViewHolder(@NonNull View itemView, HouseListAdapter adapter) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.houseTitleTextView);
            this.imageView = itemView.findViewById(R.id.imageCardView);
            this.priceTextView = itemView.findViewById(R.id.housePriceTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}
