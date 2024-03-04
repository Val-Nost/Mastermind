package com.limayrac.mastermind.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NiveauDifficulte implements Parcelable {
    private Integer id;
    private String libelle;
    private int couleurPropose;
    private int couleurSaisie;
    private Integer nombreEssai;

    public NiveauDifficulte() {
        this.couleurSaisie = 4; // Défaut, à voir pour changer plus tard
    }

    public NiveauDifficulte(String libelle, int couleurPropose, int couleurSaisie, Integer nombreEssai) {
        this.libelle = libelle;
        this.couleurPropose = couleurPropose;
        this.couleurSaisie = couleurSaisie; // Défaut, à voir pour changer plus tard
        this.nombreEssai = nombreEssai;
    }

    protected NiveauDifficulte(Parcel in) {
        libelle = in.readString();
        couleurPropose = in.readInt();
        couleurSaisie = in.readInt();
        if (in.readByte() == 0) {
            nombreEssai = null;
        } else {
            nombreEssai = in.readInt();
        }
    }

    public static final Creator<NiveauDifficulte> CREATOR = new Creator<NiveauDifficulte>() {
        @Override
        public NiveauDifficulte createFromParcel(Parcel in) {
            NiveauDifficulte niveauDifficulte = new NiveauDifficulte();
            niveauDifficulte.setId(in.readInt());
            niveauDifficulte.setLibelle(in.readString());
            niveauDifficulte.setCouleurPropose(in.readInt());
            niveauDifficulte.setCouleurSaisie(in.readInt());
            niveauDifficulte.setNombreEssai(in.readInt());

            return niveauDifficulte;
        }

        @Override
        public NiveauDifficulte[] newArray(int size) {
            return new NiveauDifficulte[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCouleurPropose() {
        return couleurPropose;
    }

    public void setCouleurPropose(int couleurPropose) {
        this.couleurPropose = couleurPropose;
    }

    public int getCouleurSaisie() {
        return couleurSaisie;
    }

    public void setCouleurSaisie(int couleurSaisie) {
        this.couleurSaisie = couleurSaisie;
    }

    public int getNombreEssai() {
        return nombreEssai;
    }

    public void setNombreEssai(int nombreEssai) {
        this.nombreEssai = nombreEssai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(libelle);
        parcel.writeInt(couleurPropose);
        parcel.writeInt(couleurSaisie);
        if (nombreEssai != null) {
            parcel.writeInt(nombreEssai);
        }
    }
}
