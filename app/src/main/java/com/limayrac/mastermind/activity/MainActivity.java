package com.limayrac.mastermind.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.adapter.NiveauDifficulteAdapter;
import com.limayrac.mastermind.dao.NiveauDao;
import com.limayrac.mastermind.model.CodeReponse;
import com.limayrac.mastermind.model.NiveauDifficulte;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private NiveauDifficulteAdapter adapter;
    private NiveauDao niveauDao;
    private List<NiveauDifficulte> niveauDifficultes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button creerNiveau = this.findViewById(R.id.creer_niveau);
        creerNiveau.setOnClickListener(this);

        Button startGame = this.findViewById(R.id.start_game);
        startGame.setOnClickListener(this);

        Button startLocalGame = this.findViewById(R.id.start_local_game);
        startLocalGame.setOnClickListener(this);

        Button score = this.findViewById(R.id.view_score);
        score.setOnClickListener(this);

//        NiveauDifficulte basique = new NiveauDifficulte("Basique", 6, 4, 12);
//        NiveauDifficulte debutant = new NiveauDifficulte("Débutant", 4, 4, 0);
//        NiveauDifficulte difficile = new NiveauDifficulte("Difficile", 6, 4, 8);
//        NiveauDifficulte tresDifficile = new NiveauDifficulte("Très difficile", 6, 4, 6);
        niveauDao = new NiveauDao(this);
        niveauDao.open();
//        niveauDao.save(basique);
//        niveauDao.save(debutant);
//        niveauDao.save(difficile);
//        niveauDao.save(tresDifficile);
        niveauDifficultes = niveauDao.findAll();
        spinner = findViewById(R.id.spinner_niveau);
        adapter = new NiveauDifficulteAdapter(this, niveauDifficultes);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_game) {
            if (niveauDifficultes == null || niveauDifficultes.isEmpty()) {
                showDialogSpinnerVide();
            } else {
                Intent intent = new Intent(this, PartieActivity.class);
                intent.putExtra("NIVEAU", adapter.getItem(spinner.getSelectedItemPosition()));
                startActivity(intent);
            }

        }
        if (view.getId() == R.id.start_local_game) {
            if (niveauDifficultes == null || niveauDifficultes.isEmpty()) {
                showDialogSpinnerVide();
            } else {
                Intent intent = new Intent(this, PartieLocalActivity.class);
                intent.putExtra("NIVEAU", adapter.getItem(spinner.getSelectedItemPosition()));
                startActivity(intent);
            }
        }
        if (view.getId() == R.id.view_score) {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.creer_niveau) {
            Intent intent = new Intent(this, NouveauNiveauActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void showDialogSpinnerVide() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.attention);
        builder.setMessage(getString(R.string.niveau_empty));

        builder.setPositiveButton(R.string.ok, null);

        builder.show();
    }
}