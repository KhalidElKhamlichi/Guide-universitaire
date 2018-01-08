package com.elboukharielkhamlichi.khalid.guideuniversitaire.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.dao.EtablissementDAO;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

/**
 * Created by Khalid on 12/29/2017.
 */

@Database(entities = {Etablissement.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;


    public abstract EtablissementDAO etablissementDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "etablissements-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
