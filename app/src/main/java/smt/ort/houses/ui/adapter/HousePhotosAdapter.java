package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.model.Photo;

public class HousePhotosAdapter extends PagerAdapter {

    LayoutInflater mLayoutInflater;
    List<Photo> mPhotos;

    public HousePhotosAdapter(Context ctx, List<Photo> photos) {
        mLayoutInflater = LayoutInflater.from(ctx);
        mPhotos = photos;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.photo_pager_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageViewDetailPhoto);

        Picasso.get().load(mPhotos.get(position).getUrl()).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
