package com.espiandev.redux;

import org.json.JSONException;
import org.json.JSONObject;

public class Asset {

    private String name;
    private Channel channel;
    private String description;
    private String uuid;
    private String key;
    private long duration;

    public static Asset fromJsonObject(JSONObject object) {
        try {
            Asset result = new Asset();
            result.name = object.getString("name");
            result.description = object.getString("description");
            result.channel = Channel.fromJsonObject(object.getJSONObject("channel"));
            result.uuid = object.getString("uuid");
            result.key = object.getString("key");
            result.duration = object.getLong("duration");
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

    public Channel getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }

}
