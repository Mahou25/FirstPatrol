package com.example.firstpatrol;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patrouille extends AppCompatActivity {

    private TextView gpsTextView;
    private TextView dateTextView;
    private TextView heureDepartTextView;
    private TextView heureFinTextView;
    private EditText nomEditText;
    private EditText prenomEditText;
    private EditText rowEditText;
    private Spinner kpSpinner;
    private EditText locationEditText;
    private CheckBox leaksCheckBox;
    // Ajoutez ici les autres CheckBox et EditText pour les autres items

    private Button demarrerButton;
    private Button finButton;
    private Button enregistrerButton;
    private EditText nomPatrouille;


    private Location gpsLocation;
    private Date heureDepart;
    private Date heureFin;


    public String getNomPatrouille() {
        return nomPatrouille.getText().toString();
    }

    public void setNomPatrouille(EditText nomPatrouille) {
        this.nomPatrouille = nomPatrouille;
    }

    public long getIdPatrouille(Patrouille patrouille) {
        DatabaseHelper databaseHelper = new DatabaseHelper(Patrouille.this);
        patrouille = new Patrouille();
        long idPatrouille = databaseHelper.createPatrouille(patrouille);
        if (idPatrouille == 0) {
            throw new IllegalStateException("L'ID de la patrouille n'a pas encore été défini");
        }
        return idPatrouille;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrouille);

        // Initialisation des vues
        gpsTextView = findViewById(R.id.gpsTextView);
        dateTextView = findViewById(R.id.dateTextView);
        heureDepartTextView = findViewById(R.id.heureDepartTextView);
        heureFinTextView = findViewById(R.id.heureFinTextView);
        nomEditText = findViewById(R.id.nomEditText);
        prenomEditText = findViewById(R.id.prenomEditText);
        rowEditText = findViewById(R.id.rowEditText);
        kpSpinner = findViewById(R.id.kpSpinner);
        locationEditText = findViewById(R.id.locationEditText);
        leaksCheckBox = findViewById(R.id.leaksCheckBox);
        // Initialisez ici les autres CheckBox et EditText pour les autres items

        demarrerButton = findViewById(R.id.demarrerButton);
        finButton = findViewById(R.id.finButton);
        enregistrerButton = findViewById(R.id.enregistrerButton);

        // Récupération de la position GPS
        gpsLocation = obtenirPositionGPS();

        // Affichage de la position GPS dans le TextView correspondant
        gpsTextView.setText(gpsLocation.toString());

        //Ajouter le nom de la patrouille
        EditText nomPatrouille = findViewById(R.id.nomPatrouille);

        // Affichage de la date actuelle dans le TextView correspondant
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateActuelle = dateFormat.format(new Date());
        dateTextView.setText(dateActuelle);

        // Gestionnaire d'événements pour le bouton "Démarrer"
        demarrerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enregistrement de l'heure de départ
                heureDepart = new Date();
                heureDepartTextView.setText(heureDepart.toString());
            }
        });

        // Gestionnaire d'événements pour le bouton "Fin"
        finButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enregistrement de l'heure de fin
                heureFin = new Date();
                heureFinTextView.setText(heureFin.toString());
            }
        });

        // Gestionnaire d'événements pour le bouton "Enregistrer"
        enregistrerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = nomPatrouille.getText().toString();

                //Ajouter le nom de la patrouille avant de pouvoir enregistrer


                if (content.isEmpty()) {
                    throw new IllegalStateException("Le champ EditText est vide");
                }

                // Effectuer l'enregistrement de la patrouille dans le dossier d'inspection
                enregistrerPatrouille();
            }
        });
    }


    private Location obtenirPositionGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Vérifier les permissions d'accès à la localisation
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Demander la permission d'accéder à la localisation si elle n'est pas déjà accordée
            int PERMISSION_REQUEST_CODE = 0;
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            return null;
        }

        // Écouteur de localisation pour obtenir les mises à jour de position GPS
        @SuppressLint("MissingPermission")
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Mettre à jour la position GPS
                gpsLocation = location;
                gpsTextView.setText(gpsLocation.toString());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Obtenir les mises à jour de position GPS
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // Obtenir la dernière position connue
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            gpsLocation = lastKnownLocation;
            gpsTextView.setText(gpsLocation.toString());
        }

        return gpsLocation;
    }



    private void enregistrerPatrouille() {
        // Obtenez une instance de votre base de données SQLite
        SQLiteOpenHelper dbHelper = null;
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Créez un objet ContentValues pour stocker les valeurs à enregistrer
        ContentValues values = new ContentValues();
        values.put("nom", nomEditText.getText().toString());
        values.put("prenom", prenomEditText.getText().toString());
        // Ajoutez ici les autres valeurs à enregistrer, en fonction des éléments de la fiche de patrouille

        // Insérez les valeurs dans la table de la base de données
        long result = database.insert("patrouille", null, values);

        if (result != -1) {
            Toast.makeText(this, "Patrouille enregistrée avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement de la patrouille", Toast.LENGTH_SHORT).show();
        }

        // Fermez la connexion à la base de données
        database.close();
    }

}
