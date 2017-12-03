package com.idev.idevchat.Fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idev.idevchat.Model.Question;
import com.idev.idevchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputSoal extends Fragment implements View.OnClickListener {
    private EditText edSoal, edpil1, edpil2, edpil3, edpil4, edjawaban;
    private Button tambah;
    private FirebaseDatabase database;

    public InputSoal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_input_soal, container, false);

        edSoal = v.findViewById(R.id.edSoal);
        edpil1 = v.findViewById(R.id.pil1);
        edpil2 = v.findViewById(R.id.pil2);
        edpil3 = v.findViewById(R.id.pil3);
        edpil4 = v.findViewById(R.id.pil4);
        edjawaban = v.findViewById(R.id.jawaban1);
        tambah = v.findViewById(R.id.btnAdd);

        database = FirebaseDatabase.getInstance();

        tambah.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == tambah) {
            String soal = edSoal.getText().toString();
            String pilihan1 = edpil1.getText().toString();
            String pilihan2 = edpil2.getText().toString();
            String pilihan3 = edpil3.getText().toString();
            String pilihan4 = edpil4.getText().toString();
            String jawaban = edjawaban.getText().toString();

            if (!soal.equals("") && !pilihan1.equals("") && !pilihan2.equals("") && !pilihan3.equals("")
                    && !pilihan4.equals("") && !jawaban.equals("")) {
                int answer = Integer.parseInt(jawaban);
                DatabaseReference myRef = database.getReference("soal");
                Question question = new Question(answer, soal);
                question.addPilihan(pilihan1);
                question.addPilihan(pilihan2);
                question.addPilihan(pilihan3);
                question.addPilihan(pilihan4);
                myRef.push().setValue(question);
                Toast.makeText(InputSoal.this.getContext(), "Soal Berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InputSoal.this.getContext(), "Data Belum Lengkap !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
