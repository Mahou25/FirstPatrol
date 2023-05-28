package com.example.firstpatrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

// MesInspectionsActivity.java

public class MesInspections extends AppCompatActivity {

    private Button btnCreateInspection;
    private ListView listInspections;
    private List<DossierInspection> inspections;
    private InspectionAdapter inspectionAdapter;
    private EditText nomInspection;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_inspections);

        btnCreateInspection = findViewById(R.id.btnCreateInspection);
        listInspections = findViewById(R.id.listInspections);
        nomInspection = findViewById(R.id.nomInspection);

        inspections = new ArrayList<>();
        inspectionAdapter = new InspectionAdapter((List<DossierInspection>) this, (Context) inspections);
        listInspections.setAdapter((ListAdapter) inspectionAdapter);

        btnCreateInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Action pour créer un nouveau dossier d'inspection
                btnCreateInspection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Créer un nouveau dossier d'inspection
                        DossierInspection dossierInspection = new DossierInspection();
                        // Effectuer les opérations nécessaires, telles que définir le nom du dossier, etc.
                        String nomDossier = nomInspection.getText().toString();
                        // Vérifier si le nom du dossier est vide
                        if (TextUtils.isEmpty(nomDossier)) {
                            // Lever une exception ou afficher un message d'erreur selon vos besoins
                            throw new IllegalArgumentException("Le nom du dossier d'inspection ne peut pas être vide");
                        } else {
                            // Définir le nom du dossier d'inspection
                            dossierInspection.setNomInspection(nomInspection);
                            // Effectuer d'autres opérations nécessaires
                        }

                        // Enregistrer le dossier d'inspection dans la base de données
                        DatabaseHelper databaseHelper = null;
                        assert databaseHelper != null;
                        long dossierId = databaseHelper.createDossierInspection(dossierInspection);
                        if (dossierId != -1) {
                            // Le dossier d'inspection a été créé avec succès
                            // Mettre à jour l'interface utilisateur en conséquence, par exemple, ajouter le dossier à la liste d'inspections
                            inspections.add(dossierInspection);
                            inspectionAdapter.notifyDataSetChanged();
                        } else {
                            // Une erreur s'est produite lors de la création du dossier d'inspection
                            Toast.makeText(MesInspections.this, "Erreur lors de la création du dossier d'inspection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        listInspections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DossierInspection inspection = inspections.get(position);
                // Action lorsque l'utilisateur sélectionne un dossier d'inspection
                listInspections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DossierInspection inspection = inspections.get(position);
                        // Ouvrir le dossier d'inspection dans une nouvelle activité
                        Intent intent = new Intent(MesInspections.this, DossierInspection.class);
                        intent.putExtra("inspectionId", inspection.getIdInspection());
                        startActivity(intent);
                    }
                });


            }
        });


    }
}
