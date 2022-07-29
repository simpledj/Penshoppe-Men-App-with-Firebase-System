package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> myCartModelList;
    int total=0, grandtotal=0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> myCartModelList) {
        this.context = context;
        this.myCartModelList = myCartModelList;
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.name.setText(myCartModelList.get(position).getName());
        holder.price.setText(myCartModelList.get(position).getPrice());
        holder.quantity.setText(myCartModelList.get(position).getQuantity());
        holder.total.setText(String.valueOf("₱"+myCartModelList.get(position).getTotal()));

        grandtotal+=myCartModelList.get(position).getTotal();

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder")
                        .document(myCartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    myCartModelList.remove(myCartModelList.get(position));
                                    notifyDataSetChanged();
                                    grandtotal=0;
                                    Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        total=total+myCartModelList.get(position).getTotal();
        Intent intent=new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",grandtotal);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity, total;
        ImageView deleteItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cart_name);
            price=itemView.findViewById(R.id.cart_price);
            quantity=itemView.findViewById(R.id.cart_quantity);
            total=itemView.findViewById(R.id.cart_total);
            deleteItem=itemView.findViewById(R.id.delete);
        }
    }
}
