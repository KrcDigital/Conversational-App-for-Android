package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class dinleyenpage extends AppCompatActivity {

    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth mAuth;
    TextView kim,neci,dakika,lastu,lastdk;

    Button arama;
    ImageButton acc,buy;

    ImageView lastuserimg;
    Integer kontrol = 0;

    Integer dakik = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinleyenpage);

        mAuth = FirebaseAuth.getInstance();

        lastuserimg = findViewById(R.id.imageView3);
        kim = findViewById(R.id.textView10);
        dakika = findViewById(R.id.textView11);
        lastu = findViewById(R.id.textView13);
        lastdk = findViewById(R.id.textView14);
        arama = findViewById(R.id.button16);
        acc = findViewById(R.id.imageButton4);
        buy = findViewById(R.id.imageButton3);



        arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),bekleme_dinleyen.class);
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
        oncesi();
        getdatas();
        Log.d(TAG, "burada: "+mAuth.getCurrentUser().getPhoneNumber() );


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
                .whereEqualTo("dinleyen", mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                                db2.collection("users")
                                        .whereEqualTo("numara", document.get("konusan"))
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







}