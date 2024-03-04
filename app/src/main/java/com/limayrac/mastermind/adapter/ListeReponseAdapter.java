package com.limayrac.mastermind.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.model.CodeReponse;
import com.limayrac.mastermind.model.TypeReponse;

import java.util.List;

public class ListeReponseAdapter extends ArrayAdapter<CodeReponse> {
    public ListeReponseAdapter(@NonNull Context context, List<CodeReponse> codeReponses) {
        super(context, 0, codeReponses);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        CodeReponse codeReponse = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

//        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_reponse, parent, false);
//        }

        LinearLayout codeCouleurLayout = convertView.findViewById(R.id.code_couleur);
        // On crée des pastilles pour le code couleurs
        for (int codeCouleur : codeReponse.getCompare().getCouleursInt()) {
            Button button = new Button(getContext());
            button.setClickable(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
            layoutParams.setMargins(10, 0, 10, 0);
            button.setLayoutParams(layoutParams);
            button.setBackgroundColor(codeCouleur);

            codeCouleurLayout.addView(button);
        }

        LinearLayout codeReponseLayout = convertView.findViewById(R.id.code_reponse);
        // On crée des pastilles pour le code reponses
        for (TypeReponse typeReponse : codeReponse.getTypeReponses()) {
            Button button = new Button(getContext());
            button.setClickable(false);
            button.setLayoutParams(new LinearLayout.LayoutParams(150, 150));

            switch (typeReponse) {
                case Croix:
                    button.setBackground(getContext().getResources().getDrawable(R.drawable.traverser));
                    break;
                case Rond:
                    button.setBackground(getContext().getResources().getDrawable(R.drawable.vide));
                    break;
                case Triangle:
                    button.setBackground(getContext().getResources().getDrawable(R.drawable.triangle));
                    break;
            }
            codeReponseLayout.addView(button);
        }

        // Return the completed view to render on screen
        return convertView;

    }
}
