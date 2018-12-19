package com.example.nandamochammad.kamuz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusModel implements Parcelable {

    private int id;
    private String kata;
    private String terjemah;

    public KamusModel(String kata, String terjemah){
        this.kata = kata;
        this.terjemah = terjemah;
    }

    public KamusModel(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getTerjemah() {
        return terjemah;
    }

    public void setTerjemah(String terjemah) {
        this.terjemah = terjemah;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kata);
        dest.writeString(this.terjemah);
    }

    protected KamusModel(Parcel in) {
        id = in.readInt();
        kata = in.readString();
        terjemah = in.readString();
    }

    public static final Creator<KamusModel> CREATOR = new Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel in) {
            return new KamusModel(in);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };
}
