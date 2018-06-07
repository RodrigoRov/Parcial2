package com.rodrigorov.cometela.parcial2.Api;

import com.rodrigorov.cometela.parcial2.Models.Login;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.Token;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GameNewsApi {

    String ENDPOINT = "http://gamenewsuca.herokuapp.com";

    @GET("/news")
    Call<List<Noticia>> getNoticias(@Header("Authorization") String codigo);

    @GET("/users")
    Single<List<User>> getUsers();

    @POST
    Single<ResponseBody> agregarUsuario(@Url String url, @Body User user);

    @FormUrlEncoded
    @POST("/login")
    Call<Token> iniciarSesion(@Field("user") String string, @Field("password") String pass);

    @FormUrlEncoded
    @PUT("/users/{idUser}")
    Call<User> modifyUser(@Path("idUser") String idUser);

    /*@GET("/users/{idUser}")
    Call<User> getActiveUser(@Path("idUser") String idUser);
*/

    @GET("/users/detail")
    Call<User> getActiveUser(@Header("Authorization") String codigo);



}
