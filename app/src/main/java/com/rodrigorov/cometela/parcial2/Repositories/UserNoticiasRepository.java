package com.rodrigorov.cometela.parcial2.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodrigorov.cometela.parcial2.Api.GameNewsApi;
import com.rodrigorov.cometela.parcial2.Api.NoticiaDeserializer;
import com.rodrigorov.cometela.parcial2.Api.TokenDeserializer;
import com.rodrigorov.cometela.parcial2.Api.UserDeserializer;
import com.rodrigorov.cometela.parcial2.Database.Daos.NoticiaDao;
import com.rodrigorov.cometela.parcial2.Database.Daos.UserDao;
import com.rodrigorov.cometela.parcial2.Database.NoticiasDatabase;
import com.rodrigorov.cometela.parcial2.Models.Login;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.Token;
import com.rodrigorov.cometela.parcial2.Models.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
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
    private LiveData<List<User>> AllUsers;
    private LiveData<List<Noticia>> AllNoticias;
    private GameNewsApi gameNewsApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Noticia> Noticias;

    public UserNoticiasRepository(Application application){
        NoticiasDatabase db = NoticiasDatabase.getDatabase(application);
        userDao = db.userDao();
        noticiaDao = db.noticiaDao();
        AllUsers = userDao.getAllUsers();
        AllNoticias = noticiaDao.getAllNoticias();
        createAPI();
    }

    public LiveData<List<User>> getAllUsers() {
        return AllUsers;
    }

    public LiveData<List<Noticia>> getAllNoticias(String token) {
        Log.d("token UNR",token);
        Call<List<Noticia>> call = gameNewsApi.getNoticias("Bearer "+ token);
        final MutableLiveData<List<Noticia>> data = new MutableLiveData<>();
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, retrofit2.Response<List<Noticia>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                    AllNoticias = data;
                    //noticiaDao.insert(Noticias.toArray(new Noticia[Noticias.size()]));
                }
                else{
                    Log.d("Error","no succesful");
                }
            }
            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Log.d("ON Failure","UNR");
            }
        });

        return data;
    }
    public LiveData<List<Noticia>> getAllNoticias() {
        return AllNoticias;
    }

    public void insertU(User user){
        new insertUAsyncTask(userDao).execute(user);
    }

    public void insertN(Noticia noticia){

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

    private static class insertNAsyncTask extends AsyncTask<Noticia,Void,Void>{

        private NoticiaDao noticiaDao;

        insertNAsyncTask(NoticiaDao noticiaDao){
            this.noticiaDao = noticiaDao;
        }

        @Override
        protected Void doInBackground(Noticia... noticias) {
            noticiaDao.insert(noticias[0]);
            return null;
        }
    }

    private void createAPI(){
        Gson gson =  new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(Noticia.class, new NoticiaDeserializer())
                .registerTypeAdapter(User.class,new UserDeserializer())
                .registerTypeAdapter(Token.class,new TokenDeserializer())
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

    private DisposableSingleObserver<List<Noticia>> getRepositoriesObserver() {
        return new DisposableSingleObserver<List<Noticia>>() {
            @Override
            public void onSuccess(List<Noticia> noticias) {
                if (!noticias.isEmpty()) {
                    for(Noticia noticia: noticias)
                        Log.d("noticia",noticia.getTitle());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d("No se pudo","Cargar noticias");
                // Toast.makeText(, "Can not load repositories", Toast.LENGTH_SHORT).show();

            }
        };
    }

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

    public List<Noticia> getNoticias(String token) {
        Log.d("token UNR",token);
        Call<List<Noticia>> call = gameNewsApi.getNoticiasNOOB("Bearer "+ token);
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, retrofit2.Response<List<Noticia>> response) {
                if(response.isSuccessful()){
                    Noticias = response.body();
                }
                else{
                    Log.d("Response ",response.message());
                    Log.d("Error","no succesful");
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
        return Noticias;
    }
}
