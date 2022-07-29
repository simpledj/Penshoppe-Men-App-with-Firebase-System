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
import com.example.myapplication.activities.DetailedActivity;
import com.example.myapplication.activities.TopsActivity;
import com.example.myapplication.models.AllTopsModel;

import java.util.List;

public class AllTopAdapter extends RecyclerView.Adapter<AllTopAdapter.ViewHolder> {

    Context context;
    List<AllTopsModel> list;

    public AllTopAdapter(Context context, List<AllTopsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllTopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_half_detail,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllTopAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("₱"+list.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailedActivity.class);
                intent.putExtra("detail", list.get(position));
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
        TextView name, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.top_half_img);
            name=itemView.findViewById(R.id.top_half_name);
            price=itemView.findViewById(R.id.top_half_price);
        }
    }
}
