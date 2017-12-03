package com.idev.idevchat.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idev.idevchat.Adapter.AdapterSoal;
import com.idev.idevchat.Adapter.AdapterSoalListView;
import com.idev.idevchat.Model.Question;
import com.idev.idevchat.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private AdapterSoalListView adapter;
    private ListView listSoal;
    private ArrayList<Question> dataPertanyaan = new ArrayList<>();
    private ArrayList<Integer> pilihanUser = new ArrayList<>();
    private ArrayList<Boolean> state = new ArrayList<>();
    private static final String TAG = "Error";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        judul.setText("Mini Quiz");

        database = FirebaseDatabase.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        adapter = new AdapterSoalListView(this);
        listSoal = findViewById(R.id.listPesan);
        listSoal.setAdapter(adapter);
        listSoal.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cekData();
        fab.setOnClickListener(v -> {
            if (adapter.getAllIsChecked()) {
                new MaterialDialog.Builder(this)
                        .title("Konfirmasi Jawaban")
                        .content("Apakah Anda Yakin Dengan Jawaban Anda?")
                        .positiveText("Yakin")
                        .negativeText("Periksa Lagi")
                        .onPositive((dialog, which) -> goCheck())
                        .onNegative((dialog, which) -> dialog.dismiss())
                        .show();

            } else {
                Toast.makeText(this, "Pertanyaan belum terjawab semua!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goCheck() {
        int benar = 0;
        int salah = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (dataPertanyaan.get(i).getJawaban() == adapter.getPilihanUser().get(i)) {
                benar++;
            } else {
                salah++;
            }
        }
        showDialogResult(salah, benar);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, HalamanLogin.class));
            finish();
        } else if (currentUser.getEmail().equals("wildan.keren1@gmail.com")) {
            startActivity(new Intent(this, HalamanAdmin.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logoutfromChat();

        } else if (id == R.id.action_refresh) {
            cekData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogResult(int salah, int benar) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setCancelable(true);
        if (salah == 0) {
            dialog.setContentView(R.layout.dialog_benarsemua);
        } else {
            dialog.setContentView(R.layout.dialog_adasalah);
            ((TextView) dialog.findViewById(R.id.title)).setText("Jawaban Benar : " + Integer.toString(benar));
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cekData();
//                onOptionsItemSelected(R.menu.menu_main);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
        if (salah == 0) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clapping_sound_effect);
            mp.start();
        } else {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.fail);
            mp.start();
        }
    }

    private void cekData() {
        pilihanUser.clear();
        dataPertanyaan.clear();
        state.clear();
        adapter.clearData();
        DatabaseReference myRef = database.getReference("soal");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Question question = dataSnapshot1.getValue(Question.class);
                    dataPertanyaan.add(question);
                }
                Collections.shuffle(dataPertanyaan);
                for (int i = 0; i < 10; i++) {
                    adapter.addQuestion(dataPertanyaan.get(i));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void logoutfromChat() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, HalamanLogin.class));
        finish();
    }


}
