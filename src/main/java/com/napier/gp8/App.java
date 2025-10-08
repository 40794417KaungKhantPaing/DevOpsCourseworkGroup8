package com.napier.gp8;

import java.sql.*;
import java.util.List;

public class App {

    /* Connect object used to connect to the MySQL database.
     * Initialized as null and set once a successful connection is made.
            */
    private Connection conn = null;

    /* Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Database connection info
        String url = "jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "root";

        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }


    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (conn != null)
        {
            try
            {
                // Close connection
                conn.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public static void main(String[] args) {
        App a = new App();
        a.connect();

        // Retrieve country world report
        Countries_World countriesWorld = new Countries_World();
        List<Country> countries = countriesWorld.getCountries_World_Report(a.conn);
        countriesWorld.printCountries_World_Report(countries);

        // Retrieve country continent report
        String[] continents = {"Asia"};
        Countries_Continent countriesContinentReport = new Countries_Continent();
        for (String continent : continents) {
            List<Country> countriesContinent = countriesContinentReport.getCountries_Continent_Report(a.conn, continent);
            countriesContinentReport.printCountries_Continent_Report(continent,countriesContinent);
        }
        // Retrieve country region report
        String[] regions = {"Middle East"};
        Countries_Region countriesRegionReport = new Countries_Region();
        for (String region : regions) {
            List<Country> countriesRegion = countriesRegionReport.getCountries_Region_Report(a.conn, region);
            countriesRegionReport.printCountries_Region_Report(region,countriesRegion);
        }
        // Retrieve city world report
        Cities_World_Report citiesWorldReport = new Cities_World_Report();
        List<City> citiesWorld = citiesWorldReport.getCitiesWorldReport(a.conn);
        citiesWorldReport.printCitiesWorldReport(citiesWorld);
        // Retrieve city continent report
        String[] continents1 = {"Asia"};

        Cities_Continent_Report citiesContinentReport = new Cities_Continent_Report();
        for (String continent : continents1) {
            List<City> citiesContinent = citiesContinentReport.getCities_By_Continent_Report(a.conn, continent);
            citiesContinentReport.printCities_By_Continent_Report(citiesContinent, continent);
        }

        // Retrieve city region report
        String[] regions1 = {"Caribbean"};
        Cities_Region_Report citiesRegionReport = new Cities_Region_Report();
        for (String region : regions1) {
            List<City> citiesRegion = citiesRegionReport.getCitiesRegionReport(a.conn, region);
            citiesRegionReport.printCitiesRegionReport(citiesRegion, region);
        }



        a.disconnect();
    }
}
