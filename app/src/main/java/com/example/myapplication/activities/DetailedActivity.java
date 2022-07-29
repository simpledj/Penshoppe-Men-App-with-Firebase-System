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
import com.example.myapplication.models.AllBottomsModel;
import com.example.myapplication.models.AllTopsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    TextView prodname, prodprice, highlights, quantity;
    Button addToCart;
    ImageView additem, removeitem, prodimg;
    int totalQuantity=1;
    int totalPrice=0;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    AllTopsModel allTopsModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final Object object=getIntent().getSerializableExtra("detail");
        if (object instanceof AllTopsModel){
            allTopsModel=(AllTopsModel) object;
        }

        additem=findViewById(R.id.additem);
        removeitem=findViewById(R.id.removeitem);
        quantity=findViewById(R.id.quantity);

        prodimg=findViewById(R.id.prodimg);
        prodname=findViewById(R.id.prodname);
        prodprice=findViewById(R.id.prodprice);
        highlights=findViewById(R.id.highlights);

        if (allTopsModel!=null){
            Glide.with(getApplicationContext()).load(allTopsModel.getImg_url()).into(prodimg);
            prodname.setText(allTopsModel.getName());
            prodprice.setText("₱"+allTopsModel.getPrice());
            highlights.setText(allTopsModel.getDescription());
            totalPrice=allTopsModel.getPrice()*totalQuantity;
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
                    totalPrice=allTopsModel.getPrice()*totalQuantity;
                }
            }
        });
        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=allTopsModel.getPrice()*totalQuantity;
                }
            }
        });

    }

    private void addedToCart() {
        final HashMap<String,Object> topcartMap=new HashMap<>();
        topcartMap.put("name",allTopsModel.getName());
        topcartMap.put("price",prodprice.getText().toString());
        topcartMap.put("quantity",quantity.getText().toString());
        topcartMap.put("total", totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").add(topcartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}