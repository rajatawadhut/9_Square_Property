package com.nine_square.property.Avtivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nine_square.property.R;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.picasso.PicassoImageLoader;


import java.util.ArrayList;

public class ViewImageActivity extends AppCompatActivity {

    ScrollGalleryView scrollGalleryView;
    SpinKitView progressBar;
    ArrayList<String> test;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Property Images");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        test = getIntent().getStringArrayListExtra("img");

        scrollGalleryView = findViewById(R.id.scroll_gallery_view);

        scrollGalleryView
                .setThumbnailSize(200)
                .setZoom(true)
                .withHiddenThumbnails(false)
                .hideThumbnailsOnClick(true)
                .hideThumbnailsAfter(5000)
                .addOnImageClickListener((position) -> {
                    Log.i(getClass().getName(), "You have clicked on image #" + position);
                })
                .setFragmentManager(getSupportFragmentManager());



        if(test != null && test.size()>0){
            for (String imageUrl : test) {
                scrollGalleryView.addMedia(
                        MediaInfo.mediaLoader(new PicassoImageLoader(imageUrl))
                );
            }
        }
    }


}