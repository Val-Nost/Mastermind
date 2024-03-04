package com.limayrac.mastermind.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.limayrac.mastermind.model.NiveauDifficulte;

import java.util.List;

public class NiveauDifficulteAdapter extends ArrayAdapter<NiveauDifficulte> {
    private List<NiveauDifficulte> niveauDifficultes;
    public NiveauDifficulteAdapter(@NonNull Context context, List<NiveauDifficulte> niveauDifficultes) {
        super(context, android.R.layout.simple_spinner_item, niveauDifficultes);
        this.niveauDifficultes = niveauDifficultes;
    }
    // Your sent context
    // Your custom values for the spinner (User)

    @Override
    public int getCount(){
        return niveauDifficultes.size();
    }

    @Override
    public NiveauDifficulte getItem(int position){
        return niveauDifficultes.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        NiveauDifficulte niveauDifficulte = getItem(position);
        textView.setText(niveauDifficulte.getLibelle());
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        NiveauDifficulte niveauDifficulte = getItem(position);
        textView.setText(niveauDifficulte.getLibelle());
        return textView;
    }
}
