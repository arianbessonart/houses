package smt.ort.houses.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadingImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;


    public DownloadingImageTask(ImageView bmImage) {
        this.imageView = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlImage = urls[0];
        InputStream in;
        Bitmap bmp = null;
        try {
            in = new java.net.URL(urlImage).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }


}
