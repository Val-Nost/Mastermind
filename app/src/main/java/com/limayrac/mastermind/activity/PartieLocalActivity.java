package com.limayrac.mastermind.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.adapter.ListeReponseAdapter;
import com.limayrac.mastermind.dao.CodeCouleurDao;
import com.limayrac.mastermind.dao.ScoreDao;
import com.limayrac.mastermind.model.CodeCouleur;
import com.limayrac.mastermind.model.CodeReponse;
import com.limayrac.mastermind.model.NiveauDifficulte;
import com.limayrac.mastermind.model.Score;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartieLocalActivity extends Activity implements View.OnClickListener {
    private TableRow saisieUtilisateur;
    private TextView titrePage;
    private int saisie_courante = 0;
    private CodeCouleur original;
    private List<CodeReponse> reponses;
    private ListeReponseAdapter adapter;
    private NiveauDifficulte niveauDifficulte;
    private ListView listeReponses;
    private CodeCouleurDao codeCouleurDao;
    private ScoreDao scoreDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partie_local_activity);

        // DAO
        codeCouleurDao = new CodeCouleurDao(this);
        codeCouleurDao.open();
        scoreDao = new ScoreDao(this);
        scoreDao.open();

        Intent intent = getIntent();
        niveauDifficulte = intent.getParcelableExtra("NIVEAU");

        // Titre de la page
        titrePage = findViewById(R.id.title_local);
        titrePage.setText(getString(R.string.local_title, "Joueur 1"));

        // Bouton de saisie des couleurs
        initBtnSaisie(niveauDifficulte);


        // Bouton retour
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        // Bouton valider
        Button validateButton = findViewById(R.id.validate_button);
        validateButton.setOnClickListener(this);

        saisieUtilisateur = findViewById(R.id.saisie_utilisateur);
        for (int i = 0; i < niveauDifficulte.getCouleurSaisie(); i++) {
            Button button = new Button(this);
            button.setId(i);
            button.setOnClickListener(this);
            button.setClickable(false);
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));

            saisieUtilisateur.addView(button);
        }

        reponses = new ArrayList<>();
        listeReponses = findViewById(R.id.list_reponse);
        adapter = new ListeReponseAdapter(this, reponses);
        listeReponses.setAdapter(adapter);

        if (reponses == null) {
            reponses = new ArrayList<>();
        }
        adapter = new ListeReponseAdapter(this, reponses);
        listeReponses.setAdapter(adapter);

        showDialogChoixCouleur();
    }

    private void initBtnSaisie(NiveauDifficulte niveauDifficulte) {
        Button yellowButton = findViewById(R.id.yellow_button);
        yellowButton.setOnClickListener(this);
        if (niveauDifficulte.getCouleurPropose() < 1) {
            yellowButton.setVisibility(View.INVISIBLE);
        }
        Button blueButton = findViewById(R.id.blue_button);
        blueButton.setOnClickListener(this);
        if (niveauDifficulte.getCouleurPropose() < 2) {
            blueButton.setVisibility(View.INVISIBLE);
        }
        Button redButton = findViewById(R.id.red_button);
        redButton.setOnClickListener(this);
        if (niveauDifficulte.getCouleurPropose() < 3) {
            redButton.setVisibility(View.INVISIBLE);
        }
        Button greenButton = findViewById(R.id.green_button);
        greenButton.setOnClickListener(this);
        if (niveauDifficulte.getCouleurPropose() < 4) {
            greenButton.setVisibility(View.INVISIBLE);
        }
        Button whiteButton = findViewById(R.id.white_button);
        whiteButton.setOnClickListener(this);
        if (niveauDifficulte.getCouleurPropose() < 5) {
            whiteButton.setVisibility(View.INVISIBLE);
        }
        Button blackButton = findViewById(R.id.black_button);
        blackButton.setOnClickListener(this);
        if (niveauDifficulte.getCouleurPropose() < 6) {
            blackButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.validate_button) {
            if (saisie_courante == niveauDifficulte.getCouleurSaisie()) {
                if (original == null) {
                    original = new CodeCouleur(saisieUtilisateur);
                    original.updateNbCouleur();

                    // On efface la saisie de l'utilisateur
                    saisie_courante = 0;
                    for (int i = 0; i < saisieUtilisateur.getChildCount(); i++) {
                        saisieUtilisateur.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    }

                    // Passage Joueur 2 pour qu'il essaie de deviner
                    // On montre un dialog pour informer le joueur
                    showDialogCodeSecret();
                    titrePage.setText(getString(R.string.local_title, "Joueur 2"));

                } else {
                    CodeCouleur codeCouleur = new CodeCouleur(saisieUtilisateur);
                    codeCouleur.updateNbCouleur();

                    // On efface la saisie de l'utilisateur
                    saisie_courante = 0;
                    for (int i = 0; i < saisieUtilisateur.getChildCount(); i++) {
                        saisieUtilisateur.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    }

                    showDialogPasssageTelephone(codeCouleur);
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.not_enough_color, niveauDifficulte.getCouleurSaisie()), Toast.LENGTH_LONG).show();
            }
        }
        // On ferme l'activité
        if (view.getId() == R.id.back_button) {
            showDialogBack();
        }

        // Bouton de saisie des couleurs
        if (view.getId() == R.id.yellow_button || view.getId() == R.id.blue_button
                || view.getId() == R.id.red_button || view.getId() == R.id.green_button
                || view.getId() == R.id.white_button || view.getId() == R.id.black_button) {
            if (saisie_courante < niveauDifficulte.getCouleurSaisie()) {
                saisieUtilisateur.getChildAt(saisie_courante).setBackgroundTintList(view.getBackgroundTintList());
                setNotClickableExcept(saisie_courante);
                saisie_courante++;
            } else {
                Toast.makeText(this, getResources().getString(R.string.enough_color), Toast.LENGTH_LONG).show();
            }
        }

        if (view.getId() == (saisie_courante-1)) {
            saisie_courante--;
            setColorByIndex(R.color.grey, saisie_courante);
            if (saisie_courante == 0) {
                // On les rends tous incliquable
                setNotClickableExcept(-1);
            } else {
                setNotClickableExcept(saisie_courante-1);
            }
        }
    }
    private void showDialogCodeSecret() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.yu_gi_oh);
        builder.setMessage(getString(R.string.message_deviner));

        builder.setPositiveButton(R.string.ok, null);

        builder.show();
    }

    private void showDialogChoixCouleur() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.choix_title);
        builder.setMessage(getString(R.string.choix_content));

        builder.setPositiveButton(R.string.ok, null);

        builder.show();
    }

    private void showDialogPasssageTelephone(CodeCouleur codeCouleur) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.attention);
        builder.setMessage(getString(R.string.passage_content));

        builder.setPositiveButton(R.string.valider, (dialog, which) -> {
            Intent intent = new Intent(this, ReponseActivity.class);
            intent.putExtra("SECRET", original);
            intent.putExtra("SAISIE", codeCouleur);
            startActivityForResult(intent, 400);
        });
        builder.setCancelable(false);
        builder.show();
    }
    public void showDialogWin(int nbTentative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.win_content, nbTentative));

        builder.setTitle(R.string.win_title);

        builder.setPositiveButton(R.string.valider, (dialog, which) -> {
            finish();
        });

        builder.setCancelable(false);
        builder.show();
    }
    public void showDialogLose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.lose_local_content));

        builder.setTitle(R.string.lose_title);

        builder.setPositiveButton(R.string.valider, (dialog, which) -> {
            finish();
        });

        builder.setCancelable(false);
        builder.show();
    }
    public void showDialogBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.verification_content));

        builder.setTitle(R.string.verification);

        builder.setPositiveButton(R.string.valider, (dialog, which) -> {
            finish();
        });

        builder.setNegativeButton(R.string.annuler, null);

        builder.show();
    }
    public void setColorByIndex(int color, int index) {
        if (index < saisieUtilisateur.getChildCount()) {
            saisieUtilisateur.getChildAt(index).setBackgroundTintList(ContextCompat.getColorStateList(this, color));
        }
    }
    public void setNotClickableExcept(int index) {
        for (int i = 0; i < saisieUtilisateur.getChildCount(); i++) {
            saisieUtilisateur.getChildAt(i).setClickable(i == index);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 400) {
            if(resultCode == Activity.RESULT_OK){
                CodeReponse codeReponse = data.getParcelableExtra("CODE_REPONSE");
                reponses.add(codeReponse);
                adapter = new ListeReponseAdapter(this, reponses);
                listeReponses.setAdapter(adapter);
                if (codeReponse.isOk()) {
                    CodeCouleur toSave = codeCouleurDao.save(codeReponse.getOriginal());
                    Score score = new Score();
                    score.setDate(LocalDate.now());
                    score.setNbTentative(adapter.getCount());
                    score.setCodeCouleur(toSave);
                    scoreDao.save(score);
                    showDialogWin(reponses.size());
                }
                if (reponses.size() == niveauDifficulte.getNombreEssai()) {
                    CodeCouleur toSave = codeCouleurDao.save(codeReponse.getOriginal());
                    Score score = new Score();
                    score.setDate(LocalDate.now());
                    score.setNbTentative(adapter.getCount());
                    score.setCodeCouleur(toSave);
                    scoreDao.save(score);
                    showDialogLose();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}
