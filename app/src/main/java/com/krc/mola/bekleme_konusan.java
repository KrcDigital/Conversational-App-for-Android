package com.krc.mola;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class bekleme_konusan extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private  static final String TAG = "MAIN_TAG";
    Integer anan = 0;

    // Is the stopwatch running?
    private boolean running;
    private int seconds = 0;
    Button iptal;
    TextView sayac;
    private boolean wasRunning;
    private Object idsiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bekleme_konusan);
        mAuth = FirebaseAuth.getInstance();

        iptal = findViewById(R.id.button4);

        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               oncesi();




            }
        });


        runTimer();
        running = true;
        getsorgu();

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

                                                Intent intent = new Intent(getApplicationContext(),mainpage.class);

                                                startActivity(intent);
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



    private void getsorgu() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("dinleyicibekleyen").document(getIntent().getStringExtra("idsis").toString());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData().get("durum").toString());

                    if(anan == 1) {
                        Intent intent = new Intent(getApplicationContext(),sohbetpage.class);
                        intent.putExtra("nerden", "2");
                        intent.putExtra("dirdir", snapshot.getData().get("gelen").toString());
                        intent.putExtra("ollan", snapshot.getId());
                        intent.putExtra("idsi", getIntent().getStringExtra("idsis"));

                        startActivity(intent);
                    }
                    anan = 1;

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });










    }




}