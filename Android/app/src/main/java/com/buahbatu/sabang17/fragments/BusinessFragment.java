package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buahbatu.sabang17.BusinessDetailActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.firebase.CustomChildEventListener;
import com.buahbatu.sabang17.firebase.CustomFirebaseImageLoader;
import com.buahbatu.sabang17.model.Business;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment {
    public static final String BUSINESS_DATA_KEY = "infoData";

    private static final String TAG = BusinessFragment.class.getSimpleName();
    private static final int rowDistance = 20;

    private int showedItem = 8;

    private RecyclerView mBusinessRecyclerView;
    private List<Business> businesses;

    private DatabaseReference businessesReference;

    private void populateChat(int stop){
        businessesReference.addChildEventListener(new CustomChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onBusinessAdded: "+dataSnapshot.getKey());

                // input new message
                Business business = Business.createBusinessObject(dataSnapshot);
                businesses.add(business);

                // notify data
                mBusinessRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onBusinessChanged: "+dataSnapshot.getKey());

                // get business data
                Business business = Business.createBusinessObject(dataSnapshot);

                for (Business data : businesses) {
                    if (TextUtils.equals(data.key, business.key)){
                        Log.i(TAG, "onBusinessSame found");
                        data.name = business.name;
                        data.desc = business.desc;
                        data.price = business.price;
                        mBusinessRecyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onMessageRemoved: "+dataSnapshot.getKey());

                // get info data
                String key = dataSnapshot.getKey();
                int selected = -1;

                for (int i = 0; i < businesses.size(); i++) {
                    Business data = businesses.get(i);
                    if (TextUtils.equals(data.key, key)){
                        Log.i(TAG, "onCriticSame found");
                        selected = i;
                        break;
                    }
                }

                if (selected != -1){
                    businesses.remove(selected);
                    mBusinessRecyclerView.getAdapter().notifyItemRemoved(selected);
                }
            }
        });
    }

    public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        return new BusinessFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        businesses = new ArrayList<>();

        // find views
        View root = inflater.inflate(R.layout.fragment_business, container, false);
        mBusinessRecyclerView = (RecyclerView)root.findViewById(R.id.business_recycler);

        // setup recycler
        mBusinessRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBusinessRecyclerView.setAdapter(new BusinessAdapter());

        /*MASIH PERLU PERBAIKAN*/
        businessesReference = FirebaseDatabase.getInstance()
                .getReference(getString(R.string.business_key));
        populateChat(showedItem);
        showedItem += rowDistance;  // move row distance

        return root;
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemDesc;
        TextView itemPrice;
        BusinessViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemDesc = (TextView)itemView.findViewById(R.id.item_desc);
            itemPrice = (TextView)itemView.findViewById(R.id.item_price);
        }
    }

    private class BusinessAdapter extends RecyclerView.Adapter<BusinessViewHolder>{
        @Override
        public BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View businessItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business, parent, false);
            return new BusinessViewHolder(businessItem);
        }

        @Override
        public void onBindViewHolder(final BusinessViewHolder holder, int position) {
            final Business data = businesses.get(position);

            // Reference to an image file in Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(getString(R.string.business_key))
                    .child(data.key + ".png");

            Drawable placeholder = VectorDrawableCompat.create(getResources(), R.drawable.ic_no_image_grey_24dp, null);

            // Load the image using Glide
            Glide.with(getContext())
                    .using(new CustomFirebaseImageLoader())
                    .load(storageReference)
                    .placeholder(placeholder)
                    .listener(new RequestListener<StorageReference, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.itemImage.setImageResource(R.drawable.ic_no_image_grey_24dp);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.itemImage.setScaleType(ImageView.ScaleType.FIT_XY);
                            return false;
                        }
                    })
                    .into(holder.itemImage);

            holder.itemName.setText(data.name);
            holder.itemDesc.setText(data.desc);
            holder.itemPrice.setText(data.price);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getContext(), BusinessDetailActivity.class);
                    intent.putExtra(BUSINESS_DATA_KEY, data);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return businesses.size();
        }
    }
}
