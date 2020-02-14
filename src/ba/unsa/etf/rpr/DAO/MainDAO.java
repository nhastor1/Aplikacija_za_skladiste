package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Manufacturer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class MainDAO {
    private static MainDAO instance = null;
    private Connection conn;
    private PreparedStatement checkIsThereAnyDatabase;

    private MainDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            checkIsThereAnyDatabase = conn.prepareStatement("SELECT * FROM *");
        } catch (SQLException e) {
            createDatabase();
        }
    }

    private void createDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("database.sql"));
            String sqlQuery = "";
            while (input.hasNext()) {
                sqlQuery += input.nextLine();
                if ( sqlQuery.length() > 1 && sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } // ...
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("There is no sql file... continuing wiht empty database");
        }
    }

    public static MainDAO getInstance() {
        if (instance == null)
            instance = new MainDAO();
        return instance;
    }

    public static void removeInstance() {
        try {
            instance.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instance = null;
    }

    public Connection getConn() {
        return conn;
    }
}
