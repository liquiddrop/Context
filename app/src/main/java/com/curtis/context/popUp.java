package com.curtis.context;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Curtis on 8/19/2018.
 */

public class popUp extends Activity {
    String link;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");
        String city = intent.getStringExtra("CITY");
        String country = intent.getStringExtra("COUNTRY");
        String summary = intent.getStringExtra("FULL_SUMMARY");
        String picture_path = intent.getStringExtra("PICTURE_PATH");
        link = intent.getStringExtra("LINK");
        ImageView displayPicture = null;

        if(picture_path != null) {
            Bitmap bitmap = getBitmapFromAsset(picture_path);
            if (bitmap != null) {
                setContentView(R.layout.pop_up_window_picture);
                displayPicture = (ImageView) findViewById(R.id.picture);
                displayPicture.setImageBitmap(bitmap);
            } else {
                setContentView(R.layout.pop_up_window);
            }
        }
        else
        {
            setContentView(R.layout.pop_up_window);
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.3));

        TextView dateView = findViewById(R.id.Date);
        TextView cityView = findViewById(R.id.City);
        TextView countryView = findViewById(R.id.Country);
        TextView summaryView = findViewById(R.id.Summary);
        TextView linkView = findViewById(R.id.WebLink);

        dateView.setText("Date: " + date);
        cityView.setText("City: " + city);
        countryView.setText("Country: " + country);
        summaryView.setMovementMethod(new ScrollingMovementMethod());
        summaryView.setText(summary);
        if(link != null) {
            linkView.setText(link);
        }
        else
        {
            linkView.setVisibility(TextView.INVISIBLE);
        }
    }

    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}
