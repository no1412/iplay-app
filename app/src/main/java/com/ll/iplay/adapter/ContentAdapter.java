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

import com.bumptech.glide.Glide;
import com.ll.iplay.activity.ContentDetailActivity;
import com.ll.iplay.activity.R;
import com.ll.iplay.fragment.FoodFragment;
import com.ll.iplay.gson.ContentDescribe;
import com.ll.iplay.gson.FoodDescribe;

import java.util.List;

/**
 * Created by ll on 2017/5/2.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>{

    private List<ContentDescribe> contentDescribes;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentSurface;
        ImageView userHead;
        TextView contentTitle;
        TextView userNickName;
        LinearLayout contentId;

        public ViewHolder(View itemView) {
            super(itemView);
            contentId = (LinearLayout) itemView.findViewById(R.id.id_content_linear_layout_id);
            contentSurface = (ImageView) itemView.findViewById(R.id.id_content_surface);
            userHead = (ImageView) itemView.findViewById(R.id.id_user_head_pic);
            contentTitle = (TextView) itemView.findViewById(R.id.id_content_title);
            userNickName = (TextView) itemView.findViewById(R.id.id_user_nick_name);

        }
    }

    public ContentAdapter(Context mContext, List<ContentDescribe> contentDescribes) {
        this.mContext = mContext;
        this.contentDescribes = contentDescribes;
    }

    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContentAdapter.ViewHolder holder, final int position) {

        ContentDescribe contentDescribe = contentDescribes.get(position);
        //Glide.with(mContext).load(mFoodList.get(position).getFoodSurface()).into(holder.foodSurface);
        Glide
            .with(mContext)
            .load(contentDescribes.get(position).getSurface())
            .placeholder(R.drawable.place_holder)
            .override(200, 200) // resizes the image to these dimensions (in pixel)
            .centerCrop()
            //.fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(holder.contentSurface);
        //holder.foodSurface.setImageResource(food.getFoodSurface());
        Glide
            .with(mContext)
            .load(contentDescribes.get(position).getHeadPicUrl())
            .fitCenter() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
            .into(holder.userHead);
        holder.contentTitle.setText(contentDescribe.getTitle());
        holder.userNickName.setText(contentDescribe.getNickName());
        //holder.foodId.set(foodDescribe.getId());


        holder.contentSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentId = contentDescribes.get(position).getId();
                Intent intent = new Intent(v.getContext(), ContentDetailActivity.class);
                intent.putExtra("contentId",contentId);
                FoodFragment.foodFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentDescribes.size();
    }

    public List<ContentDescribe> getContentDescribes() {
        return contentDescribes;
    }

    public void setContentDescribes(List<ContentDescribe> contentDescribes) {
        this.contentDescribes = contentDescribes;
    }
}
