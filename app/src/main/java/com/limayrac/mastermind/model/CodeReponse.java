package com.limayrac.mastermind.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CodeReponse implements Parcelable {
    private int nbElement;
    private CodeCouleur original;
    private CodeCouleur compare;
    List<TypeReponse> typeReponses;
    public CodeReponse() {
        typeReponses = new ArrayList<>();
    }
    protected CodeReponse(Parcel in) {
        original = in.readParcelable(CodeCouleur.class.getClassLoader());
        compare = in.readParcelable(CodeCouleur.class.getClassLoader());
//        typeReponses = new ArrayList<>();
//        in.readList(typeReponses, TypeReponse.class.getClassLoader());
        nbElement = in.readInt();
        typeReponses = new ArrayList<>();
        for (int i = 0; i < nbElement; i++) {
            TypeReponse typeReponse = TypeReponse.valueOf(in.readString());
            typeReponses.add(typeReponse);
        }
    }

    public static final Creator<CodeReponse> CREATOR = new Creator<CodeReponse>() {
        @Override
        public CodeReponse createFromParcel(Parcel in) {
            return new CodeReponse(in);
        }

        @Override
        public CodeReponse[] newArray(int size) {
            return new CodeReponse[size];
        }
    };

    public List<TypeReponse> getTypeReponses() {
        return typeReponses;
    }

    public void setTypeReponses(List<TypeReponse> typeReponses) {
        this.typeReponses = typeReponses;
    }

    public boolean isOk() {
        for (int index = 0; index < typeReponses.size(); index++) {
            if (!typeReponses.get(index).equals(TypeReponse.Croix)) {
                return false;
            }
        }
        // Il y a autant de croix que de couleur
        return typeReponses.size() == original.size();
    }

    public CodeCouleur getOriginal() {
        return original;
    }

    public void setOriginal(CodeCouleur original) {
        this.original = original;
    }

    public CodeCouleur getCompare() {
        return compare;
    }

    public void setCompare(CodeCouleur compare) {
        this.compare = compare;
    }

    public int getNbElement() {
        return nbElement;
    }

    public void setNbElement(int nbElement) {
        this.nbElement = nbElement;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(original, i);
        parcel.writeParcelable(compare, i);
        parcel.writeInt(nbElement);
        for (TypeReponse typeReponse : typeReponses) {
            parcel.writeString(typeReponse.name());
        }
    }
}
