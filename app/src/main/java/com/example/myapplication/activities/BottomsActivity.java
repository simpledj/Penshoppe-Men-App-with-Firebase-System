package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AllBottomsAdapter;
import com.example.myapplication.adapters.AllTopAdapter;
import com.example.myapplication.models.AllBottomsModel;
import com.example.myapplication.models.AllTopsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BottomsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    AllBottomsAdapter allBottomsAdapter;
    List<AllBottomsModel> allBottomsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottoms);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();
        String type=getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.botrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allBottomsModelList=new ArrayList<>();
        allBottomsAdapter=new AllBottomsAdapter(this, allBottomsModelList);
        recyclerView.setAdapter(allBottomsAdapter);
        if (type!=null && type.equalsIgnoreCase("Bottoms"));
        firestore.collection("Bottoms").whereEqualTo("type", "Bottoms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                    AllBottomsModel allBottomsModel = documentSnapshot.toObject(AllBottomsModel.class);
                    allBottomsModelList.add(allBottomsModel);
                    allBottomsAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}