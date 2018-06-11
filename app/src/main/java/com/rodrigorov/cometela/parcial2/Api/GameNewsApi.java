package com.rodrigorov.cometela.parcial2.Api;

import com.rodrigorov.cometela.parcial2.Models.FavsResponse;
import com.rodrigorov.cometela.parcial2.Models.Login;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.Token;
import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GameNewsApi {

    String ENDPOINT = "http://gamenewsuca.herokuapp.com";

    //USUARIO
    @GET("/users")
    Single<List<User>> getUsers();

    @FormUrlEncoded
    @POST("/users")
    Call<User> agregarUsuario(@Field("user") String user, @Field("avatar") String avatar, @Field("password") String password);

    @FormUrlEncoded
    @POST("/login")
    Call<Token> iniciarSesion(@Field("user") String string, @Field("password") String pass);

    @FormUrlEncoded
    @PUT("/users/{idUser}")
    Call<User> modifyUser(@Header("Authorization") String codigo,@Path("idUser") String idUser,@Field("password") String pass);

    /*@GET("/users/{idUser}")
    Call<User> getActiveUser(@Path("idUser") String idUser);
*/

    @GET("/users/detail")
    Call<User> getActiveUser(@Header("Authorization") String codigo);


    @FormUrlEncoded
    @POST("/users/{idUser}/fav")
    Call<FavsResponse> guardarFav(@Header("Authorization") String codigo, @Path("idUser") String idUser, @Field("new") String idNoticia);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/users/{userId}/fav", hasBody = true)
    Call<ResponseBody> deleteFav(@Header("Authorization") String token,@Path("userId") String userId,@Field("new") String noticiaId);


    //NOTICIA
    @GET("/news")
    Call<List<Noticia>> getNoticias(@Header("Authorization") String codigo);

    @GET("/news/type/list")
    Call<String[]>  getCategorias(@Header("Authorization") String codigo);

    @GET("/news/type/{game}")
    Call<List<Noticia>> getNoticiasByGame(@Header("Authorization") String token,@Path("game") String game);

    @GET("/news/{id}")
    Call<Noticia> getNoticiaDetail(@Header("Authorization") String token,@Path("id") String idNoticia);


    //TOP PLAYERS
    @GET("/players/type/{game}")
    Call<List<TopPlayers>> getPlayersByGame(@Header("Authorization") String string,@Path("game") String game);



}
