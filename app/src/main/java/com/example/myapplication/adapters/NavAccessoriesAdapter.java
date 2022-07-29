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
import com.example.myapplication.activities.AccessoriesActivity;
import com.example.myapplication.activities.BottomsActivity;
import com.example.myapplication.models.NavAccessoriesModel;

import java.util.List;

public class NavAccessoriesAdapter extends RecyclerView.Adapter<NavAccessoriesAdapter.ViewHolder> {

    Context context;
    List<NavAccessoriesModel> list;

    public NavAccessoriesAdapter(Context context, List<NavAccessoriesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NavAccessoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.accessories_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavAccessoriesAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AccessoriesActivity.class);
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
            imageView=itemView.findViewById(R.id.acc_img);
            name=itemView.findViewById(R.id.acc_name);
        }
    }
}
