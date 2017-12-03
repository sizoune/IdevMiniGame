package com.idev.idevchat.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idev.idevchat.Adapter.AdapterSoal;
import com.idev.idevchat.Model.Question;
import com.idev.idevchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LihatSoal extends Fragment {
    private RecyclerView listSoal;
    private AdapterSoal adapter;
    private FirebaseDatabase database;

    public LihatSoal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lihat_soal, container, false);

        listSoal = v.findViewById(R.id.listSoal);
        adapter = new AdapterSoal(LihatSoal.this.getContext());
        listSoal.setLayoutManager(new LinearLayoutManager(LihatSoal.this.getContext()));
        listSoal.setAdapter(adapter);
        cekSoal();
        return v;
    }

    private void cekSoal() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("soal");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question question = dataSnapshot.getValue(Question.class);
                adapter.addQuestion(question);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
