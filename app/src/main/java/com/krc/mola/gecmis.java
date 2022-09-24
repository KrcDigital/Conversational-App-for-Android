package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class gecmis extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    List<gecjava> geclist;
    gecjava imagearr;
    ImageButton acc,buy,main;

    LinearLayoutManager linearLayoutManager;
    gecadap adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis);

        mAuth = FirebaseAuth.getInstance();

        acc = findViewById(R.id.imageButton4);
        buy = findViewById(R.id.imageButton3);
        main = findViewById(R.id.imageButton2);
        recyclerView = findViewById(R.id.favrec);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        geclist = new ArrayList<>();
        adapter = new gecadap(this, geclist);
        recyclerView.setAdapter(adapter);


        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),mainpage.class);
                startActivity(intent);
            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),profilim.class);
                startActivity(intent);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),market.class);
                startActivity(intent);
            }
        });


        getgecmis();

    }



    private void getgecmis() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dinleyicibekleyen")
                .whereEqualTo("numara", mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                                db2.collection("users")
                                        .whereEqualTo("numara", document.get("gelen"))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                                        imagearr = new gecjava(document.get("img").toString(),document.get("isim").toString(),"");
                                                        geclist.add(imagearr);
                                                        adapter.notifyDataSetChanged();


                                                    }
                                                } else {
                                                }
                                            }
                                        });


                            }
                        } else {
                        }
                    }
                });

    }
}