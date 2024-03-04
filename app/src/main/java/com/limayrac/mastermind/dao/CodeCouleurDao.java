package com.limayrac.mastermind.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;

import com.limayrac.mastermind.bdd.DBHelper;
import com.limayrac.mastermind.model.CodeCouleur;

import java.util.ArrayList;
import java.util.List;

public class CodeCouleurDao {
    private final DBHelper bdHelper;
    private SQLiteDatabase sqLiteDatabase;

    //////////////////////////////////////////////////////////////
    ///////////////////////// Attributs///////////////////////////
    //////////////////////////////////////////////////////////////
    public final static int NUM_COL_ID = 0;
    public final static int NUM_COL_COULEUR_1 = 1;
    public final static int NUM_COL_COULEUR_2 = 2;
    public final static int NUM_COL_COULEUR_3 = 3;
    public final static int NUM_COL_COULEUR_4 = 4;
    //////////////////////////////////////////////////////////////
    ///////////////////////// REQUETES////////////////////////////
    //////////////////////////////////////////////////////////////
    private final static String FIND_ALL = "SELECT * FROM " + DBHelper.CODE_COULEUR_TABLE;
    private final static String FIND_BY_ID = "SELECT * FROM " + DBHelper.CODE_COULEUR_TABLE + " WHERE " + DBHelper.CODE_COULEUR_ID + " = ?";

    public CodeCouleurDao(Context context) {
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
    public Cursor cursorFindById(Integer id) {
        return sqLiteDatabase.rawQuery(FIND_BY_ID,
                new String[]{String.valueOf(id)});
    }
    public CodeCouleur findById(Integer aLong) {
        return cursorToObject(cursorFindById(aLong));
    }

    public Cursor cursorFindAll() {
        return sqLiteDatabase.rawQuery(FIND_ALL, null);
    }
    public List<CodeCouleur> findAll() {
        return cursorToObjectList(cursorFindAll());
    }
    public CodeCouleur save(CodeCouleur toSave) {
        ContentValues enregistrement = objectToContentValues(toSave);
        int id = (int) sqLiteDatabase.insert(DBHelper.CODE_COULEUR_TABLE, null, enregistrement);
        return findById(id);
    }
    public CodeCouleur cursorToObject(Cursor cursor) {
        CodeCouleur result;

        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }

        // Si le curseur ne contient qu'un seul enregistrement, on l'initialise à cet enregistrement,
        // S'il en contient plus d'un, cela crée une boucle infinie avec la méthode CursorToObjectList
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
        }

        result = new CodeCouleur();
        result.setId(cursor.getInt(NUM_COL_ID));
        List<Integer> couleurs = new ArrayList<>();
        couleurs.add(cursor.getInt(NUM_COL_COULEUR_1));
        couleurs.add(cursor.getInt(NUM_COL_COULEUR_2));
        couleurs.add(cursor.getInt(NUM_COL_COULEUR_3));
        couleurs.add(cursor.getInt(NUM_COL_COULEUR_4));
        result.setCouleursInt(couleurs);

        List<Color> colors = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            colors.add(Color.valueOf(cursor.getInt(NUM_COL_COULEUR_1)));
            colors.add(Color.valueOf(cursor.getInt(NUM_COL_COULEUR_2)));
            colors.add(Color.valueOf(cursor.getInt(NUM_COL_COULEUR_3)));
            colors.add(Color.valueOf(cursor.getInt(NUM_COL_COULEUR_4)));
        }
        result.setColors(colors);

        // On ferme le curseur s'il ne contient qu'un seul enregistrement,
        // Sinon on ne peut pas accéder aux autres avec la méthode CursorToObjectList
        if (cursor.getCount() == 1) {
            cursor.close();
        }

        return result;
    }
    public List<CodeCouleur> cursorToObjectList(Cursor cursor) {
        List<CodeCouleur> codeCouleurs = new ArrayList<>();
        for ( cursor.moveToFirst() ; !cursor.isAfterLast() ; cursor.moveToNext()) {
            CodeCouleur codeCouleur = cursorToObject(cursor);
            codeCouleurs.add(codeCouleur);
        }
        // On ferme le curseur après la conversion
        cursor.close();
        return codeCouleurs;
    }

    public ContentValues objectToContentValues(CodeCouleur toConvert) {
        ContentValues enregistrement = new ContentValues();

        enregistrement.put(DBHelper.CODE_COULEUR_ID, toConvert.getId());
        enregistrement.put(DBHelper.CODE_COULEUR_COULEUR1, toConvert.getCouleursInt().get(0));
        enregistrement.put(DBHelper.CODE_COULEUR_COULEUR2, toConvert.getCouleursInt().get(1));
        enregistrement.put(DBHelper.CODE_COULEUR_COULEUR3, toConvert.getCouleursInt().get(2));
        enregistrement.put(DBHelper.CODE_COULEUR_COULEUR4, toConvert.getCouleursInt().get(3));

        return enregistrement;
    }
}
