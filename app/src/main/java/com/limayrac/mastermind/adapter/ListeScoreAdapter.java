package com.limayrac.mastermind.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.model.Score;

import java.util.List;

public class ListeScoreAdapter extends ArrayAdapter<Score> {
    public ListeScoreAdapter(@NonNull Context context, @NonNull List<Score> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_score, parent, false);

        Score score = getItem(position);

        Button couleur1 = convertView.findViewById(R.id.score_couleur_1);
        couleur1.setBackgroundTintList(ColorStateList.valueOf(score.getCodeCouleur().getCouleursInt().get(0)));

        Button couleur2 = convertView.findViewById(R.id.score_couleur_2);
        couleur2.setBackgroundTintList(ColorStateList.valueOf(score.getCodeCouleur().getCouleursInt().get(1)));

        Button couleur3 = convertView.findViewById(R.id.score_couleur_3);
        couleur3.setBackgroundTintList(ColorStateList.valueOf(score.getCodeCouleur().getCouleursInt().get(2)));

        Button couleur4 = convertView.findViewById(R.id.score_couleur_4);
        couleur4.setBackgroundTintList(ColorStateList.valueOf(score.getCodeCouleur().getCouleursInt().get(3)));

        TextView nbTent = convertView.findViewById(R.id.score_nb_tentative);
        nbTent.setText(String.valueOf(score.getNbTentative()));

        return convertView;
    }
}
