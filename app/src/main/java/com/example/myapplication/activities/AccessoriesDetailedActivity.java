package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.AllAccModel;
import com.example.myapplication.models.AllTopsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AccessoriesDetailedActivity extends AppCompatActivity {

    TextView acc_name_dtl, acc_price_dtl, acc_dec_dtl, quantity;
    Button addToCart;
    ImageView additem, removeitem, acc_img_dtl;
    int totalQuantity=1;
    int totalPrice=0;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    AllAccModel allAccModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories_detailed);

        firestore= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();

        final Object object=getIntent().getSerializableExtra("detail");
        if (object instanceof AllAccModel){
            allAccModel=(AllAccModel) object;
        }

        additem=findViewById(R.id.additem);
        removeitem=findViewById(R.id.removeitem);
        quantity=findViewById(R.id.quantity);

        acc_img_dtl=findViewById(R.id.acc_img_dtl);
        acc_name_dtl=findViewById(R.id.acc_name_dtl);
        acc_price_dtl=findViewById(R.id.acc_price_dtl);
        acc_dec_dtl=findViewById(R.id.acc_dec_dtl);

        if (allAccModel!=null){
            Glide.with(getApplicationContext()).load(allAccModel.getImg_url()).into(acc_img_dtl);
            acc_name_dtl.setText(allAccModel.getName());
            acc_price_dtl.setText("₱"+allAccModel.getPrice());
            acc_dec_dtl.setText(allAccModel.getDescription());
            totalPrice=allAccModel.getPrice()*totalQuantity;
        }

        addToCart=findViewById(R.id.addtocart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=allAccModel.getPrice()*totalQuantity;
                }
            }
        });
        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=allAccModel.getPrice()*totalQuantity;
                }
            }
        });

    }

    private void addedToCart() {
        final HashMap<String,Object> acccartMap=new HashMap<>();
        acccartMap.put("name",allAccModel.getName());
        acccartMap.put("price",acc_price_dtl.getText().toString());
        acccartMap.put("quantity",quantity.getText().toString());
        acccartMap.put("total", totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").add(acccartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(AccessoriesDetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}