package com.limayrac.mastermind.model;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TableRow;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CodeCouleur implements Parcelable {
    public static int jaune = Color.YELLOW;
    public static int rouge = Color.RED;
    public static int vert = Color.GREEN;
    public static int bleu = Color.BLUE;
    public static int blanc = Color.WHITE;
    public static int noir = Color.BLACK;
    private int nbCouleur;
    private static final List<Integer> defaultColors = initDefaultColor();
    private List<ColorStateList> couleurs;
    private List<Integer> couleursInt;
    private List<Color> colors;
    private Integer id;

    protected CodeCouleur(Parcel in) {
        couleurs = in.createTypedArrayList(ColorStateList.CREATOR);
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }

    public static final Creator<CodeCouleur> CREATOR = new Creator<CodeCouleur>() {
        @Override
        public CodeCouleur createFromParcel(Parcel in) {
            CodeCouleur codeCouleur = new CodeCouleur();
            codeCouleur.setNbCouleur(in.readInt());
            List<Color> colors = new ArrayList<>();
            List<Integer> couleursInt = new ArrayList<>();
            for (int i = 0; i < codeCouleur.getNbCouleur(); i++) {
                int current = in.readInt();
                colors.add(Color.valueOf(current));
                couleursInt.add(current);
            }
            codeCouleur.setColors(colors);
            codeCouleur.setCouleursInt(couleursInt);
            return codeCouleur;
        }

        @Override
        public CodeCouleur[] newArray(int size) {
            return new CodeCouleur[size];
        }
    };

    private static List<Integer> initDefaultColor() {
        List<Integer> list = new ArrayList<>();
        list.add(jaune);
        list.add(rouge);
        list.add(bleu);
        list.add(vert);
        list.add(blanc);
        list.add(noir);
        return list;
    }
    public CodeCouleur() {
        couleurs = new ArrayList<>();
        couleursInt = new ArrayList<>();
        colors = new ArrayList<>();
    }
    public CodeCouleur(TableRow tableRow) {
        couleurs = new ArrayList<>();
        couleursInt = new ArrayList<>();
        colors = new ArrayList<>();
        for (int i = 0; i < tableRow.getChildCount(); i++) {
            if (tableRow.getChildAt(i).getBackgroundTintList() != null) {
                couleurs.add(tableRow.getChildAt(i).getBackgroundTintList());
                couleursInt.add(Objects.requireNonNull(tableRow.getChildAt(i).getBackgroundTintList()).getDefaultColor());
                colors.add(Color.valueOf(Objects.requireNonNull(tableRow.getChildAt(i).getBackgroundTintList()).getDefaultColor()));
            }
        }
    }
    public static CodeCouleur generateRandom(int sizeCode, int nbColor) {
        CodeCouleur retour = new CodeCouleur();
        for (int i = 0; i < sizeCode; i++) {
            int random = (int) ((Math.random() * (nbColor-1)));;
            if (nbColor <= defaultColors.size()) {
                int codeColor = defaultColors.get(random);
                retour.getCouleursInt().add(codeColor);
                retour.getColors().add(Color.valueOf(codeColor));
            }
        }
        return retour;
    }
    public CodeReponse compare(CodeCouleur codeCouleur) {
        CodeReponse codeReponse = new CodeReponse();
        codeReponse.setOriginal(this);
        codeReponse.setCompare(codeCouleur);
        if (this.haveSameSize(codeCouleur)) {
            for (int i = 0; i < this.size(); i++) {
                if (Objects.equals(this.getColorByIndex(i), codeCouleur.getColorByIndex(i))) {
                    codeReponse.getTypeReponses().add(TypeReponse.Croix);
                } else {
                    if (codeCouleur.contains(this.getColorByIndex(i))) {
                        codeReponse.getTypeReponses().add(TypeReponse.Rond);
                    }
                }
            }
            return codeReponse;
        }
        return null;
    }
    public CodeReponse compareUnordered(CodeCouleur codeCouleur) {
        CodeReponse codeReponse = new CodeReponse();
        codeReponse.setOriginal(this);
        codeReponse.setCompare(codeCouleur);
        if (this.haveSameSize(codeCouleur)) {
            for (int i = 0; i < this.size(); i++) {
                if (Objects.equals(this.getColorByIndex(i), codeCouleur.getColorByIndex(i))) {
                    codeReponse.getTypeReponses().add(TypeReponse.Croix);
                }
            }
            for (int i = 0; i < this.size(); i++) {
                if (this.contains(codeCouleur.getColorByIndex(i))) {
                    codeReponse.getTypeReponses().add(TypeReponse.Rond);
                }
            }
            return codeReponse;
        }
        return null;
    }
    public ColorStateList getObjectByIndex(int index) {
        return couleurs.get(index);
    }
    public Integer getIntByIndex(int index) {
        return couleursInt.get(index);
    }
    public Color getColorByIndex(int index) {
        return colors.get(index);
    }
    public boolean haveSameSize(CodeCouleur codeCouleur) {
        return couleursInt.size() == codeCouleur.couleursInt.size();
    }
    public int size() {
        return couleursInt.size();
    }
    public boolean contains(Color color) {
        return colors.contains(color);
    }
    public boolean contains(Integer color) {
        return couleursInt.contains(color);
    }
    // Getters et Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public List<ColorStateList> getCouleurs() {
        return couleurs;
    }
    public void setCouleurs(List<ColorStateList> couleurs) {
        this.couleurs = couleurs;
    }
    public List<Integer> getCouleursInt() {
        return couleursInt;
    }
    public void setCouleursInt(List<Integer> couleursInt) {
        this.couleursInt = couleursInt;
    }
    public List<Color> getColors() {
        return colors;
    }
    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
    public void updateNbCouleur() {
        if (colors != null) {
            nbCouleur = colors.size();
        } else {
            if (couleursInt != null) {
                nbCouleur = couleursInt.size();
            }
        }
    }
    public int getNbCouleur() {
        return nbCouleur;
    }
    public void setNbCouleur(int nbCouleur) {
        this.nbCouleur = nbCouleur;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(nbCouleur);
        for (Integer couleur : couleursInt) {
            parcel.writeInt(couleur);
        }
    }
}
