package com.warriorminds.astropics.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.warriorminds.astropics.R;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.utils.Constants;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<Image> imageArrayList;
    private Context mActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public View mView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.tvImageName);
            mImageView = (ImageView) view.findViewById(R.id.ivImage);
        }
    }

    public ImageAdapter(ArrayList<Image> images, Context activity) {
        imageArrayList = images;
        mActivity = activity;
    }

    public ArrayList<Image> getImageArrayList(){
        return imageArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Image image = imageArrayList.get(position);
        holder.mTextView.setText(image.getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mActivity, PictureDetailActivity.class);
                intent.putExtra(Constants.IMAGE_EXTRA, image);
                mActivity.startActivity(intent);*/
            }
        });

        Glide.with(mActivity)
                .load(image.getUrlGallery())
                .centerCrop()
                        //.placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (imageArrayList != null)
            return imageArrayList.size();
        else
            return 0;
    }
}
