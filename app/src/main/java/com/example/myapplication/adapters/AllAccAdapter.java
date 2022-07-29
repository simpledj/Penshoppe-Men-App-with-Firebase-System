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
import com.example.myapplication.activities.AccessoriesDetailedActivity;
import com.example.myapplication.activities.DetailedActivity;
import com.example.myapplication.models.AllAccModel;
import com.example.myapplication.models.NavAccessoriesModel;

import java.util.List;

public class AllAccAdapter extends RecyclerView.Adapter<AllAccAdapter.ViewHolder> {

    Context context;
    List<AllAccModel> list;

    public AllAccAdapter(Context context, List<AllAccModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllAccAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_half_detail,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllAccAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("₱"+list.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AccessoriesDetailedActivity.class);
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
        TextView name, price,type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.top_half_img);
            name=itemView.findViewById(R.id.top_half_name);
            price=itemView.findViewById(R.id.top_half_price);
        }
    }
}
