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

public class BottomsDetailedActivity extends AppCompatActivity {

    TextView bot_name_dtl, bot_price_dtl, bot_dec_dtl, quantity;
    Button addToCart;
    ImageView additem, removeitem, bot_img_dtl;
    int totalQuantity=1;
    int totalPrice=0;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    AllBottomsModel allBottomsModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottoms_detailed);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final Object object=getIntent().getSerializableExtra("detail");
        if (object instanceof AllBottomsModel){
            allBottomsModel=(AllBottomsModel) object;
        }

        additem=findViewById(R.id.additem);
        removeitem=findViewById(R.id.removeitem);
        quantity=findViewById(R.id.quantity);

        bot_img_dtl=findViewById(R.id.bot_img_dtl);
        bot_name_dtl=findViewById(R.id.bot_name_dtl);
        bot_price_dtl=findViewById(R.id.bot_price_dtl);
        bot_dec_dtl=findViewById(R.id.bot_dec_dtl);

        if (allBottomsModel!=null){
            Glide.with(getApplicationContext()).load(allBottomsModel.getImg_url()).into(bot_img_dtl);
            bot_name_dtl.setText(allBottomsModel.getName());
            bot_price_dtl.setText("₱"+allBottomsModel.getPrice());
            bot_dec_dtl.setText(allBottomsModel.getDescription());
            totalPrice=allBottomsModel.getPrice()*totalQuantity;
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
                    totalPrice=allBottomsModel.getPrice()*totalQuantity;

                }
            }
        });
        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=allBottomsModel.getPrice()*totalQuantity;

                }
            }
        });

    }
    private void addedToCart() {
        final HashMap<String,Object> botcartMap=new HashMap<>();
        botcartMap.put("name",allBottomsModel.getName());
        botcartMap.put("price",bot_price_dtl.getText().toString());
        botcartMap.put("quantity",quantity.getText().toString());
        botcartMap.put("total", totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").add(botcartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(BottomsDetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}