package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

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
        //create an instance of the main application class to manage database connection
        App a = new App();

        //Establish connection to the database
        a.connect();


        //============================================================
        //REPORT: Capital Cities in the World
        //============================================================

        //Create instance of CapitalCities_World class
        CapitalCities_World capitalCitiesWorldReport = new CapitalCities_World();

        //Get all capital cities in the world ordered by population (descending)
        ArrayList<City> capitalsWorld = capitalCitiesWorldReport.getAllCapitalCitiesInWorldByPopulation(a.conn);

        //print the capital cities in world report
        capitalCitiesWorldReport.printAllCapitalCitiesInWorldByPopulation(capitalsWorld);



        //============================================================
        //REPORT: Capital Cities in a Continent
        //============================================================

        //Define the continent for which the report will be generated.
        String continent = "Asia";

        //Create instance to generate the continent level report
        CapitalCities_Continent capitalCitiesContinentReport = new CapitalCities_Continent();

        //Get all capital cities within the specified continent, ordered by population (Descending)
        ArrayList<City> capitalsContinent = capitalCitiesContinentReport.getCapitalCitiesInContinentByPopulation(a.conn, continent);

        //print the capital cities in the specified continent report.
        capitalCitiesContinentReport.printCapitalCitiesInContinentByPopulation(capitalsContinent, continent);


        //============================================================
        //REPORT: Capital Cities in a Region
        //============================================================

        //Define the region for which the report will be generated.
        String region ="Middle East";

        //Create instance to generate the region-level report.
        CapitalCities_Region capitalCitiesRegionReport = new CapitalCities_Region();

        //Retrieve all capital cities with the specified region, ordered by population (Descending)
        ArrayList<City> capitalsRegion = capitalCitiesRegionReport.getCapitalCitiesInRegionByPopulation(a.conn,region);

        //print the capital cities in the specified region report
        capitalCitiesRegionReport.printCapitalCitiesInRegionByPopulation(capitalsRegion, region);

        //------------------------------------------

        //disconnect from database after generating all reports.
        a.disconnect();
    }
}
