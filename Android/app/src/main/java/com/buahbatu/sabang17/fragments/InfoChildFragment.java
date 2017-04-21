package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.buahbatu.sabang17.InfoDetailActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.firebase.CustomChildEventListener;
import com.buahbatu.sabang17.firebase.CustomFirebaseImageLoader;
import com.buahbatu.sabang17.model.Info;
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

public class InfoChildFragment extends Fragment {
    public static final String INFO_TYPE_KEY = "infoType";
    public static final String INFO_DATA_KEY = "infoData";

    private static final String TAG = InfoChildFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final int rowDistance = 20;

    private int showedItem = 8;
    private String infoType;

    private RecyclerView mInfoRecycler;
    private String mInfoId;

    private List<Info> infoList;
    private DatabaseReference informationReference;

    private void populateChat(int stop){
        informationReference.addChildEventListener(new CustomChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onInfoAdded: "+dataSnapshot.getKey());

                // input new message
                Info info = Info.createInfoObject(dataSnapshot);
                infoList.add(0, info);

                // notify data
                mInfoRecycler.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onInfoChanged: "+dataSnapshot.getKey());

                // get info data
                Info info = Info.createInfoObject(dataSnapshot);

                for (Info data : infoList) {
                    if (TextUtils.equals(data.key, info.key)){
                        Log.i(TAG, "onMessageSame found");
                        data.title = info.title;
                        data.date_start = info.date_start;
                        data.date_end = info.date_end;
                        data.desc = info.desc;
                        data.organizer = info.organizer;
                        data.time_start = info.time_start;
                        data.time_end = info.time_end;
                        mInfoRecycler.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onInfoRemoved: "+dataSnapshot.getKey());

                // get critic data
                String key = dataSnapshot.getKey();
                int selected = -1;

                for (int i = 0; i < infoList.size(); i++) {
                    Info data = infoList.get(i);
                    if (TextUtils.equals(data.key, key)){
                        Log.i(TAG, "onCriticSame found");
                        selected = i;
                        break;
                    }
                }

                if (selected != -1){
                    infoList.remove(selected);
                    mInfoRecycler.getAdapter().notifyItemRemoved(selected);
                }
            }
        });
    }

    public InfoChildFragment() {
        // Required empty public constructor
    }

    public static InfoChildFragment newInstance(String infoId) {
        InfoChildFragment fragment = new InfoChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, infoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInfoId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instantiate list
        infoList = new ArrayList<>();

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info_child, container, false);
        mInfoRecycler = (RecyclerView)root.findViewById(R.id.info_child_recycler);


        // setup recycler
        // handle different info
        switch (mInfoId){
            case "opinion":
                infoType = "opinion";
                mInfoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                informationReference = FirebaseDatabase.getInstance()
                        .getReference(getString(R.string.opinion_key));
                break;
            case "cadre":
                infoType = "cadre";
                mInfoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                informationReference = FirebaseDatabase.getInstance()
                        .getReference(getString(R.string.cadre_key));
                break;
            case "event":
                infoType = "event";
                mInfoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                informationReference = FirebaseDatabase.getInstance()
                        .getReference(getString(R.string.event_key));
                break;
            default:
                infoType = "event";
                mInfoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                informationReference = FirebaseDatabase.getInstance()
                        .getReference(getString(R.string.event_key));
                break;
        }
        mInfoRecycler.setAdapter(new InfoChildAdapter());

        /*MASIH PERLU PERBAIKAN*/
        populateChat(showedItem);
        showedItem += rowDistance;  // move row distance

        return root;
    }

    class InfoChildViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDesc;
        InfoChildViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemDesc = (TextView)itemView.findViewById(R.id.item_desc);
        }
    }

    private class InfoChildAdapter extends RecyclerView.Adapter<InfoChildViewHolder>{
        @Override
        public InfoChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
            return new InfoChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final InfoChildViewHolder holder, int position) {
            final Info data = infoList.get(position);

            // Reference to an image file in Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(infoType)
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

            holder.itemName.setText(data.title);
            holder.itemDesc.setText(data.desc);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getContext(), InfoDetailActivity.class);
                    intent.putExtra(INFO_TYPE_KEY, infoType);
                    intent.putExtra(INFO_DATA_KEY, data);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }
}
