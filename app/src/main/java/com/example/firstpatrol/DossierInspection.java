package com.example.firstpatrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;



public class DossierInspection extends AppCompatActivity {

    private Button btnCreatePatrouille;
    private ListView listPatrouilles;
    private Button btnFinInspection;
    private List<Patrouille> patrouilles;
    private PatrouilleAdapter patrouilleAdapter;
    private String employe;

    private Patrouille patrouille;

    private EditText nomInspection;

    public String getNomInspection() {
        return String.valueOf(nomInspection);
    }


    public long getIdInspection() {
        DatabaseHelper databaseHelper = new DatabaseHelper(DossierInspection.this);
        DossierInspection inspection = new DossierInspection();
        long id = databaseHelper.createDossierInspection(inspection);
        if (id == 0) {
            throw new IllegalStateException("L'ID du dossier d'inspection n'a pas encore été défini");
        }
        return id;
    }

    

    public void setNomInspection(EditText nomInspection) {
        this.nomInspection = nomInspection;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_inspection);

        btnCreatePatrouille = findViewById(R.id.button_start_patrol);
        listPatrouilles = findViewById(R.id.listPatrouilles);
        btnFinInspection = findViewById(R.id.btnFinInspection);
        nomInspection = findViewById(R.id.nomInspection);

        patrouilles = new ArrayList<>();
        patrouilleAdapter = new PatrouilleAdapter(this, patrouilles);
        listPatrouilles.setAdapter((ListAdapter) patrouilleAdapter);

        btnCreatePatrouille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour créer une nouvelle patrouille dans le dossier d'inspection
                btnCreatePatrouille.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Créer une nouvelle patrouille
                        Patrouille patrouille = new Patrouille();
                        // Effectuer les opérations nécessaires, telles que définir les détails de la patrouille, etc.
                        // Enregistrer la patrouille dans la base de données
                        DatabaseHelper databaseHelper = null;
                        long patrouilleId = databaseHelper.createPatrouille(patrouille);
                        if (patrouilleId != -1) {
                            // La patrouille a été créée avec succès
                            // Mettre à jour l'interface utilisateur en conséquence, par exemple, ajouter la patrouille à la liste de patrouilles du dossier d'inspection
                            patrouilles.add(patrouille);
                            patrouilleAdapter.notifyDataSetChanged();
                        } else {
                            // Une erreur s'est produite lors de la création de la patrouille
                            Toast.makeText(DossierInspection.this, "Erreur lors de la création de la patrouille", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnFinInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour terminer l'inspection
                btnFinInspection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Terminer l'inspection
                        DossierInspection inspection = new DossierInspection();
                        // Enregistrer le dossier d'inspection dans la base de données
                        Context context = DossierInspection.this;
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        boolean success = databaseHelper.updateDossierInspection(inspection);
                        if (success) {
                            // L'inspection a été terminée et le dossier d'inspection a été enregistré avec succès
                            // Mettre à jour l'interface utilisateur en conséquence, par exemple, marquer l'inspection comme terminée
                        } else {
                            // Une erreur s'est produite lors de l'enregistrement du dossier d'inspection
                            Toast.makeText(DossierInspection.this, "Erreur lors de l'enregistrement du dossier d'inspection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        listPatrouilles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patrouille patrouille = patrouilles.get(position);
                // Action lorsque l'utilisateur sélectionne une patrouille
                listPatrouilles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Patrouille patrouille = patrouilles.get(position);
                        // Ouvrir la fiche de patrouille dans une nouvelle activité
                        Intent intent = new Intent(DossierInspection.this, Patrouille.class);
                        intent.putExtra("patrouilleId", patrouille.getIdPatrouille(patrouille));
                        startActivity(intent);
                    }
                });

            }
        });
    }
}






