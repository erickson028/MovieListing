package com.gienapps.movielisting.Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by erick on 10/20/17.
 */

public class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

    ImageView ivImage;

    public ImageLoaderTask(ImageView ivImage) {
        this.ivImage = ivImage;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        for (String url :
                params) {
            try {
                URL bitmapUrl = new URL(url);
                return BitmapFactory.decodeStream(bitmapUrl.openConnection().getInputStream());
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ivImage.setImageBitmap(bitmap);
    }
}
