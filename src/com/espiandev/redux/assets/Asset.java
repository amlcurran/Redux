package com.espiandev.redux.assets;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Asset implements Parcelable {

    public static final DateFormat DATE_INSTANCE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzzz");
    private String name;
    //private Channel channel;
    private String description;
    private String uuid;
    private String key;
    private long duration;
    private Channel channel;
    private Date broadcastDate;

    public Asset() {

    }

    public Asset(Parcel in) {
        name = in.readString();
        description = in.readString();
        uuid = in.readString();
        key = in.readString();
        duration = in.readLong();
        channel = in.readParcelable(getClass().getClassLoader());
        try {
            broadcastDate = DATE_INSTANCE.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Asset fromJsonObject(JSONObject object) {
        try {
            Asset result = new Asset();
            result.name = object.getString("name");
            result.description = object.getString("description");
            result.channel = Channel.fromJsonObject(object.getJSONObject("channel"));
            result.uuid = object.getString("uuid");
            result.key = object.getString("key");
            result.duration = object.getJSONObject("timing").getLong("duration");
            result.broadcastDate = DATE_INSTANCE.parse(
                    object.getJSONObject("timing").getString("start"));
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getDuration() {
        return duration;
    }

    public Channel getChannel() {
        return channel;
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

    public Date getBroadcastDate() {
        return broadcastDate;
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
        parcel.writeParcelable(channel, 0);
        parcel.writeString(DATE_INSTANCE.format(broadcastDate));
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
