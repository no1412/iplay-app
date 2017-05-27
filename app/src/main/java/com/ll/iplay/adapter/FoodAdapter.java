package com.ll.iplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ll.iplay.activity.ContentDetailActivity;
import com.ll.iplay.activity.MainActivity;
import com.ll.iplay.activity.R;
import com.ll.iplay.fragment.FoodFragment;
import com.ll.iplay.gson.FoodDescribe;

import org.w3c.dom.Text;

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
        LinearLayout foodId;

        public ViewHolder(View itemView) {
            super(itemView);
            foodId = (LinearLayout) itemView.findViewById(R.id.id_food_id);
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
    public void onBindViewHolder(FoodAdapter.ViewHolder holder, final int position) {

        FoodDescribe foodDescribe = foodDescribes.get(position);
        //Glide.with(mContext).load(mFoodList.get(position).getFoodSurface()).into(holder.foodSurface);
        Glide
            .with(mContext)
            .load(foodDescribes.get(position).getSurface())
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
        //holder.foodId.set(foodDescribe.getId());


        holder.foodSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentId = foodDescribes.get(position).getId();
                Intent intent = new Intent(v.getContext(), ContentDetailActivity.class);
                intent.putExtra("contentId",contentId);
                FoodFragment.foodFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDescribes.size();
    }
}
