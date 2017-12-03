package com.idev.idevchat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.idev.idevchat.Model.Question;
import com.idev.idevchat.R;

import java.util.ArrayList;

/**
 * Created by mwildani on 29/11/2017.
 */

public class AdapterSoal extends RecyclerView.Adapter<AdapterSoal.SoalViewHolder> {
    private OnCheckedChangeListener onCheckedChangeListener;
    private ArrayList<Question> dataPertanyaan = new ArrayList<>();
    private Context context;

    public AdapterSoal(Context context) {
        this.context = context;
    }

    @Override
    public SoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_soal, parent, false);
        return new SoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SoalViewHolder holder, int position) {
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

        holder.jawaban.setOnCheckedChangeListener((group, checkedId) ->
                onCheckedChangeListener.onCheckedChanged(group, checkedId, position));
    }


    @Override
    public int getItemCount() {
        return dataPertanyaan.size();
    }

    class SoalViewHolder extends RecyclerView.ViewHolder {
        public TextView soal;
        public RadioButton pil1, pil2, pil3, pil4;
        public RadioGroup jawaban;

        public SoalViewHolder(View itemView) {
            super(itemView);

            soal = itemView.findViewById(R.id.soal);
            pil1 = itemView.findViewById(R.id.pilihan1);
            pil2 = itemView.findViewById(R.id.pilihan2);
            pil3 = itemView.findViewById(R.id.pilihan3);
            pil4 = itemView.findViewById(R.id.pilihan4);
            jawaban = itemView.findViewById(R.id.jawaban);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(RadioGroup group, int checkedId, int position);
    }

    public void clearData() {
        dataPertanyaan.clear();
        notifyDataSetChanged();
    }

    public void addQuestion(Question question) {
        dataPertanyaan.add(question);
        notifyDataSetChanged();
    }
}
