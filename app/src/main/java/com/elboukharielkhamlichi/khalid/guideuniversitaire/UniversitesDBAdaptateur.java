package com.elboukharielkhamlichi.khalid.guideuniversitaire;

/**
 * Created by Khalid on 12/29/2017.
 */

public class UniversitesDBAdaptateur {

//    private static final int BASE_VERSION = 1;
//    private static final String BASE_NOM = "universites.db";
//
//    // L’instance de la base qui sera manipulée au travers de cette classe.
//    private SQLiteDatabase univBaseDonnees;
//    private UnivBaseOpenHelper baseHelper;
//
//    // Classe interne contenant la définition des champs de la table table_universites.
//    public static abstract class table_universites {
//        public static final String TABLE_UNIVERSITES = "table_universites";
//        public static final String COLONNE_ID = "_id";
//        public static final int COLONNE_ID_ID = 0;
//        public static final String COLONNE_NOM = "nom";
//        public static final int COLONNE_NOM_ID = 1;
//        public static final String COLONNE_VILLE = "ville";
//        public static final int COLONNE_VILLE_ID = 2;
//
//        // La requête de création de la structure de la base de données.
//        private static final String REQUETE_CREATION_BD = "create table "
//                + TABLE_UNIVERSITES + " (" + COLONNE_ID + " integer primary key autoincrement, " +
//                COLONNE_NOM + " text not null unique, " + COLONNE_VILLE + " text not null);";
//    }
//
//    private class UnivBaseOpenHelper extends SQLiteOpenHelper {
//
//        public UnivBaseOpenHelper(Context context, String nom, SQLiteDatabase.CursorFactory cursorfactory, int version) {
//            super(context, nom, cursorfactory, version); // Création d'une base de données s'il n'est pas déjà créée.
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(table_universites.REQUETE_CREATION_BD);  // Création de la table table_universites.
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // Dans notre cas, nous supprimons la base et les données pour en créer
//            // une nouvelle. Vous pouvez créer une logique de mise à jour
//            // propre à votre base permettant de garder les données à la place.
//            db.execSQL("DROP TABLE" + table_universites.TABLE_UNIVERSITES + ";");
//
//            // Création de la nouvelle base de données.
//            onCreate(db);
//        }
//    }
//
//    // Constructeur de la classe adaptateur UniversitesDBAdaptateur.
//    public UniversitesDBAdaptateur(Context ctx) {
//        baseHelper = new UnivBaseOpenHelper(ctx, BASE_NOM, null, BASE_VERSION);
//    }
//
//    // Ouvre la base de données en écriture.
//    public SQLiteDatabase open() {
//        univBaseDonnees = baseHelper.getWritableDatabase();
//        return univBaseDonnees;
//    }
//
//    // Ferme la base de données.
//    public void close()
//
//    {
//        univBaseDonnees.close();
//    }
//
//    public SQLiteDatabase getBaseDonnees()
//
//    {
//        return univBaseDonnees;
//    }
//
//    /**
//     * Récupère une université en fonction de son nom.
//     */
//    /* La classe Etablissement doit être préalablement définie, elle sert à créer les objets métiers contenant des informations
//    sur une université. */
//    public Etablissement getUniversite(String nom) {
//        // Insérer le code de requête d’une université.
//    }
//
//    /**
//     * Récupère une université en fonction de son id.
//     */
//    public Etablissement getUniversite(int id) {
//        // Insérer le code de requête d’une université.
//    }
//
//    /**
//     * Convertie un curseur en un objet du type Etablissement.
//     */
//    private   Etablissement cursorToUniversite(Cursor c) {
//        // Si la requête ne renvoie pas de résultat.
//        if (c.getCount() == 0)
//            return null;
//
//        Etablissement retUniversite = new  Etablissement();
//
//        // Extraction des valeurs depuis le curseur.
//        c.moveToFirst();
//        retUniversite.setId(c.getInt(table_universites.COLONNE_ID_ID));
//        retUniversite.setNom(c.getString(table_universites.COLONNE_NOM_ID));
//        retUniversite.setVille(c.getFloat(table_universites.COLONNE_RAYON_ID));
//
//        // Ferme le curseur pour libérer les ressources.
//        c.close();
//        return retUniversite;
//    }
//
//    /**
//     * Retourne toutes les université de la base de données dans un curseur.
//     */
//    public Cursor getAllUniversites() {
//        // Insérer le code de requête de l’ensemble des universités de la base.
//    }
//
//    /**
//     * Transforme un Cursor en objet de type ArrayListe<Etablissement>.
//     ** @param c
//     * Le curseur à utiliser pour récupérer les données des Université.
//     * @return Une liste ArrayList<Etablissement> contenant la liste des universités récupérés du curseur.
//     */
//
//    public ArrayList<Etablissement> cursorToUniversites(Cursor c) {
//        // Si la requête ne renvoie pas de résultat.
//        if (c.getCount() == 0)
//            return new ArrayList<Etablissement>(0);
//
//        ArrayList<Etablissement> retUniversites = new ArrayList<Etablissement>(c.getCount());
//        c.moveToFirst();
//        do {
//            Etablissement universite = new Etablissement();
//            universite.setId(c.getInt(table_universites.COLONNE_ID_ID));
//            universite.setNom(c.getString(table_universites.COLONNE_NOM_ID));
//            universite.setVille(c.getFloat(table_universites.COLONNE_RAYON_ID));
//            retUniversites.add(universite);
//        }
//        while (c.moveToNext());
//
//        // Ferme le curseur pour libérer les ressources.
//        c.close();
//        return retUniversites;
//    }
//
//    /**
//     * Insère une université dans la table des universités.
//     */
//    public long insertUniversite(Etablissement universite) {
//        // Insérer le code d’insertion.
//    }
//
//    /**
//     * Met à jour une université dans la table des université.
//     */
//    public int updateUniversite(int id, Etablissement universiteToUpdate) {
//        // Insérer le code de mise à jour de la base.
//    }
//
//    public int updateUniversite(ContentValues valeurs, String where, String[] whereArgs) {
//        return univBaseDonnees.update(table_universites.TABLE_UNIVERSITES, valeurs, where, whereArgs);
//    }
//
//    /**
//     * Supprime une université à partir de son nom.
//     */
//    public int removeUniversite(String nom) {
//        // Insérer le code de suppression d’une université.
//    }
//
//    /**
//     * Supprime une université à partir de son id.
//     */
//    public int removeUniversite(int id) {
//        // Insérer le code de suppression d’une université.
//    }

}
