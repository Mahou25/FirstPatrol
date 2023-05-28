package com.example.firstpatrol;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void main(String[] args) {
        Connexion connexion = new Connexion();
        try {
            connexion.connect();

            // Effectuer vos opérations sur la base de données ici

            connexion.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}