package com.rodrigorov.cometela.parcial2.Api;

import com.rodrigorov.cometela.parcial2.Models.Login;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface GameNewsApi {

    String ENDPOINT = "http://gamenewsuca.herokuapp.com";

    @GET("/news")
    Single<List<Noticia>> getNoticias();

    @GET("/users")
    Single<List<User>> getUsers();

    @POST
    Single<ResponseBody> agregarUsuario(@Url String url, @Body User user);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/login")
    Call<ResponseBody> iniciarSesion(@Body String data);



}
