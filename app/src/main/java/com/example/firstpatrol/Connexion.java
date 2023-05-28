package com.example.firstpatrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect() throws SQLException {
        String url = "jdbc:sqlite:/chemin/vers/ma_base_de_donnees.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Connexion à la base de données établie !");
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Connexion à la base de données fermée.");
        }
    }
}

