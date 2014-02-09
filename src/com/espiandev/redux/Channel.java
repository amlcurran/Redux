package com.espiandev.redux;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Channel implements Parcelable {

    private String name;
    private String displayName;

    public Channel() {

    }

    public Channel(Parcel in) {
        name = in.readString();
        displayName = in.readString();
    }

    public static Channel fromJsonObject(JSONObject jsonObject) {
        try {
            Channel channel = new Channel();
            channel.name = jsonObject.getString("name");
            channel.displayName = jsonObject.getString("display_name");
            return channel;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(displayName);
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {

        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
