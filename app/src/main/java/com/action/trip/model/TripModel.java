package com.action.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by hanyuezi on 18/3/11.
 */
@JsonObject
public class TripModel implements Parcelable{
    @JsonField
    private int id;
    @JsonField
    private String location;
    @JsonField
    private String pot;
    @JsonField
    private String image;
    @JsonField
    private int type;
    @JsonField
    private int degree;
    @JsonField
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPot() {
        return pot;
    }

    public void setPot(String pot) {
        this.pot = pot;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.location);
        dest.writeString(this.pot);
        dest.writeString(this.image);
        dest.writeInt(this.type);
        dest.writeInt(this.degree);
        dest.writeString(this.desc);
    }

    public TripModel() {
    }

    protected TripModel(Parcel in) {
        this.id = in.readInt();
        this.location = in.readString();
        this.pot = in.readString();
        this.image = in.readString();
        this.type = in.readInt();
        this.degree = in.readInt();
        this.desc = in.readString();
    }

    public static final Creator<TripModel> CREATOR = new Creator<TripModel>() {
        @Override
        public TripModel createFromParcel(Parcel source) {
            return new TripModel(source);
        }

        @Override
        public TripModel[] newArray(int size) {
            return new TripModel[size];
        }
    };
}
