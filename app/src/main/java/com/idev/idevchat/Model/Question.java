package com.idev.idevchat.Model;

import java.util.ArrayList;

/**
 * Created by mwildani on 29/11/2017.
 */

public class Question {
    private int jawaban;
    private String soal;
    private ArrayList<String> pilihan = new ArrayList<>();

    public Question() {

    }

    public Question(int jawaban, String soal) {
        this.jawaban = jawaban;
        this.soal = soal;
    }

    public Question(String soal) {
        this.soal = soal;
    }

    public int getJawaban() {
        return jawaban;
    }

    public void setJawaban(int jawaban) {
        this.jawaban = jawaban;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public ArrayList<String> getPilihan() {
        return pilihan;
    }

    public void setPilihan(ArrayList<String> pilihan) {
        this.pilihan = pilihan;
    }

    public void addPilihan(String pil) {
        pilihan.add(pil);
    }
}
