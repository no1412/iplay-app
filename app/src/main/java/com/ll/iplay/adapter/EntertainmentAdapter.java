package com.ll.iplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ll.iplay.activity.R;
import com.ll.iplay.gson.EntertainmentDescribe;
import com.ll.iplay.gson.FoodDescribe;

import java.util.List;

/**
 * Created by ll on 2017/5/2.
 */

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.ViewHolder>{

    private List<EntertainmentDescribe> entertainmentDescribes;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView entertainmentSurface;
        ImageView userHead;
        TextView foodTitle;
        TextView userNickName;
        TextView entertainmentId;

        public ViewHolder(View itemView) {
            super(itemView);
            entertainmentSurface = (ImageView) itemView.findViewById(R.id.id_entertainment_surface);
            userHead = (ImageView) itemView.findViewById(R.id.id_user_head_pic);
            foodTitle = (TextView) itemView.findViewById(R.id.id_entertainment_title);
            userNickName = (TextView) itemView.findViewById(R.id.id_user_nick_name);
            entertainmentId = (TextView) itemView.findViewById(R.id.id_entertainment_id);
        }
    }

    public EntertainmentAdapter(Context mContext, List<EntertainmentDescribe> entertainmentDescribes) {
        this.mContext = mContext;
        this.entertainmentDescribes = entertainmentDescribes;
    }

    @Override
    public EntertainmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entertainment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EntertainmentAdapter.ViewHolder holder, int position) {

        EntertainmentDescribe entertainmentDescribe = entertainmentDescribes.get(position);
        //Glide.with(mContext).load(mFoodList.get(position).getFoodSurface()).into(holder.foodSurface);
        Glide
            .with(mContext)
            .load(entertainmentDescribes.get(position).getEntertainmentSurface())
            .override(500, 600) // resizes the image to these dimensions (in pixel)
            .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(holder.entertainmentSurface);
        //holder.foodSurface.setImageResource(food.getFoodSurface());
        Glide
            .with(mContext)
            .load(entertainmentDescribes.get(position).getHeadPicUrl())
            .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(holder.userHead);
        holder.foodTitle.setText(entertainmentDescribe.getTitle());
        holder.userNickName.setText(entertainmentDescribe.getNickName());
        holder.entertainmentId.setText(entertainmentDescribe.getId());
    }

    @Override
    public int getItemCount() {
        return entertainmentDescribes.size();
    }
}
