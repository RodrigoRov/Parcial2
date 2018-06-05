package com.rodrigorov.cometela.parcial2.Api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rodrigorov.cometela.parcial2.Models.Token;

import java.lang.reflect.Type;

public class TokenDeserializer implements JsonDeserializer<Token> {
    @Override
    public Token deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Token token = new Token();

        JsonObject jsonObject = json.getAsJsonObject();
        token.setToken(jsonObject.get("token").getAsString());

        return token;
    }
}
