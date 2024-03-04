package com.limayrac.mastermind.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public enum TypeReponse implements Parcelable {
    Croix, // Les couleurs sont identiques et au bon endroits
    Rond, // La couleur est présente dans le code original mais pas au bon endroit
    Triangle;

    public static final Creator<TypeReponse> CREATOR = new Creator<TypeReponse>() {
        @Override
        public TypeReponse createFromParcel(Parcel in) {
            return TypeReponse.valueOf(in.readString());
        }

        @Override
        public TypeReponse[] newArray(int size) {
            return new TypeReponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.name());
    }
}
