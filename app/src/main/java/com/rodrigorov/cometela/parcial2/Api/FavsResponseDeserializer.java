package com.rodrigorov.cometela.parcial2.Api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rodrigorov.cometela.parcial2.Models.FavsResponse;

import java.lang.reflect.Type;

public class FavsResponseDeserializer implements JsonDeserializer<FavsResponse> {
    @Override
    public FavsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FavsResponse favsResponse = new FavsResponse();

        JsonObject favObject = json.getAsJsonObject();
        favsResponse.setSuccess(favObject.get("success").getAsString());

        return favsResponse;
    }
}
