package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class sohbetpage extends AppCompatActivity {
    RecyclerView recyclerView;
    List<chatjava> chatlist;
    chatjava imagearr;

    TextView isimi,sure;

    EditText mesajim;

    ImageView userim;

    ImageButton sendder,adfav;

    Button kapat;

    String oid;
    String kimbu;
    String yeniid;
    private boolean running;
    private int seconds = 0;
    LinearLayoutManager linearLayoutManager;
    Adapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sohbetpage);
        mAuth = FirebaseAuth.getInstance();

        userim = findViewById(R.id.imageView2);
        mesajim = findViewById(R.id.editTextTextPersonName5);
        sendder = findViewById(R.id.imageButton7);
        adfav = findViewById(R.id.imageButton14);
        isimi = findViewById(R.id.textView17);
        sure = findViewById(R.id.textView18);

        kapat = findViewById(R.id.button8);

        recyclerView = findViewById(R.id.chatrec);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        chatlist = new ArrayList<>();
        adapter = new Adapter(this, chatlist);
        recyclerView.setAdapter(adapter);






            kimbu = getIntent().getStringExtra("dirdir").toString();
            getprofiledates();


            getchat();

        if (getIntent().getStringExtra("nerden") == "1") {//geçmişten gelenler için
           // getchat();

        }
        ;

        if (getIntent().getStringExtra("nerden") == "2") {
          //  getchatid();

        }
        ;


        sendder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Add a new document with a generated id.
                Map<String, Object> data = new HashMap<>();
                data.put("kim", mAuth.getCurrentUser().getPhoneNumber().toString());
                data.put("mesaj", mesajim.getText().toString());
                data.put("oturum_id", getIntent().getStringExtra("idsi"));

                db.collection("in_chat")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                mesajim.setText("");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });



        adfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Add a new document with a generated id.
                Map<String, Object> data = new HashMap<>();
                data.put("eklenen", kimbu);
                data.put("ekleyen", mAuth.getCurrentUser().getPhoneNumber().toString());

                db.collection("favo")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                adfav.setColorFilter(Color.argb(255, 255, 234, 0));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });


        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mainpage.class);
                startActivity(intent);
            }
        });



    }






    public static void hideKeyboard(Activity activity, IBinder binder) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (binder != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(binder, 0);//HIDE_NOT_ALWAYS
                inputManager.showSoftInputFromInputMethod(binder, 0);
            }
        }
    }

    private  void getchat() {
        chatlist.clear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("in_chat")
                .whereEqualTo("oturum_id", getIntent().getStringExtra("idsi"))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        chatlist.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Log.d("ağağağağ", "olması gereken 31");

                                if(doc.get("kim").toString() == mAuth.getCurrentUser().getPhoneNumber()) {
                                    imagearr = new chatjava(doc.getId(), "",doc.getString("mesaj"));
                                    chatlist.add(imagearr);
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    imagearr = new chatjava(doc.getId(),doc.getString("mesaj"),"");
                                    chatlist.add(imagearr);
                                    adapter.notifyDataSetChanged();
                                }




                        }
                    }
                });









    }



    private  void getchatid() {
        Log.d("sorgu3", "getchatid");

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("chats")
                .whereEqualTo("mainid", getIntent().getStringExtra("idsi"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                yeniid = document.getId();
                                if (getIntent().getStringExtra("nerden") == "0") {//geçmişten gelenler için

                                    kimbu = getIntent().getStringExtra("dirdir").toString();
                                    getprofiledates();

                                }
                                ;
                                if (getIntent().getStringExtra("nerden") == "2") {//geçmişten gelenler için

                                    kimbu = document.get("dinleyen").toString();

                                }
                                ;

                            }
                        } else {

                        }
                    }
                });

    }


    private  void getprofiledates() {
        Log.d("sorgu4", "getprof");

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users")
                .whereEqualTo("numara", kimbu)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                    isimi.setText(document.get("isim").toString());
                                Picasso.get().load(document.get("img").toString()).into(userim);


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
                    seconds--;
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