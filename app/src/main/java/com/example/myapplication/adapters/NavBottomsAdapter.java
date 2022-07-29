package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activities.BottomsActivity;
import com.example.myapplication.activities.TopsActivity;
import com.example.myapplication.models.NavBottomsModel;

import java.util.List;

public class NavBottomsAdapter extends RecyclerView.Adapter<NavBottomsAdapter.ViewHolder> {

    Context context;
    List<NavBottomsModel> list;

    public NavBottomsAdapter(Context context, List<NavBottomsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NavBottomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottoms_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavBottomsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BottomsActivity.class);
                intent.putExtra("type", list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.bot_img);
            name=itemView.findViewById(R.id.bot_name);
        }
    }
}
