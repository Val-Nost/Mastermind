package com.limayrac.mastermind.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.limayrac.mastermind.R;
import com.limayrac.mastermind.adapter.ListeScoreAdapter;
import com.limayrac.mastermind.dao.ScoreDao;
import com.limayrac.mastermind.model.Score;

import java.util.List;

public class ScoreActivity extends Activity implements View.OnClickListener {
    private ScoreDao scoreDao;
    private ListView listeScore;
    private ListeScoreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        // DAO
        scoreDao = new ScoreDao(this);
        scoreDao.open();

        Button retour = findViewById(R.id.score_retour);
        retour.setOnClickListener(this);

        listeScore = findViewById(R.id.list_score);
        List<Score> scores = scoreDao.findAll();
        adapter = new ListeScoreAdapter(this, scores);
        listeScore.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.score_retour) {
            finish();
        }
    }
}
