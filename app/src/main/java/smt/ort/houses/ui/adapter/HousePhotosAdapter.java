package smt.ort.houses.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import java.util.List;

import smt.ort.houses.R;
import smt.ort.houses.task.DownloadingImageTask;

public class HousePhotosAdapter extends PagerAdapter {

    LayoutInflater mLayoutInflater;
    List<String> mPhotos;

    public HousePhotosAdapter(Context ctx, List<String> photos) {
        mLayoutInflater = LayoutInflater.from(ctx);
        mPhotos = photos;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.photo_pager_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageViewDetailPhoto);

        new DownloadingImageTask(imageView).execute(mPhotos.get(position));
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
