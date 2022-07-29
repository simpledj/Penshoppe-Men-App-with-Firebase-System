package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.RegistrationActivity;
import com.example.myapplication.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        List<MyCartModel> list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");
        if (list!=null && list.size()>0){
            for (MyCartModel model: list){
                final HashMap<String,Object> topcartMap=new HashMap<>();
                topcartMap.put("name",model.getName());
                topcartMap.put("price",model.getPrice());
                topcartMap.put("quantity",model.getQuantity());
                topcartMap.put("total",model.getTotal());

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").add(topcartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PlaceOrderActivity.this, "Your Order has Been Placed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }


    }
}