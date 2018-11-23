package smt.ort.houses.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smt.ort.houses.R;
import smt.ort.houses.model.Favorite;
import smt.ort.houses.util.StringUtil;

public class FavoriteViewHolder extends BaseViewHolder<Favorite, OnRecyclerObjectClickListener> {

    private TextView titleView;
    private TextView priceTextView;
    private ImageView imageView;
    private TextView squareMeters;

    public FavoriteViewHolder(View itemView, OnRecyclerObjectClickListener listener) {
        super(itemView, listener);
        initViews();
    }

    private void initViews() {
        this.titleView = itemView.findViewById(R.id.houseTitleTextView);
        this.imageView = itemView.findViewById(R.id.imageCardView);
        this.priceTextView = itemView.findViewById(R.id.housePriceTextView);
        this.squareMeters = itemView.findViewById(R.id.houseSquareMetersTextView);
        itemView.setOnClickListener(v -> getListener().onItemClicked(getAdapterPosition()));
    }

    @Override
    public void onBind(Favorite item) {
        titleView.setText(item.getTitle());
        priceTextView.setText(StringUtil.formatCurrency(item.getPrice()));
        squareMeters.setText(StringUtil.formatSquareMeters(item.getSquareMeters()));

        // Set photo
        if (item.getPhotos().size() > 0) {
            Picasso.get().load(item.getPhotos().get(0).getUrl()).placeholder(R.drawable.camera).into(imageView);
        } else {
            Picasso.get().load(R.drawable.camera).into(imageView);
        }
    }
}
