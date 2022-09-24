package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class bekleme_dinleyen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private boolean running;
    private int seconds = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bekleme_dinleyen);
        mAuth = FirebaseAuth.getInstance();
        runTimer();
        running = true;
        getsorgu();
    }

    private void getsorgu() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dinleyicibekleyen")
                .whereEqualTo("durum", "0")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {




                            /////
                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                                DocumentReference washingtonRef = db2.collection("dinleyicibekleyen").document(document.getId());

                                washingtonRef
                                        .update("durum", "1","gelen",mAuth.getCurrentUser().getPhoneNumber().toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {



                                                Intent intent = new Intent(getApplicationContext(),sohbetpage.class);
                                                intent.putExtra("idsi", document.getId());
                                                intent.putExtra("nerden", "0");
                                                intent.putExtra("dirdir", document.get("numara").toString());
                                                startActivity(intent);


                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });

                                ////////












                            }
                        } else {

                        }
                    }
                });

    }

    private  void updater() {
        Log.d("sorgu5", "olması gereken");
        Log.d("sorgu6", getIntent().getStringExtra("idsi"));




    }

    private void runTimer()
    {

        // Get the text view.
        final TextView timeView
                = (TextView)findViewById(
                R.id.textView16);

        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                running = true;
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;




                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                    time = String
                            .format(Locale.getDefault(),
                                    "%d:%02d:%02d", hours,
                                    minutes, secs);
                    timeView.setText(time);

                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

}