package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AllAccAdapter;
import com.example.myapplication.adapters.AllBottomsAdapter;
import com.example.myapplication.models.AllAccModel;
import com.example.myapplication.models.AllBottomsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccessoriesActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    AllAccAdapter allAccAdapter;
    List<AllAccModel> allAccModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();
        String type=getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.accrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allAccModelList=new ArrayList<>();
        allAccAdapter=new AllAccAdapter(this, allAccModelList);
        recyclerView.setAdapter(allAccAdapter);
        if (type!=null && type.equalsIgnoreCase("Accessories"));
        firestore.collection("Accessories").whereEqualTo("type", "Accessories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                    AllAccModel allAccModel = documentSnapshot.toObject(AllAccModel.class);
                    allAccModelList.add(allAccModel);
                    allAccAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}