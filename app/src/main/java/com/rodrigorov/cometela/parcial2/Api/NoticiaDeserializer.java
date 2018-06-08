package com.rodrigorov.cometela.parcial2.Api;

import android.util.Log;

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
        noticia.setId(jsonObject.get("_id").getAsString());

        noticia.setTitle(jsonObject.get("title").getAsString());
        /*LOG
        Log.d("title",jsonObject.get("title").getAsString());*/
        noticia.setBody(jsonObject.get("body").getAsString());
        /*LOG
        Log.d("body",jsonObject.get("body").getAsString());*/
        noticia.setGame(jsonObject.get("game").getAsString());
        /*LOG
        Log.d("game",jsonObject.get("game").getAsString());*/


        try {
            noticia.setCoverImage(jsonObject.get("coverImage").getAsString());
            /*LOG
            Log.d("CoverImage", jsonObject.get("coverImage").getAsString());*/
        }catch (Exception e){
            /*Log.d("COVERIMG",e.getMessage());*/
            noticia.setCoverImage("");
        }

        try {
            noticia.setCreate_date(jsonObject.get("created_date").getAsString());
            /*LOG
            Log.d("Createdate",jsonObject.get("created_date").getAsString());*/
        }catch (Exception e){
            noticia.setCreate_date("");
        }

        try {
            noticia.setDescription(jsonObject.get("description").getAsString());
            /*LOG
            Log.d("DESC",jsonObject.get("description").getAsString());*/
        }catch (Exception e){
            /*Log.d("DESC EXCEP",e.getMessage());*/
            noticia.setDescription("");
        }
        return noticia;
    }
}
