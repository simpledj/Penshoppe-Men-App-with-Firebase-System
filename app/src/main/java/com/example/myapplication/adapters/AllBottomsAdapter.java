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
import com.example.myapplication.activities.BottomsDetailedActivity;
import com.example.myapplication.activities.DetailedActivity;
import com.example.myapplication.models.AllBottomsModel;
import com.example.myapplication.models.AllTopsModel;

import java.util.List;

public class AllBottomsAdapter extends RecyclerView.Adapter<AllBottomsAdapter.ViewHolder> {

    Context context;
    List<AllBottomsModel> list;

    public AllBottomsAdapter(Context context, List<AllBottomsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllBottomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tops_half_detail,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllBottomsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("₱"+list.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BottomsDetailedActivity.class);
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
