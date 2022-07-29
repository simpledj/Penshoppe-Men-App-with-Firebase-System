package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.NavAccessoriesAdapter;
import com.example.myapplication.adapters.NavBottomsAdapter;
import com.example.myapplication.adapters.NavTopsAdapter;
import com.example.myapplication.models.NavAccessoriesModel;
import com.example.myapplication.models.NavBottomsModel;
import com.example.myapplication.models.NavTopsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView topRec, botRec, accRec;
    FirebaseFirestore db;
    //Tops Items
    List<NavTopsModel> navTopsModelList;
    NavTopsAdapter navTopsAdapter;
    //Bottoms Items
    List<NavBottomsModel> navBottomsModelList;
    NavBottomsAdapter navBottomsAdapter;
    //Accessories
    List<NavAccessoriesModel> navAccessoriesModelList;
    NavAccessoriesAdapter navAccessoriesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db=FirebaseFirestore.getInstance();

        //Tops Items
        topRec=root.findViewById(R.id.recyclertop);
        topRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        navTopsModelList=new ArrayList<>();
        navTopsAdapter=new NavTopsAdapter(getActivity(),navTopsModelList);
        topRec.setAdapter(navTopsAdapter);
        db.collection("Tops")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavTopsModel navTopsModel = document.toObject(NavTopsModel.class);
                                navTopsModelList.add(navTopsModel);
                                navTopsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Bottoms Items
        botRec=root.findViewById(R.id.recyclerbottom);
        botRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        navBottomsModelList=new ArrayList<>();
        navBottomsAdapter=new NavBottomsAdapter(getActivity(),navBottomsModelList);
        botRec.setAdapter(navBottomsAdapter);
        db.collection("Bottoms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavBottomsModel navBottomsModel = document.toObject(NavBottomsModel.class);
                                navBottomsModelList.add(navBottomsModel);
                                navBottomsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Accessories
        accRec=root.findViewById(R.id.recycleraccessories);
        accRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        navAccessoriesModelList=new ArrayList<>();
        navAccessoriesAdapter=new NavAccessoriesAdapter(getActivity(),navAccessoriesModelList);
        accRec.setAdapter(navAccessoriesAdapter);
        db.collection("Accessories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavAccessoriesModel navAccessoriesModel = document.toObject(NavAccessoriesModel.class);
                                navAccessoriesModelList.add(navAccessoriesModel);
                                navAccessoriesAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }
}