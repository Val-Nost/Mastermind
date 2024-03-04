package com.limayrac.mastermind.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.limayrac.mastermind.bdd.DBHelper;
import com.limayrac.mastermind.model.Score;
import com.limayrac.mastermind.model.Score;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScoreDao {
    private final DBHelper bdHelper;
    private SQLiteDatabase sqLiteDatabase;
    private CodeCouleurDao codeCouleurDao;
    
    //////////////////////////////////////////////////////////////
    ///////////////////////// Attributs///////////////////////////
    //////////////////////////////////////////////////////////////
    public final static int NUM_COL_ID = 0;
    public final static int NUM_COL_DATE = 1;
    public final static int NUM_COL_NB_NOMBRE = 2;
    public final static int NUM_COL_CODE_COULEUR = 3;
    //////////////////////////////////////////////////////////////
    ///////////////////////// REQUETES////////////////////////////
    //////////////////////////////////////////////////////////////
    private final static String FIND_ALL = "SELECT * FROM " + DBHelper.SCORE_TABLE;
    public ScoreDao(Context context) {
        codeCouleurDao = new CodeCouleurDao(context);
        bdHelper = new DBHelper(
                context,
                DBHelper.DATABASE_NAME,
                null,
                DBHelper.VERSION
        );
    }

    public void open() {
        sqLiteDatabase = bdHelper.getWritableDatabase();
        codeCouleurDao.open();
    }

    public void close() {
        sqLiteDatabase.close();
        bdHelper.close();
    }
    public Cursor cursorFindAll() {
        return sqLiteDatabase.rawQuery(FIND_ALL, null);
    }
    public List<Score> findAll() {
        return cursorToObjectList(cursorFindAll());
    }
    public void save(Score toSave) {
        ContentValues enregistrement = objectToContentValues(toSave);
        long id = sqLiteDatabase.insert(DBHelper.SCORE_TABLE, null, enregistrement);
    }
    public Score cursorToObject(Cursor cursor) {
        Score result;

        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }

        // Si le curseur ne contient qu'un seul enregistrement, on l'initialise à cet enregistrement,
        // S'il en contient plus d'un, cela crée une boucle infinie avec la méthode CursorToObjectList
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
        }

        result = new Score();
        result.setId(cursor.getInt(NUM_COL_ID));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            result.setDate(LocalDate.parse(cursor.getString(NUM_COL_DATE)));
        }
        result.setNbTentative(cursor.getInt(NUM_COL_NB_NOMBRE));
        result.setCodeCouleur(codeCouleurDao.findById(cursor.getInt(NUM_COL_CODE_COULEUR)));

        // On ferme le curseur s'il ne contient qu'un seul enregistrement,
        // Sinon on ne peut pas accéder aux autres avec la méthode CursorToObjectList
        if (cursor.getCount() == 1) {
            cursor.close();
        }

        return result;
    }
    public List<Score> cursorToObjectList(Cursor cursor) {
        List<Score> scores = new ArrayList<>();
        for ( cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext()) {
            Score score = cursorToObject(cursor);
            scores.add(score);
        }
        // On ferme le curseur après la conversion
        cursor.close();
        return scores;
    }

    public ContentValues objectToContentValues(Score toConvert) {
        ContentValues enregistrement = new ContentValues();

        enregistrement.put(DBHelper.SCORE_ID, toConvert.getId());
        enregistrement.put(DBHelper.SCORE_DATE, toConvert.getDate().toString());
        enregistrement.put(DBHelper.SCORE_NOMBRE_TENTATIVE, toConvert.getNbTentative());
        enregistrement.put(DBHelper.SCORE_CODE_COULEUR, toConvert.getCodeCouleur().getId());

        return enregistrement;
    }
}
