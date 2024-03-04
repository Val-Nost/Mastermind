package com.limayrac.mastermind.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.limayrac.mastermind.bdd.DBHelper;
import com.limayrac.mastermind.model.NiveauDifficulte;

import java.util.ArrayList;
import java.util.List;

public class NiveauDao {
    private final DBHelper bdHelper;
    private SQLiteDatabase sqLiteDatabase;
    //////////////////////////////////////////////////////////////
    ///////////////////////// Attributs///////////////////////////
    //////////////////////////////////////////////////////////////
    public final static int NUM_COL_ID = 0;
    public final static int NUM_COL_LIBELLE = 1;
    public final static int NUM_COL_NB_ESSAI = 2;
    public final static int NUM_COL_NB_COULEUR = 3;
    //////////////////////////////////////////////////////////////
    ///////////////////////// REQUETES////////////////////////////
    //////////////////////////////////////////////////////////////
    private final static String FIND_ALL = "SELECT * FROM " + DBHelper.NIVEAU_TABLE;
    public NiveauDao(Context context) {
        bdHelper = new DBHelper(
                context,
                DBHelper.DATABASE_NAME,
                null,
                DBHelper.VERSION
        );
    }
    public void open() {
        sqLiteDatabase = bdHelper.getWritableDatabase();
    }
    public void close() {
        sqLiteDatabase.close();
        bdHelper.close();
    }
    public Cursor cursorFindAll() {
        return sqLiteDatabase.rawQuery(FIND_ALL, null);
    }
    public List<NiveauDifficulte> findAll() {
        return cursorToObjectList(cursorFindAll());
    }
    public void save(NiveauDifficulte toSave) {
        ContentValues enregistrement = objectToContentValues(toSave);
        long id = sqLiteDatabase.insert(DBHelper.NIVEAU_TABLE, null, enregistrement);
    }
    public NiveauDifficulte cursorToObject(Cursor cursor) {
        NiveauDifficulte result;

        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }

        // Si le curseur ne contient qu'un seul enregistrement, on l'initialise à cet enregistrement,
        // S'il en contient plus d'un, cela crée une boucle infinie avec la méthode CursorToObjectList
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
        }

        result = new NiveauDifficulte();
        result.setId(cursor.getInt(NUM_COL_ID));
        result.setLibelle(cursor.getString(NUM_COL_LIBELLE));
        result.setCouleurPropose(cursor.getInt(NUM_COL_NB_COULEUR));
        result.setNombreEssai(cursor.getInt(NUM_COL_NB_ESSAI));

        // On ferme le curseur s'il ne contient qu'un seul enregistrement,
        // Sinon on ne peut pas accéder aux autres avec la méthode CursorToObjectList
        if (cursor.getCount() == 1) {
            cursor.close();
        }

        return result;
    }
    public List<NiveauDifficulte> cursorToObjectList(Cursor cursor) {
        List<NiveauDifficulte> niveauDifficultes = new ArrayList<>();
        for ( cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext()) {
            NiveauDifficulte niveauDifficulte = cursorToObject(cursor);
            niveauDifficultes.add(niveauDifficulte);
        }
        // On ferme le curseur après la conversion
        cursor.close();
        return niveauDifficultes;
    }

    public ContentValues objectToContentValues(NiveauDifficulte toConvert) {
        ContentValues enregistrement = new ContentValues();

        enregistrement.put(DBHelper.NIVEAU_ID, toConvert.getId());
        enregistrement.put(DBHelper.NIVEAU_LIBELLE, toConvert.getLibelle());
        enregistrement.put(DBHelper.NIVEAU_NOMBRE_ESSAI, toConvert.getNombreEssai());
        enregistrement.put(DBHelper.NIVEAU_NOMBRE_COULEUR, toConvert.getCouleurPropose());

        return enregistrement;
    }
}
