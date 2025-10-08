package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
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

        // Retrieve population data using the Population_Continent_Report class
        Population_Continent_Report populationContinentReport = new Population_Continent_Report();
        List<Country> Continentcountries = populationContinentReport.getPopulation_Continent_Report(a.conn);
        // Print the report
        populationContinentReport.printPopulation_Continent_Report(Continentcountries);

        // Retrieve population data using the Population_Region_Report class
        Population_Region_Report populationRegionReport = new Population_Region_Report();
        List<Country> regionCountries = populationRegionReport.getPopulation_Region_Report(a.conn);
        // Print the report
        populationRegionReport.printPopulation_Region_Report(regionCountries);

        // Retrieve population data using the Population_Country_Report class
        Population_Country_Report populationCountryReport = new Population_Country_Report();
        List<Country> countryList = populationCountryReport.getPopulation_Country_Report(a.conn);
        // Print the report
        populationCountryReport.printPopulation_Country_Report(countryList);

        a.disconnect();
    }
}