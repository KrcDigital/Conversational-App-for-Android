package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

public class dinleyenfilter extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button ara,iptal;
    EditText detayim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinleyenfilter);
        mAuth = FirebaseAuth.getInstance();

        ara = findViewById(R.id.button4);
        iptal = findViewById(R.id.button2);

        detayim = findViewById(R.id.editTextTextMultiLine);

        oncesi();
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mainpage.class);
                startActivity(intent);

            }
        });
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayit();



            }
        });


    }


    private  void kayit() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> city = new HashMap<>();
        city.put("detay", detayim.getText().toString());
        city.put("durum", "0");
        city.put("gelen", "-");


        city.put("numara",mAuth.getCurrentUser().getPhoneNumber() );


        db.collection("dinleyicibekleyen")
                .add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Intent intent = new Intent(getApplicationContext(),bekleme_konusan.class);

                        intent.putExtra("idsis", documentReference.getId().toString()  );
                        startActivity(intent);



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }




    private  void oncesi() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dinleyicibekleyen")
                .whereEqualTo("numara", mAuth.getCurrentUser().getPhoneNumber()).whereEqualTo("durum" ,"0")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                                db2.collection("dinleyicibekleyen").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });



                            }
                        } else {
                        }
                    }
                });

    }

}