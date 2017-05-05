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
import com.ll.iplay.gson.FoodDescribe;

import java.util.List;

/**
 * Created by ll on 2017/5/2.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{

    private List<FoodDescribe> foodDescribes;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodSurface;
        ImageView userHead;
        TextView foodTitle;
        TextView userNickName;

        public ViewHolder(View itemView) {
            super(itemView);
            foodSurface = (ImageView) itemView.findViewById(R.id.id_food_surface);
            userHead = (ImageView) itemView.findViewById(R.id.id_user_head_pic);
            foodTitle = (TextView) itemView.findViewById(R.id.id_food_title);
            userNickName = (TextView) itemView.findViewById(R.id.id_user_nick_name);
        }
    }

    public FoodAdapter(Context mContext, List<FoodDescribe> foodDescribes) {
        this.mContext = mContext;
        this.foodDescribes = foodDescribes;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.ViewHolder holder, int position) {

        FoodDescribe foodDescribe = foodDescribes.get(position);
        //Glide.with(mContext).load(mFoodList.get(position).getFoodSurface()).into(holder.foodSurface);
        Glide
            .with(mContext)
            .load(foodDescribes.get(position).getFoodSurface())
            .override(500, 600) // resizes the image to these dimensions (in pixel)
            .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(holder.foodSurface);
        //holder.foodSurface.setImageResource(food.getFoodSurface());
        Glide
            .with(mContext)
            .load(foodDescribes.get(position).getHeadPicUrl())
            .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(holder.userHead);
        holder.foodTitle.setText(foodDescribe.getTitle());
        holder.userNickName.setText(foodDescribe.getNickName());
    }

    @Override
    public int getItemCount() {
        return foodDescribes.size();
    }
}
