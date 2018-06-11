package com.rodrigorov.cometela.parcial2.Api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rodrigorov.cometela.parcial2.Models.TopPlayers;

import java.lang.reflect.Type;

public class TopPlayersDeserializer implements JsonDeserializer<TopPlayers> {
    @Override
    public TopPlayers deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TopPlayers player = new TopPlayers();

        JsonObject jsonObject = json.getAsJsonObject();
        player.setPlayerId(jsonObject.get("_id").getAsString());
        player.setPlayerAvatar(jsonObject.get("avatar").getAsString());
        player.setPlayerName(jsonObject.get("name").getAsString());
        player.setPlayerBiografia(jsonObject.get("biografia").getAsString());
        player.setPlayerGame(jsonObject.get("game").getAsString());
        return null;
    }
}
