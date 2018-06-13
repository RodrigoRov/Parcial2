package com.rodrigorov.cometela.parcial2.Api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
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

        JsonArray favoriteNews = UserjsonObject.getAsJsonArray("favoriteNews");
        StringBuilder sb = new StringBuilder();
        Log.d("array FAVNEws",String.valueOf(favoriteNews.size()));
        for(int i = 0;i<favoriteNews.size();i++){
            if (i==favoriteNews.size()-1){
                System.out.println(favoriteNews.get(i).getAsString());
                sb.append(favoriteNews.get(i).getAsString());
                //sb.append(favoriteNews.get(i).getAsJsonObject().get("_id").getAsString());
            }
            else {
                System.out.println(favoriteNews.get(i).getAsString());
                sb.append(favoriteNews.get(i).getAsString());
                sb.append(",");
                //sb.append(favoriteNews.get(i).getAsJsonObject().get("_id").getAsString());
                //sb.append(",");
            }
        }
        user.setFavoriteNews(sb.toString());
        Log.d("Favs",sb.toString());
        return user;
    }
}
