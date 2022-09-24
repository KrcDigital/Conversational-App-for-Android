package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

public class mainpage extends AppCompatActivity {


    private FirebaseAuth mAuth;
    TextView kim,neci,dakika,lastu,lastdk;

    Button arama,tofav;
    ImageButton acc,buy;

    ImageView lastuserimg;
    Integer kontrol = 0;

    Integer dakik = 0;

    private  static final String TAG = "MAIN_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        mAuth = FirebaseAuth.getInstance();

        lastuserimg = findViewById(R.id.imageView3);
        kim = findViewById(R.id.textView10);
        dakika = findViewById(R.id.textView11);
        lastu = findViewById(R.id.textView13);
        lastdk = findViewById(R.id.textView14);
        arama = findViewById(R.id.button6);
        tofav = findViewById(R.id.button);
        acc = findViewById(R.id.imageButton4);
        buy = findViewById(R.id.imageButton3);



        arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),dinleyenfilter.class);
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

        tofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),favorilerim.class);
                startActivity(intent);
            }
        });
        oncesi();

        Log.d(TAG, "burada: "+mAuth.getCurrentUser().getDisplayName() );
        varmi();
        benkimim();

    }

    private void varmi() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("numara", mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                kontrol = 1;

                                getdatas();


                            }
                        } else {
                            kontrol = 0;
                            Intent intent = new Intent(getApplicationContext(),kayit.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Intent intent = new Intent(getApplicationContext(),kayit.class);
                        startActivity(intent);
                    }
                });

        if(mAuth.getCurrentUser().getDisplayName() == null){
            Intent intent = new Intent(getApplicationContext(),kayit.class);
            startActivity(intent);
        }



    }


    private void getdatas() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("numara", mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("cins").toString() == "2") {
                                    Log.d(TAG, "burada3: "+ document.get("isim").toString() );

                                    kim.setText("Dert Anlatan31,"+document.get("isim").toString());
                                    arama.setVisibility(View.VISIBLE);
                                }
                                if(document.get("cins").toString() == "1"){
                                    arama.setVisibility(View.INVISIBLE);

                                    kim.setText("Dert Dinleyen,"+document.get("isim").toString());
                                }
                                kim.setText("Hoşgeldin, "+document.get("isim").toString());

                                dakika.setText(document.get("dakika").toString()+ " Dakika");

                                dakik = Integer.parseInt(document.get("dakika").toString());


                            }
                        } else {

                        }
                    }
                });

    }


    private  void oncesi() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("gerceklesenarama")
                .whereEqualTo("konusan", mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                                db2.collection("users")
                                        .whereEqualTo("numara", document.get("dinleyen"))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document2 : task.getResult()) {





                                                        Picasso.get().load(document2.get("img").toString()).into(lastuserimg);
                                                        lastu.setText(document2.get("isim").toString());

                                                    }
                                                } else {
                                                }
                                            }
                                        });


                                lastdk.setText(document.get("dk" ).toString());







                            }
                        } else {
                        }
                    }
                });

    }


    private void benkimim() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("numara", mAuth.getCurrentUser().getPhoneNumber()).whereEqualTo("cins","1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Log.d(TAG, "olsorg23u: "+ document.get("cins").toString() );
                                Intent intent = new Intent(getApplicationContext(),dinleyenpage.class);
                                startActivity(intent);
                                if(document.get("cins") == "1") {



                                }

                            }
                        } else {

                        }
                    }
                });

    }



}