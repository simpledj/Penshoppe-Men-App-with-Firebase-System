package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AllTopAdapter;
import com.example.myapplication.models.AllTopsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    AllTopAdapter allTopAdapter;
    List<AllTopsModel> allTopsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tops);
        getSupportActionBar().hide();

        firestore=FirebaseFirestore.getInstance();
        String type=getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.toprecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allTopsModelList=new ArrayList<>();
        allTopAdapter=new AllTopAdapter(this, allTopsModelList);
        recyclerView.setAdapter(allTopAdapter);
        if (type!=null && type.equalsIgnoreCase("Tops"));
        firestore.collection("Tops").whereEqualTo("type", "Tops").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                   AllTopsModel allTopsModel = documentSnapshot.toObject(AllTopsModel.class);
                   allTopsModelList.add(allTopsModel);
                   allTopAdapter.notifyDataSetChanged();
               }
            }
        });
    }
}