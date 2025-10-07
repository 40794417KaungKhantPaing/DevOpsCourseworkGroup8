package com.napier.gp8;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class App {

    /* Connect object used to connect to the MySQL database.
     * Initialized as null and set once a successful connection is made.
     */
    private Connection conn = null;

    /* Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Database connection info
        String url = "jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "root";

        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }


    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (conn != null) {
            try {
                // Close connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public static void main(String[] args) {
        App a = new App();
        a.connect();
        // Retrieve city data using the CityReport class
        Cities_World_Report citiesWorldReport = new Cities_World_Report();
        List<City> citiesWorld = citiesWorldReport.getCitiesWorldReport(a.conn);
        // Print the report
        citiesWorldReport.printCitiesWorldReport(citiesWorld);
        String[] continents = {"Asia"};

        Cities_Continent_Report citiesContinentReport = new Cities_Continent_Report();

        for (String continent : continents) {
            List<City> citiesContinent = citiesContinentReport.getCities_By_Continent_Report(a.conn, continent);
            citiesContinentReport.printCities_By_Continent_Report(citiesContinent, continent);
        }

        a.disconnect();
    }
}
