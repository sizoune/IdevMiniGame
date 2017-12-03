package com.idev.idevchat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idev.idevchat.R;

import java.util.HashMap;


public class HalamanLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText edEmail, edPassword;
    private Button login;
    private TextView daftar;

    private FirebaseAuth mAuth;

    private static final String TAG = "DataBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);
        mAuth = FirebaseAuth.getInstance();
        initComponent();
    }

    private void initComponent() {
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        login = (Button) findViewById(R.id.btnLogin);
        daftar = (TextView) findViewById(R.id.txtDaftar);
        login.setOnClickListener(this);
        daftar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == login) {
            if (validateForm()) {
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user.getEmail().equals("wildan.keren1@gmail.com")) {
                                    startActivity(new Intent(getApplicationContext(), HalamanAdmin.class));
                                } else {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(HalamanLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else if (v == daftar) {
            startActivity(new Intent(getApplicationContext(), HalamanPendaftaran.class));
        }
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


}
