package com.ramki.myexercise.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ramki.myexercise.R;
import com.ramki.myexercise.data.model.Information;

import java.util.ArrayList;

/**
 * Created by Ramki on 2/11/2018.
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewHolder> {
    private Context mContext;
    private ArrayList<Information> mInformations;

    public InformationAdapter(Context mContext, ArrayList<Information> mInformations) {
        this.mContext = mContext;
        this.mInformations = mInformations;
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_list_item, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InformationViewHolder holder, int position) {
        Information information = mInformations.get(position);
        if (!TextUtils.isEmpty(information.getTitle()))
            holder.tvTitle.setText(information.getTitle());
        else
            holder.tvTitle.setText("");

        if (!TextUtils.isEmpty(information.getDescription()))
            holder.tvDescription.setText(information.getDescription());
        else
            holder.tvDescription.setText("");
        String url = information.getImageUrl();
        if (!TextUtils.isEmpty(url))
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.image_not_found)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.image_not_found)
                    .into(holder.ivImage);
        else
            holder.ivImage.setImageResource(R.drawable.image_not_found);
    }

    @Override
    public int getItemCount() {
        return (mInformations != null) ? mInformations.size() : 0;
    }

    public class InformationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        ImageView ivImage;

        public InformationViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
            ivImage = (ImageView) itemView.findViewById(R.id.imageView);


        }
    }
}
