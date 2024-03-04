package com.limayrac.mastermind.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //////////////////////////////////////////////////////////////
    ///////////////////////// BDD ////////////////////////////////
    //////////////////////////////////////////////////////////////
    public final static String DATABASE_NAME = "mastermind.db";
    public static final int VERSION = 1;

    //////////////////////////////////////////////////////////////
    ////////////////////// NIVEAU ////////////////////////////////
    //////////////////////////////////////////////////////////////
    public final static String NIVEAU_TABLE = "niveau";
    public final static String NIVEAU_ID = "_id";
    public final static String NIVEAU_LIBELLE = "libelle";
    public final static String NIVEAU_NOMBRE_ESSAI = "nombre_essai";
    public final static String NIVEAU_NOMBRE_COULEUR = "nombre_couleur";
    private final static String NIVEAU_CREATE = "CREATE TABLE " + NIVEAU_TABLE + " (" +
            NIVEAU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            NIVEAU_LIBELLE + " VARCHAR NOT NULL, " +
            NIVEAU_NOMBRE_ESSAI + " INTEGER NOT NULL, " +
            NIVEAU_NOMBRE_COULEUR + " INTEGER NOT NULL" +
            ")";


    //////////////////////////////////////////////////////////////
    ////////////////////// Code couleur //////////////////////////
    //////////////////////////////////////////////////////////////
    public final static String CODE_COULEUR_TABLE = "code_couleur";
    public final static String CODE_COULEUR_ID = "_id";
    public final static String CODE_COULEUR_COULEUR1 = "couleur1";
    public final static String CODE_COULEUR_COULEUR2 = "couleur2";
    public final static String CODE_COULEUR_COULEUR3 = "couleur3";
    public final static String CODE_COULEUR_COULEUR4 = "couleur4";
    private final static String CODE_COULEUR_CREATE = "CREATE TABLE " + CODE_COULEUR_TABLE + " (" +
            CODE_COULEUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            CODE_COULEUR_COULEUR1 + " INTEGER NOT NULL, " +
            CODE_COULEUR_COULEUR2 + " INTEGER NOT NULL, " +
            CODE_COULEUR_COULEUR3 + " INTEGER NOT NULL," +
            CODE_COULEUR_COULEUR4 + " INTEGER NOT NULL" +
            ")";

    //////////////////////////////////////////////////////////////
    ////////////////////// SCORE/ ////////////////////////////////
    //////////////////////////////////////////////////////////////
    public final static String SCORE_TABLE = "score";
    public final static String SCORE_ID = "_id";
    public final static String SCORE_DATE = "date_partie";
    public final static String SCORE_NOMBRE_TENTATIVE = "nombre_tentative";
    public final static String SCORE_CODE_COULEUR = "code_couleur";
    private final static String SCORE_CREATE = "CREATE TABLE " + SCORE_TABLE + " (" +
            SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            SCORE_DATE + " DATE NOT NULL, " +
            SCORE_NOMBRE_TENTATIVE + " INTEGER NOT NULL, " +
            SCORE_CODE_COULEUR + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + SCORE_CODE_COULEUR +") REFERENCES " + CODE_COULEUR_TABLE +"(" + CODE_COULEUR_ID + ")" +
            ")";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NIVEAU_CREATE);
        sqLiteDatabase.execSQL(CODE_COULEUR_CREATE);
        sqLiteDatabase.execSQL(SCORE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
