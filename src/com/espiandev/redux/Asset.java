package com.espiandev.redux;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Asset implements Parcelable {

    private String name;
    //private Channel channel;
    private String description;
    private String uuid;
    private String key;
    private long duration;

    public Asset() {

    }

    public Asset(Parcel in) {
        name = in.readString();
        description = in.readString();
        uuid = in.readString();
        key = in.readString();
        duration = in.readLong();
    }

    public static Asset fromJsonObject(JSONObject object) {
        try {
            Asset result = new Asset();
            result.name = object.getString("name");
            result.description = object.getString("description");
            //result.channel = Channel.fromJsonObject(object.getJSONObject("channel"));
            result.uuid = object.getString("uuid");
            result.key = object.getString("key");
            //result.duration = object.getLong("duration");
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getDuration() {
        return duration;
    }

    public String getKey() {
        return key;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDescription() {
        return description;
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
        parcel.writeString(description);
        parcel.writeString(uuid);
        parcel.writeString(key);
        parcel.writeLong(duration);
    }

    @Override
    public String toString() {
        return String.format("%1$s\n%2$s", name, description);
    }

    public static final Creator<Asset> CREATOR = new Creator<Asset>() {

        public Asset createFromParcel(Parcel in) {
            return new Asset(in);
        }

        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };
}
