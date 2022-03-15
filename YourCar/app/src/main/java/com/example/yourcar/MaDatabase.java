package com.example.yourcar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;


public class MaDatabase extends SQLiteOpenHelper {

     private Context context;
     private static final int BASE_VERSION = 1;
     private static final String BASE_NOM = "Offres";
     private static final String TABLE_NOM = "historique";
     public static final String COLONNE_ID = "id";

     public static final String COLONNE_MARQUE = "marque";
     public static final String COLONNE_MODEL="model";
     public static final String COLONNE_ANNEE = "annee";
     public static final String COLONNE_KM="km";
    public static final String COLONNE_PRIX="prix";
    public static final String COLONNE_CHV="cheveaux";
    public static final String COLONNE_CARBU = "carburant";
    public static final String COLONNE_DATE="date_envoi_msg";

     private MaDatabase db;
    private SQLiteDatabase maBaseDonnees;
     String queryCreate = " CREATE TABLE " + TABLE_NOM +
             " (" + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + COLONNE_MARQUE + " TEXT, "
             + COLONNE_MODEL + " TEXT, "
             + COLONNE_ANNEE + " TEXT, "
             + COLONNE_KM + " TEXT, "
             + COLONNE_PRIX + " TEXT, "
             + COLONNE_CHV + " TEXT, "
             + COLONNE_CARBU + " TEXT, "
             + COLONNE_DATE + " TEXT);";

     String querydrop = " drop table " + TABLE_NOM + ";";

    public MaDatabase(Context context) {
        super(context, BASE_NOM, null, BASE_VERSION);

    }
    //PARTIE DATABASE

         @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(queryCreate);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int newVersion) {

            Log.w(MaDatabase.class.getName(),
                    "Upgrading database from version " + ancienneVersion + " to "
                            + newVersion + ", which will destroy all old data");

            db.execSQL(querydrop);//supprime l'ancienne table
            onCreate(db);        //on creer une nouvelle bd
        }
    //PARTIE BASE DE DONNÉE

    public Boolean insertData(String marque, String model, String annee, String km,
                              String prix, String chv,
                              String carbu, String datemsg){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLONNE_MARQUE, marque);
        contentValues.put(COLONNE_MODEL, model);
        contentValues.put(COLONNE_ANNEE, annee);
        contentValues.put(COLONNE_KM, km);
        contentValues.put(COLONNE_PRIX, prix);
        contentValues.put(COLONNE_CHV, chv);
        contentValues.put(COLONNE_CARBU, carbu);
        contentValues.put(COLONNE_DATE, String.valueOf(datemsg));
        long result = db.insert(TABLE_NOM, null, contentValues);
        if (result == -1) return false;
        else{
            Log.d("test", "insertData: inseré");
            return true;
    }


    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from "+ TABLE_NOM +";", null);

    }


}
