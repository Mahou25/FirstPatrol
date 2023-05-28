package com.example.firstpatrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Time;

public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "patrolapp.db";
        private static final int DATABASE_VERSION = 1;

        public static final String TABLE_USERS = "users";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";


        private static final String COLUMN_GPS = "gps";
        private String TABLE_PATROUILLE = "patrouilles";
        private String COLUMN_Nom = "Rama GOUSSANOU";
        private Date COLUMN_DATE = Date.valueOf("25/01/23");

        private Time COLUMN_HEURE_DEPART = Time.valueOf("09:32");

        private Time COLUMN_HEURE_FIN = Time.valueOf("12:55");

        private String COLUMN_PRENOMS = "agybugfyzif bieuaibfIBI IBiuabfiiebfg";

        private String COLUMN_ROW = "Row";

        private String COLUMN_KPSPINNER ="zhguigiibzvjbjkn" ;

        private String COLUMN_LOCATION = "tyuizonbchvjzblhj";

        private String COLUMN_LEAKS_CHECK_BOX ="Leaks_checkBox";

        private String COLUMN_NOMpatrouille = "Nom Patrouille";

        private String COLUMN_NOM_DOSSIER = "NomDossier";

        private Patrouille COLUMN_PATROUILLE = new Patrouille();

        private String TABLE_DOSSIER_INSPECTION ="Inspection";


    public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                    "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT" +
                    ")";
            db.execSQL(CREATE_USERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }

        public boolean addUser(String email, String password) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);

            long result = db.insert(TABLE_USERS, null, values);

            return result != -1;
        }

        public boolean checkUser(String email, String password) {
            SQLiteDatabase db = getReadableDatabase();

            String[] columns = {COLUMN_ID};

            String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
            String[] selectionArgs = {email, password};

            Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            int count = cursor.getCount();

            cursor.close();

            boolean b = count > 0;
            return b;
        }

        public long createPatrouille(Patrouille patrouille) {
            SQLiteDatabase db = getWritableDatabase();

            String CREATE_PATROUILLES_TABLE = "CREATE TABLE" + TABLE_PATROUILLE
                    + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_GPS + "TEXT,"
                    + COLUMN_Nom + "TEXT,"
                    + COLUMN_DATE +"DATE,"
                    + COLUMN_HEURE_DEPART + "TEXT,"
                    + COLUMN_HEURE_FIN + "TEXT,"
                    + COLUMN_PRENOMS + "TEXT,"
                    + COLUMN_ROW + "TEXT,"
                    + COLUMN_KPSPINNER + "TEXT,"
                    + COLUMN_LOCATION + "TEXT,"
                    + COLUMN_LEAKS_CHECK_BOX +"TEXT"
                     +")";

            db.execSQL(CREATE_PATROUILLES_TABLE);

            ContentValues values = new ContentValues();
            // Ajoutez les valeurs spécifiques à la patrouille à insérer dans la table Patrouille

            long id = db.insert(TABLE_PATROUILLE, null, values);


            db.close();

            return id;
        }

        public boolean updateDossierInspection(DossierInspection dossierInspection) {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues values = new ContentValues();
            values.put(COLUMN_NOM_DOSSIER, dossierInspection.getNomInspection());
            // Ajoutez d'autres valeurs à mettre à jour dans la table DossierInspection

            String whereClause = COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(dossierInspection.getNomInspection())};

            int rowsAffected = db.update(TABLE_DOSSIER_INSPECTION, values, whereClause, whereArgs);

            db.close();

            return rowsAffected > 0;
        }

        public long createDossierInspection(DossierInspection dossierInspection) {
            SQLiteDatabase db = getWritableDatabase();

            String CREATE_DossierInspection_TABLE = "CREATE TABLE" + TABLE_DOSSIER_INSPECTION
                    + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PATROUILLE + "Patrouille,"
                    + COLUMN_NOMpatrouille + "TEXT"
                    +")";

            ContentValues values = new ContentValues();
            values.put(COLUMN_NOM_DOSSIER, dossierInspection.getNomInspection());
            // Ajoutez d'autres valeurs à insérer dans la table DossierInspection

            long id = db.insert(TABLE_DOSSIER_INSPECTION, null, values);


            db.close();

            return id;
        }

    }

