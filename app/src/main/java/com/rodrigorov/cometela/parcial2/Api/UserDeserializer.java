package com.rodrigorov.cometela.parcial2.Api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {


    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        User user = new User();

        JsonObject UserjsonObject = json.getAsJsonObject();
        user.setId(UserjsonObject.get("_id").getAsString());
        user.setUser(UserjsonObject.get("user").getAsString());
        user.setPassword(UserjsonObject.get("password").getAsString());

        JsonElement NotiJsonElement = UserjsonObject.getAsJsonObject("favoriteNews");


        return null;
    }
}
