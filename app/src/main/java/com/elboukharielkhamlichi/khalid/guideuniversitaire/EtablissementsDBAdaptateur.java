package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.ArrayList;
import java.util.List;


public class EtablissementsDBAdaptateur {

    private static final int BASE_VERSION = 1;
    private static final String BASE_NOM = "etablissements.db";

    // L’instance de la base qui sera manipulée au travers de cette classe.
    private SQLiteDatabase etabBaseDonnees;
    private EtabBaseOpenHelper baseHelper;

    // Classe interne contenant la définition des champs de la table table_etablissements.
    public static abstract class table_etablissements {
        public static final String TABLE_ETABLISSEMENTS = "table_etablissements";
        public static final String COLONNE_ID = "eid";
        public static final String COLONNE_NOM = "nom";
        public static final String COLONNE_VILLE = "ville";
        public static final String COLONNE_EMAIL = "email";
        public static final String COLONNE_TEL = "tel";
        public static final String COLONNE_ADRESSE = "adresse";
        public static final String COLONNE_TYPE = "type";
        public static final String COLONNE_ETABLISSEMENTS = "etablissements";
        public static final String COLONNE_IMAGE = "imageUri";

        // La requête de création de la structure de la base de données.
        private static final String REQUETE_CREATION_BD = "create table "
                + TABLE_ETABLISSEMENTS + " (" + COLONNE_ID + " integer primary key autoincrement, " +
                COLONNE_NOM + " text not null unique, " + COLONNE_VILLE + " text not null, " + COLONNE_EMAIL + " text not null, " +
                COLONNE_TEL + " text not null, " + COLONNE_ADRESSE + " text not null, " + COLONNE_TYPE + " text not null, " +
                COLONNE_ETABLISSEMENTS + " text not null, " + COLONNE_IMAGE + " text not null);";
    }

    private class EtabBaseOpenHelper extends SQLiteOpenHelper {

        public EtabBaseOpenHelper(Context context, String nom, SQLiteDatabase.CursorFactory cursorfactory, int version) {
            super(context, nom, cursorfactory, version); // Création d'une base de données s'il n'est pas déjà créée.
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(table_etablissements.REQUETE_CREATION_BD);  // Création de la table table_etablissements.
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Dans notre cas, nous supprimons la base et les données pour en créer
            // une nouvelle. Vous pouvez créer une logique de mise à jour
            // propre à votre base permettant de garder les données à la place.
            db.execSQL("DROP TABLE" + table_etablissements.TABLE_ETABLISSEMENTS + ";");

            // Création de la nouvelle base de données.
            onCreate(db);
        }
    }

    public EtablissementsDBAdaptateur(Context ctx) {
        baseHelper = new EtabBaseOpenHelper(ctx, BASE_NOM, null, BASE_VERSION);
    }

    // Ouvre la base de données en écriture.
    public SQLiteDatabase open() {
        etabBaseDonnees = baseHelper.getWritableDatabase();
        return etabBaseDonnees;
    }

    // Ferme la base de données.
    public void close() {
        etabBaseDonnees.close();
    }


    public Etablissement getEtablissementById(int id) {

        Cursor c = etabBaseDonnees.rawQuery("SELECT * FROM "+table_etablissements.TABLE_ETABLISSEMENTS+
                " WHERE "+table_etablissements.COLONNE_ID+"='"+id+"'" , null);
        // Si la requête ne renvoie pas de résultat.
        if (c.getCount() == 0)
            return null;

        Etablissement retEtablissement = new  Etablissement();

        // Extraction des valeurs depuis le curseur.
        c.moveToFirst();
        retEtablissement.setEid(c.getInt(0));
        retEtablissement.setNom(c.getString(1));
        retEtablissement.setVille(c.getString(2));
        retEtablissement.setEmail(c.getString(3));
        retEtablissement.setTel(c.getString(4));
        retEtablissement.setAdresse(c.getString(5));
        retEtablissement.setType(c.getString(6));
        System.out.println(c.getString(7));
        retEtablissement.setEtablissements(EtablissementConverter.stringToIntegerList(c.getString(7)));
        retEtablissement.setImageUri(c.getString(8));


        return retEtablissement;
    }

    /**
     * Retourne toutes les etablissements de la base de données dans un curseur.
     */
    public List<Etablissement> getAllEtablissements() {

        Cursor c = etabBaseDonnees.rawQuery("SELECT * FROM "+table_etablissements.TABLE_ETABLISSEMENTS, null);

        List<Etablissement> result = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                Etablissement et = new  Etablissement();

                et.setEid(c.getInt(0));
                et.setNom(c.getString(1));
                et.setVille(c.getString(2));
                et.setEmail(c.getString(3));
                et.setTel(c.getString(4));
                et.setAdresse(c.getString(5));
                et.setType(c.getString(6));
                System.out.println(c.getString(7));
                et.setEtablissements(EtablissementConverter.stringToIntegerList(c.getString(7)));
                et.setImageUri(c.getString(8));
                result.add(et);

            }while(c.moveToNext());
        }

        /*while (temp != null) {
            result.add(temp);
            temp = cursorToUniversite(c);
        }*/

        // Ferme le curseur pour libérer les ressources.
        c.close();

        return result;
    }

    /**
     * Insère une université dans la table des universités.
     */
    public long insertEtablissement(Etablissement etablissement) {

        ContentValues values = new ContentValues();
        values.put(table_etablissements.COLONNE_NOM, etablissement.getNom());
        values.put(table_etablissements.COLONNE_VILLE, etablissement.getVille());
        values.put(table_etablissements.COLONNE_EMAIL, etablissement.getEmail());
        values.put(table_etablissements.COLONNE_TEL, etablissement.getTel());
        values.put(table_etablissements.COLONNE_ADRESSE, etablissement.getAdresse());
        values.put(table_etablissements.COLONNE_TYPE, etablissement.getType());
        values.put(table_etablissements.COLONNE_ETABLISSEMENTS, EtablissementConverter.IntegerListToString(etablissement.getEtablissements()));
        values.put(table_etablissements.COLONNE_IMAGE, etablissement.getImageUri());

        return etabBaseDonnees.insert(table_etablissements.TABLE_ETABLISSEMENTS, null, values);
    }

    /**
     * Met à jour un etablissement dans la table des etablissements.
     */
    public int updateEtablissement(Etablissement etabToUpdate) {

        ContentValues values = new ContentValues();
        values.put(table_etablissements.COLONNE_NOM, etabToUpdate.getNom());
        values.put(table_etablissements.COLONNE_VILLE, etabToUpdate.getVille());
        values.put(table_etablissements.COLONNE_EMAIL, etabToUpdate.getEmail());
        values.put(table_etablissements.COLONNE_TEL, etabToUpdate.getTel());
        values.put(table_etablissements.COLONNE_ADRESSE, etabToUpdate.getAdresse());
        values.put(table_etablissements.COLONNE_TYPE, etabToUpdate.getType());
        values.put(table_etablissements.COLONNE_ETABLISSEMENTS, EtablissementConverter.IntegerListToString(etabToUpdate.getEtablissements()));
        values.put(table_etablissements.COLONNE_IMAGE, etabToUpdate.getImageUri());
        String[] selectionArgs = { String.valueOf(etabToUpdate.getEid()) };

        return etabBaseDonnees.update(table_etablissements.TABLE_ETABLISSEMENTS, values,table_etablissements.COLONNE_ID+"=?", selectionArgs);
    }


    /**
     * Supprime un etablissement.
     */
    public int removeUniversite(Etablissement etabToRemove) {

        String selection = table_etablissements.COLONNE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(etabToRemove.getEid()) };
        return etabBaseDonnees.delete(table_etablissements.TABLE_ETABLISSEMENTS, selection, selectionArgs);
    }

}

