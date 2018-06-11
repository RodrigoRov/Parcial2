package com.rodrigorov.cometela.parcial2.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodrigorov.cometela.parcial2.Api.FavsResponseDeserializer;
import com.rodrigorov.cometela.parcial2.Api.GameNewsApi;
import com.rodrigorov.cometela.parcial2.Api.NoticiaDeserializer;
import com.rodrigorov.cometela.parcial2.Api.TokenDeserializer;
import com.rodrigorov.cometela.parcial2.Api.TopPlayersDeserializer;
import com.rodrigorov.cometela.parcial2.Api.UserDeserializer;
import com.rodrigorov.cometela.parcial2.Database.Daos.NoticiaDao;
import com.rodrigorov.cometela.parcial2.Database.Daos.UserDao;
import com.rodrigorov.cometela.parcial2.Database.NoticiasDatabase;
import com.rodrigorov.cometela.parcial2.Models.FavsResponse;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.Token;
import com.rodrigorov.cometela.parcial2.Models.TopPlayers;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class UserNoticiasRepository {

    private final NoticiaDao noticiaDao;
    private final UserDao userDao;
    private GameNewsApi gameNewsApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Noticia> Noticias;

    public UserNoticiasRepository(Application application){
        NoticiasDatabase db = NoticiasDatabase.getDatabase(application);
        userDao = db.userDao();
        noticiaDao = db.noticiaDao();
        createAPI();
    }

    /*
    *
    *
    *
    *CREANDO API CON RETROFIT Y GSON
    *
    *
    *
     */

    private void createAPI(){
        Gson gson =  new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .registerTypeAdapter(Noticia.class, new NoticiaDeserializer())
                .registerTypeAdapter(User.class,new UserDeserializer())
                .registerTypeAdapter(Token.class,new TokenDeserializer())
                .registerTypeAdapter(FavsResponse.class,new FavsResponseDeserializer())
                .registerTypeAdapter(TopPlayers.class, new TopPlayersDeserializer())
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                                Credentials.basic("00357215","00357215"));

                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GameNewsApi.ENDPOINT)
                /*.client(okHttpClient)*/
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gameNewsApi = retrofit.create(GameNewsApi.class);

    }

    /*
    **
    **
    **
    USER ACCESS
    **
    **
    **
     */

    public LiveData<String> login(String user,String password){
        final MutableLiveData<String> token = new MutableLiveData<>();
        Call<Token> call = gameNewsApi.iniciarSesion(user,password);
        call.enqueue(new Callback<Token>(){
            @Override
            public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
                if(response.isSuccessful()){
                    Log.d("Token",response.body().getToken());
                    token.setValue(response.body().getToken());
                }
                else{
                    Log.d("No pudo","obtener token");
                }

            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Log.d("Error","error");
        }
        });
        return token;
    }

    public LiveData<User> getUserDetail(String token){
        final MutableLiveData<User> data = new MutableLiveData<>();
        final User user = new User();
        Call<User> call = gameNewsApi.getActiveUser("Bearer "+token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                user.setUser(response.body().getUser());
                user.setId(response.body().getId());
                Log.d("User Id",response.body().getId());
                user.setPassword(response.body().getPassword());
                user.setFavoriteNews(response.body().getFavoriteNews());
                data.setValue(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Failure",t.getMessage());
                Log.d("call",call.request().toString());
            }
        });
        return data;
    }


    private static class insertUAsyncTask extends AsyncTask<User,Void,Void>{

        private UserDao userDao;

        insertUAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
    public void insertU(User user){
        new insertUAsyncTask(userDao).execute(user);
    }

    /*
    *
    *
    *NOTICIAS ACCESS
    *
    *
     */

    private static class insertNAsyncTask extends AsyncTask<Noticia,Void,Void>{

        private NoticiaDao noticiaDao;

        insertNAsyncTask(NoticiaDao noticiaDao){
            this.noticiaDao = noticiaDao;
        }

        @Override
        protected Void doInBackground(Noticia... noticias) {
            noticiaDao.insert(noticias);
            return null;
        }
    }

    private static class getAllNoticiasDAO extends AsyncTask<Void,Void,List<Noticia>>{

        NoticiaDao noticiaDao;
        MutableLiveData<List<Noticia>> data;

        getAllNoticiasDAO(NoticiaDao noticiaDao, MutableLiveData<List<Noticia>> data){
            this.noticiaDao = noticiaDao;
            this.data = data;
        }

        @Override
        protected List<Noticia> doInBackground(Void... voids) {
            return noticiaDao.getAllNoticias();
        }

        @Override
        protected void onPostExecute(List<Noticia> noticias) {
            super.onPostExecute(noticias);
            data.setValue(noticias);
        }
    }
    public LiveData<List<Noticia>> getAllNoticias(String token) {
        Call<List<Noticia>> call = gameNewsApi.getNoticias("Bearer "+ token);
        final MutableLiveData<List<Noticia>> data = new MutableLiveData<>();
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, retrofit2.Response<List<Noticia>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                    if (response.body() != null) {
                        new insertNAsyncTask(noticiaDao).execute(response.body().toArray(new Noticia[response.body().size()]));
                    }
                    //noticiaDao.insert(Noticias.toArray(new Noticia[Noticias.size()]));
                }
                else{
                    Log.d("Error","no succesful");
                }
            }
            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                new getAllNoticiasDAO(noticiaDao,data).execute();
            }
        });
        return data;
    }

    public void setFavoritos(String token,String userId,String noticiaId){
        Call<FavsResponse> call = gameNewsApi.guardarFav("Bearer "+token,userId,noticiaId);
        call.enqueue(new Callback<FavsResponse>() {
            @Override
            public void onResponse(Call<FavsResponse> call, retrofit2.Response<FavsResponse> response) {
                if (response.isSuccessful()){
                    Log.d("Success",response.body().getSuccess());
                }
                else{
                    Log.d("call",call.request().toString());
                    Log.d("response",response.message());
                    Log.d("Error","Not Succesful");
                }
            }
            @Override
            public void onFailure(Call<FavsResponse> call, Throwable t) {
                Log.d("On Failure",t.getMessage());
            }
        });
        return;
    }

    public void deleteFavoritos(String token,String userId,String noticiaId){
        Call<ResponseBody> call = gameNewsApi.deleteFav("Bearer "+token,userId,noticiaId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("Success",response.body().toString());
                }
                else{
                    Log.d("Call",call.request().toString());
                    Log.d("REsponse",response.message());
                    Log.d("Error","no succesful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("On Failure",t.getMessage());
            }
        });
    }

    public LiveData<Noticia> getNoticia(String token,String noticiaId){
        Call<Noticia> call = gameNewsApi.getNoticiaDetail("Bearer "+token,noticiaId);
        final MutableLiveData<Noticia> data = new MutableLiveData<>();
        call.enqueue(new Callback<Noticia>() {
            @Override
            public void onResponse(Call<Noticia> call, retrofit2.Response<Noticia> response) {
                if (response.isSuccessful()){
                    //TODO que salga de base datos
                    data.setValue(response.body());
                }
                else {
                    Log.d("call",call.request().toString());
                    Log.d("REsponse",response.message());
                    Log.d("Error","Not succesful");
                }
            }
            @Override
            public void onFailure(Call<Noticia> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });
        return data;
    }

    public LiveData<List<Noticia>> getNoticiaByGame(String token,String game){
        Call<List<Noticia>> call = gameNewsApi.getNoticiasByGame("Bearer "+token,game);
        final MutableLiveData<List<Noticia>> data = new MutableLiveData<>();
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, retrofit2.Response<List<Noticia>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }
                else{
                    Log.d("No", "Succesful");
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Log.d("ON Failure",t.getMessage());
            }
        });
        return data;
    }

    public LiveData<String []> getCategorias(String token){
        final MutableLiveData<String []> data = new MutableLiveData<>();
        Call<String []> call = gameNewsApi.getCategorias("Bearer "+token);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, retrofit2.Response<String[]> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }
                else{
                    Log.d("Call",call.request().toString());
                    Log.d("Response",response.message());
                    Log.d("Categorias","No successful");
                }
            }
            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Log.d("Categorias","On failure");
                Log.d("Error",t.getMessage());
            }
        });
        return data;
    }

    /*
    *
    *
    *TOP PLAYERS
    *
    *
     */

    public LiveData<List<TopPlayers>> getPlayersByGame(String token,String game){
        final MutableLiveData<List<TopPlayers>> data = new MutableLiveData<>();
        Call<List<TopPlayers>> call = gameNewsApi.getPlayersByGame("Bearer "+token,game);
        call.enqueue(new Callback<List<TopPlayers>>() {
            @Override
            public void onResponse(Call<List<TopPlayers>> call, retrofit2.Response<List<TopPlayers>> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    System.out.println("Success");
                }
                else{
                   Log.d("No succesful","no");
                }
            }

            @Override
            public void onFailure(Call<List<TopPlayers>> call, Throwable t) {
                Log.d("On failure",t.getMessage());
            }
        });
        return data;
    }



}
