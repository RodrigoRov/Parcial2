package com.rodrigorov.cometela.parcial2.Api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rodrigorov.cometela.parcial2.Models.Noticia;

import java.lang.reflect.Type;

public class NoticiaDeserializer implements JsonDeserializer<Noticia>{
    @Override
    public Noticia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Noticia noticia = new Noticia();

        JsonObject jsonObject = json.getAsJsonObject();
        noticia.setTitle(jsonObject.get("title").getAsString());
        noticia.setBody(jsonObject.get("body").getAsString());
        noticia.setGame(jsonObject.get("game").getAsString());
        noticia.setCoverImage(jsonObject.get("coverImage").getAsString());
        noticia.setCreate_date(jsonObject.get("created_date").getAsString());
        noticia.setDescription(jsonObject.get("description").getAsString());

        return noticia;
    }
}
