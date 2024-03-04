package com.limayrac.mastermind.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.dao.NiveauDao;
import com.limayrac.mastermind.model.NiveauDifficulte;

public class NouveauNiveauActivity extends Activity implements View.OnClickListener {
    private final static int TENTATIVE_MIN = 0;
    private final static int POSSIBILITE_MIN = 2;
    private final static int POSSIBILITE_MAX = 6;
    private NiveauDao niveauDao;
    private EditText libelleView;
    private EditText tentativeView;
    private EditText possibiliteeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_niveau_activity);

        // DAO
        niveauDao = new NiveauDao(this);
        niveauDao.open();

        libelleView = findViewById(R.id.niveau_libelle);
        tentativeView = findViewById(R.id.saisie_tentative);
        tentativeView.setText("0");
        possibiliteeView = findViewById(R.id.saisie_possibilitee);
        possibiliteeView.setText("4");

        Button retour = findViewById(R.id.niveau_retour);
        retour.setOnClickListener(this);

        Button valider = findViewById(R.id.niveau_valider);
        valider.setOnClickListener(this);

        Button tentativeMoins = findViewById(R.id.tentative_moins);
        tentativeMoins.setOnClickListener(this);

        Button tentativePlus = findViewById(R.id.tentative_plus);
        tentativePlus.setOnClickListener(this);

        Button possibMoins = findViewById(R.id.possibilitee_moins);
        possibMoins.setOnClickListener(this);

        Button possibPlus = findViewById(R.id.possibilitee_plus);
        possibPlus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.niveau_retour) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (view.getId() == R.id.tentative_moins) {
            int valeurActuelle = Integer.parseInt(tentativeView.getText().toString());
            if (valeurActuelle > TENTATIVE_MIN) {
                tentativeView.setText(String.valueOf(valeurActuelle-1));
            } else {
                Toast.makeText(this, getString(R.string.tentative_min, TENTATIVE_MIN), Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == R.id.tentative_plus) {
            int valeurActuelle = Integer.parseInt(tentativeView.getText().toString());
            tentativeView.setText(String.valueOf(valeurActuelle+1));
        }
        if (view.getId() == R.id.possibilitee_moins) {
            int valeurActuelle = Integer.parseInt(possibiliteeView.getText().toString());
            if (valeurActuelle > POSSIBILITE_MIN) {
                possibiliteeView.setText(String.valueOf(valeurActuelle-1));
            } else {
                Toast.makeText(this, getString(R.string.possibilitee_min, POSSIBILITE_MIN), Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == R.id.possibilitee_plus) {
            int valeurActuelle = Integer.parseInt(possibiliteeView.getText().toString());
            if (valeurActuelle < POSSIBILITE_MAX) {
                possibiliteeView.setText(String.valueOf(valeurActuelle+1));
            } else {
                Toast.makeText(this, getString(R.string.possibilitee_max, POSSIBILITE_MAX), Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == R.id.niveau_valider) {
            if (isAllOk()) {
                NiveauDifficulte niveauDifficulte = new NiveauDifficulte();
                niveauDifficulte.setLibelle(libelleView.getText().toString());
                niveauDifficulte.setNombreEssai(Integer.parseInt(tentativeView.getText().toString()));
                niveauDifficulte.setCouleurPropose(Integer.parseInt(possibiliteeView.getText().toString()));
                niveauDao.save(niveauDifficulte);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, R.string.libelle_empty, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isAllOk() {
        return libelleView.getText() != null && !libelleView.getText().toString().equals("")
                // Pas de restrictions pour les tentatives
                && tentativeView.getText() != null
                // On veut que ce chiffre soit compris entre 2 (au minimum) et 6 (au max)
                && possibiliteeView.getText() != null && Integer.parseInt(possibiliteeView.getText().toString()) >= 2 && Integer.parseInt(possibiliteeView.getText().toString()) <= 6;
    }
}
