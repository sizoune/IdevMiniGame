package com.idev.idevchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.idev.idevchat.Model.Question;
import com.idev.idevchat.R;

import java.util.ArrayList;

/**
 * Created by mwildani on 01/12/2017.
 */

public class AdapterSoalListView extends BaseAdapter {
    private ArrayList<Question> dataPertanyaan = new ArrayList<>();
    private ArrayList<Integer> pilihanUser = new ArrayList<>();
    private ArrayList<Boolean> allIsChecked = new ArrayList<>();
    private Context context;
    private ArrayList<Integer> pilihanstate = new ArrayList<>();

    public AdapterSoalListView(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataPertanyaan.size();
    }

    @Override
    public Question getItem(int position) {
        return dataPertanyaan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SoalHolder holder = null;
        View itemView = convertView;

        if (itemView == null) {
            itemView = inflater.inflate(R.layout.list_row_soal, parent, false);
            holder = new SoalHolder();

            holder.soal = itemView.findViewById(R.id.soal);
            holder.pil1 = itemView.findViewById(R.id.pilihan1);
            holder.pil2 = itemView.findViewById(R.id.pilihan2);
            holder.pil3 = itemView.findViewById(R.id.pilihan3);
            holder.pil4 = itemView.findViewById(R.id.pilihan4);
            holder.jawaban = itemView.findViewById(R.id.jawaban);
            holder.jawaban.setOnCheckedChangeListener((group, checkedId) -> {
                int pos = (Integer) group.getTag();
                allIsChecked.set(pos, true);
                pilihanstate.set(pos, group.getCheckedRadioButtonId());
                switch (checkedId) {
                    case R.id.pilihan1:
                        pilihanUser.set(pos, 1);
                        break;
                    case R.id.pilihan2:
                        pilihanUser.set(pos, 2);
                        break;
                    case R.id.pilihan3:
                        pilihanUser.set(pos, 3);
                        break;
                    case R.id.pilihan4:
                        pilihanUser.set(pos, 4);
                        break;
                    default:
                        break;
                }
            });
            itemView.setTag(holder);
        } else {
            holder = (SoalHolder) itemView.getTag();
        }

        Question question = dataPertanyaan.get(position);
        if (!question.getSoal().contains("?")) {
            holder.soal.setText(question.getSoal() + " ?");
        } else {
            holder.soal.setText(question.getSoal());
        }
        holder.pil1.setText(question.getPilihan().get(0));
        holder.pil2.setText(question.getPilihan().get(1));
        holder.pil3.setText(question.getPilihan().get(2));
        holder.pil4.setText(question.getPilihan().get(3));
        holder.jawaban.setTag(position);
        if (pilihanstate.get(position) != -1) {
            holder.jawaban.check(pilihanstate.get(position));
            allIsChecked.set(position, true);
        } else {
            allIsChecked.set(position, false);
            holder.jawaban.clearCheck();
        }
        return itemView;
    }

    public void clearData() {
        dataPertanyaan.clear();
        pilihanstate.clear();
        pilihanUser.clear();
        allIsChecked.clear();
        notifyDataSetChanged();
    }

    public void addQuestion(Question question) {
        dataPertanyaan.add(question);
        pilihanUser.add(-1);
        pilihanstate.add(-1);
        allIsChecked.add(false);
        notifyDataSetChanged();
    }

    static class SoalHolder {
        TextView soal;
        RadioButton pil1, pil2, pil3, pil4;
        RadioGroup jawaban;
    }

    public ArrayList<Integer> getPilihanUser() {
        return pilihanUser;
    }

    private void pesan(String pesan) {
        Toast.makeText(context, "user memilih " + pesan, Toast.LENGTH_SHORT).show();
    }

    public boolean getAllIsChecked() {
        boolean allcheck = true;
        for (boolean b : allIsChecked) {
            if (!b) {
                allcheck = false;
                break;
            }
        }
        return allcheck;
    }
}
