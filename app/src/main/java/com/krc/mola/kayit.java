package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class kayit extends AppCompatActivity {
    private  static final String TAG = "MAIN_TAG";

    EditText isim,kadi,email,cinsiyet,yas;
    private FirebaseAuth mAuth;

    Button one,two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        mAuth = FirebaseAuth.getInstance();

        isim = findViewById(R.id.editTextTextPersonName);
        kadi = findViewById(R.id.editTextTextPersonName5);
        email = findViewById(R.id.editTextTextPersonName2);
        cinsiyet = findViewById(R.id.editTextTextPersonName3);
        yas = findViewById(R.id.editTextTextPersonName4);

        one = findViewById(R.id.button5);
        two = findViewById(R.id.button3);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kayitol(1);

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kayitol(2);

            }
        });

    }


    private <Int> void kayitol(Int deger) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> city = new HashMap<>();
        city.put("kadi", kadi.getText().toString());
        city.put("email", email.getText().toString());
        city.put("yas", yas.getText().toString());
        city.put("cinsiyet", cinsiyet.getText().toString());
        city.put("isim", isim.getText().toString());
        city.put("cins", deger.toString());
        city.put("dakika", 0);
        city.put("img", "https://firebasestorage.googleapis.com/v0/b/myarea-47b20.appspot.com/o/image-removebg-preview%20(13).png?alt=media&token=7854072c-0d8c-4cae-a7e6-9a2b87705422");
        city.put("numara",mAuth.getCurrentUser().getPhoneNumber());


        db.collection("users")
                .add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName("1")
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Intent intent = new Intent(getApplicationContext(),mainpage.class);
                                            intent.putExtra("oldum", "1"   );

                                            startActivity(intent);
                                        }
                                    }
                                });

                                           }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}