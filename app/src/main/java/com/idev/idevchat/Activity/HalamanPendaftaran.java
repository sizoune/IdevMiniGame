package com.idev.idevchat.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.idev.idevchat.R;


public class HalamanPendaftaran extends AppCompatActivity implements View.OnClickListener {

    private EditText edEmail, edPassword;
    private Button daftar;
    private TextView login;

    private FirebaseAuth mAuth;
    private static final String TAG = "Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_pendaftaran);
        mAuth = FirebaseAuth.getInstance();
        initComponent();
    }

    private void initComponent() {
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        daftar = (Button) findViewById(R.id.btnDaftar);
        login = (TextView) findViewById(R.id.txtLogin);
        login.setOnClickListener(this);
        daftar.setOnClickListener(this);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = edEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            edEmail.setError("Required.");
            valid = false;
        } else {
            edEmail.setError(null);
        }

        String password = edPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edPassword.setError("Required.");
            valid = false;
        } else {
            edPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        if (v == daftar) {
            if (validateForm()) {
                final String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(HalamanPendaftaran.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else if (v == login) {
            startActivity(new Intent(getApplicationContext(), HalamanLogin.class));
            finish();
        }
    }

}
