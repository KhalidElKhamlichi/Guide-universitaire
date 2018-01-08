package com.elboukharielkhamlichi.khalid.guideuniversitaire.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.List;

/**
 * Created by Gamer on 12/29/2017.
 */

@Dao
public interface EtablissementDAO {

    @Query("SELECT * FROM Etablissement")
    List<Etablissement> getAll();

    @Query("SELECT * FROM Etablissement WHERE eid IN (:etablissementIds)")
    List<Etablissement> loadAllByIds(int[] etablissementIds);

    @Query("SELECT * FROM Etablissement WHERE nom LIKE :nom LIMIT 1")
    Etablissement findByName(String nom);

    @Insert
    void insertAll(Etablissement... etablissement);

    @Delete
    void delete(Etablissement etablissement);

}
