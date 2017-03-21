package com.buahbatu.sabang17;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buahbatu.sabang17.firebase.CustomFirebaseImageLoader;
import com.buahbatu.sabang17.fragments.InfoChildFragment;
import com.buahbatu.sabang17.model.Business;
import com.buahbatu.sabang17.model.Info;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InfoDetailActivity extends AppCompatActivity {

    private ImageView mItemImage;
    private TextView mItemTitle;
    private TextView mItemOrganizer;
    private TextView mItemDate;
    private TextView mItemTime;
    private TextView mItemDesc;

    private void setupViews(String infoType, Info data){
        mItemTitle.setText(data.title);
        mItemOrganizer.setText(data.organizer);
        if (!TextUtils.isEmpty(data.date_start)){
            String date = data.date_start;
            if (!TextUtils.isEmpty(data.date_end)){
                date += " s.d " + data.date_end;
            }
            mItemDate.setText(date);
        }else {
            mItemDate.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.time_start)){
            String time = data.time_start;
            if (!TextUtils.isEmpty(data.time_end)){
                time += " s.d " + data.time_end;
            }
            mItemTime.setText(time);
        }else {
            mItemTime.setVisibility(View.GONE);
        }
        mItemDesc.setText(data.desc);

        // Reference to an image file in Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(infoType)
                .child(data.key + ".png");

        // Load the image using Glide
        Glide.with(getApplicationContext())
                .using(new CustomFirebaseImageLoader())
                .load(storageReference)
                .placeholder(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_no_image_grey_24dp))
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mItemImage.setImageResource(R.drawable.ic_no_image_grey_24dp);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mItemImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        return false;
                    }
                })
                .into(mItemImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        // find views
        mItemImage = (ImageView) findViewById(R.id.item_image);
        mItemTitle = (TextView) findViewById(R.id.item_title);
        mItemOrganizer = (TextView) findViewById(R.id.item_organizer);
        mItemDate = (TextView) findViewById(R.id.item_date);
        mItemTime = (TextView) findViewById(R.id.item_time);
        mItemDesc = (TextView) findViewById(R.id.item_desc);

        // setup views
        String infoType = getIntent().getStringExtra(InfoChildFragment.INFO_TYPE_KEY);
        Info info = (Info) getIntent().getSerializableExtra(InfoChildFragment.INFO_DATA_KEY);
        setupViews(infoType, info);
    }
}
