package com.buahbatu.sabang17;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.buahbatu.sabang17.firebase.CustomFirebaseImageLoader;
import com.buahbatu.sabang17.fragments.BusinessFragment;
import com.buahbatu.sabang17.model.Business;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BusinessDetailActivity extends AppCompatActivity {

    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mItemDesc;

    private void setupViews(Business data){
        mItemName.setText(data.name);
        mItemPrice.setText(data.price);
        mItemDesc.setText(data.desc);

        // Reference to an image file in Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(getString(R.string.business_key))
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
        setContentView(R.layout.activity_business_detail);

        // find views
        mItemImage = (ImageView) findViewById(R.id.item_image);
        mItemName = (TextView) findViewById(R.id.item_name);
        mItemPrice = (TextView) findViewById(R.id.item_price);
        mItemDesc = (TextView) findViewById(R.id.item_desc);

        // setup views
        Business business = (Business) getIntent().getSerializableExtra(BusinessFragment.BUSINESS_DATA_KEY);
        setupViews(business);
    }
}
