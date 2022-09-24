package com.krc.mola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.krc.mola.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String mVerificationId ;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog pd;

    private  static final String TAG = "MAIN_TAG";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.kodem.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        pd.setTitle("Lütfen Bekleyin...");

        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                signWithPhone(phoneAuthCredential);
            }

            public void onVerificationFailed(@NonNull FirebaseException e) {

                pd.dismiss();

            }

            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Log.d(TAG,"onCodeSent:"+mVerificationId);

                mVerificationId = s;

                forceResendingToken = forceResendingToken;

                pd.dismiss();

                binding.telo.setVisibility(View.INVISIBLE);
                binding.kodem.setVisibility(View.VISIBLE);
                binding.button21.setVisibility(View.INVISIBLE);
                // gizleme yeri
            }
        };


        binding.button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = binding.editTextPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){

                    Toast.makeText(MainActivity.this, "Telefon Numaranızı Girin",Toast.LENGTH_SHORT).show();

                }
                else {
                    startPhoneNumberVerification(phone);
                }

            }
        });



        binding.button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = binding.editTextTextPersonName8.getText().toString().trim();
                if (TextUtils.isEmpty(code)){

                    Toast.makeText(MainActivity.this, "Doğrulama kodunuzu girin",Toast.LENGTH_SHORT).show();

                }
                else {
                    verifyPhone(mVerificationId,code);
                }

            }
        });


    }

    private void verifyPhone(String mVerificationId, String code) {
        pd.setMessage("Doğrulanıyor");
        pd.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId , code);
        signWithPhone(credential);

    }

    private void signWithPhone(PhoneAuthCredential credential) {

        pd.setMessage("Giriliyor..");

        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd.dismiss();
                String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                Intent intent = new Intent(getApplicationContext(),mainpage.class);
                intent.putExtra("oldum", "0"  );

                startActivity(intent);
                finish();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){

                pd.dismiss();
            }

        });


    }

    private void startPhoneNumberVerification(String phone) {

        pd.setMessage("Doğrulanıyor");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber("+9"+phone).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallbacks).build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}