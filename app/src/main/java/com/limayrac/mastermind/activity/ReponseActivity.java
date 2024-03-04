package com.limayrac.mastermind.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.model.CodeCouleur;
import com.limayrac.mastermind.model.CodeReponse;
import com.limayrac.mastermind.model.TypeReponse;

import java.util.ArrayList;
import java.util.List;

public class ReponseActivity extends Activity implements View.OnClickListener {
    private TableRow saisieJoueur;
    private CodeCouleur secret;
    private CodeCouleur saisie;
    private int saisie_courante = 0;
    private List<TypeReponse> typeReponses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reponse_activity);

        saisieJoueur = findViewById(R.id.saisie_joueur);

        ImageButton croix = findViewById(R.id.croix);
        croix.setOnClickListener(this);

        ImageButton rond = findViewById(R.id.rond);
        rond.setOnClickListener(this);

        secret = getIntent().getParcelableExtra("SECRET");
        saisie = getIntent().getParcelableExtra("SAISIE");

        Button valider = findViewById(R.id.valider);
        valider.setOnClickListener(this);

        TableRow secretRow = findViewById(R.id.secret);
        for (int i = 0; i < secret.size(); i++) {
            Button button = new Button(this);
            button.setClickable(false);
            button.setBackgroundTintList(ColorStateList.valueOf(secret.getIntByIndex(i)));

            secretRow.addView(button);
        }

        TableRow saisieRow = findViewById(R.id.saisie_adversaire);
        for (int i = 0; i < saisie.size(); i++) {
            Button button = new Button(this);
            button.setClickable(false);
            button.setBackgroundTintList(ColorStateList.valueOf(saisie.getIntByIndex(i)));

            saisieRow.addView(button);
        }

        for (int i = 0; i < secret.getNbCouleur(); i++) {
            Button button = new Button(this);
            button.setId(i);
            button.setOnClickListener(this);
            button.setClickable(false);

            saisieJoueur.addView(button);
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor"})
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.valider) {
            showDialogValidation();
        }
        if (view.getId() == R.id.croix) {
            if (saisie_courante < secret.getNbCouleur()) {
                saisieJoueur.getChildAt(saisie_courante).setBackground(getDrawable(R.drawable.traverser));
                setNotClickableExcept(saisie_courante);
                typeReponses.add(TypeReponse.Croix);
                saisie_courante++;
            } else {
                Toast.makeText(this, getResources().getString(R.string.enough_color), Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == R.id.rond) {
            if (saisie_courante < secret.getNbCouleur()) {
                saisieJoueur.getChildAt(saisie_courante).setBackground(getDrawable(R.drawable.vide));
                setNotClickableExcept(saisie_courante);
                typeReponses.add(TypeReponse.Rond);
                saisie_courante++;
            } else {
                Toast.makeText(this, getResources().getString(R.string.enough_color), Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == (saisie_courante-1)) {
            saisie_courante--;
            typeReponses.remove(saisie_courante);
            ColorDrawable colorDrawable = new ColorDrawable(Color.GRAY);
            RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(R.color.grey), colorDrawable, null);
            view.setBackground(rippleDrawable);
            if (saisie_courante == 0) {
                // On les rends tous incliquable
                setNotClickableExcept(-1);
            } else {
                setNotClickableExcept(saisie_courante-1);
            }
        }
    }
    public void setNotClickableExcept(int index) {
        for (int i = 0; i < saisieJoueur.getChildCount(); i++) {
            saisieJoueur.getChildAt(i).setClickable(i == index);
        }
    }
    public void showDialogValidation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.title_validation);
        builder.setMessage(getString(R.string.content_validation));

        builder.setPositiveButton(R.string.valider, (dialog, which) -> {
            Intent returnIntent = new Intent();
            CodeReponse codeReponse = new CodeReponse();
            codeReponse.setOriginal(secret);
            codeReponse.setCompare(saisie);
            codeReponse.setTypeReponses(typeReponses);
            codeReponse.setNbElement(typeReponses.size());
            returnIntent.putExtra("CODE_REPONSE", codeReponse);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        builder.setNegativeButton(R.string.annuler, null);

        builder.show();
    }
}
