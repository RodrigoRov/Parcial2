package com.rodrigorov.cometela.parcial2.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.rodrigorov.cometela.parcial2.Database.Daos.NoticiaDao;
import com.rodrigorov.cometela.parcial2.Database.Daos.UserDao;
import com.rodrigorov.cometela.parcial2.Models.Noticia;
import com.rodrigorov.cometela.parcial2.Models.User;

@Database(entities = {User.class, Noticia.class}, version = 4,exportSchema = false)
public abstract class NoticiasDatabase extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract NoticiaDao noticiaDao();

    private static NoticiasDatabase INSTANCE;

    public static NoticiasDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NoticiasDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoticiasDatabase.class,"noticias_database")
                            .addMigrations(MIGRATION_1_2)
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE noticia_table");
            database.execSQL("CREATE TABLE noticia_table(" +
                    "noticia_id TEXT PRIMARY KEY NOT NULL," +
                    "noticia_titulo TEXT," +
                    "noticia_imagen TEXT," +
                    "noticia_fecha TEXT," +
                    "noticia_descripcion TEXT," +
                    "noticia_contenido TEXT," +
                    "noticia_juego TEXT);");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE toplayer_table(" +
                    "player_id TEXT PRIMARY KEY NOT NULL," +
                    "player_name TEXT," +
                    "player_biografia TEXT," +
                    "player_avatar TEXT," +
                    "player_game TEXT);");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE noticia_table");
            database.execSQL("CREATE TABLE noticia_table(" +
                    "noticia_id TEXT PRIMARY KEY NOT NULL," +
                    "noticia_titulo TEXT," +
                    "noticia_imagen TEXT," +
                    "noticia_fecha TEXT," +
                    "noticia_descripcion TEXT," +
                    "noticia_contenido TEXT," +
                    "noticia_juego TEXT," +
                    "noticia_fav INTEGER DEFAULT 0 NOT NULL);");
        }
    };
}
