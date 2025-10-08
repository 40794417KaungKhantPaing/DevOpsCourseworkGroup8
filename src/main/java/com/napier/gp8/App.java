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
        App app = new App();
        app.connect();

        // Retrieve country world report
        Countries_World countriesWorld = new Countries_World();
        List<Country> countriesWorldList = countriesWorld.getCountries_World_Report(app.conn);
        countriesWorld.printCountries_World_Report(countriesWorldList);

        // Retrieve country continent report
        String countries_Continent = "Asia";
        Countries_Continent countriesContinentReport = new Countries_Continent();
        List<Country> countriesContinentList = countriesContinentReport.getCountries_Continent_Report(app.conn, countries_Continent);
        countriesContinentReport.printCountries_Continent_Report(countries_Continent, countriesContinentList);

        // Retrieve country region report
        String countries_Region = "Middle East";
        Countries_Region countriesRegionReport = new Countries_Region();
        List<Country> countriesRegionList = countriesRegionReport.getCountries_Region_Report(app.conn, countries_Region);
        countriesRegionReport.printCountries_Region_Report(countries_Region, countriesRegionList);

        // Retrieve city world report
        Cities_World_Report citiesWorldReport = new Cities_World_Report();
        List<City> citiesWorldList = citiesWorldReport.getCitiesWorldReport(app.conn);
        citiesWorldReport.printCitiesWorldReport(citiesWorldList);

        // Retrieve city continent report
        String cities_Continent = "Asia";
        Cities_Continent_Report citiesContinentReport = new Cities_Continent_Report();
        List<City> citiesContinentList = citiesContinentReport.getCities_By_Continent_Report(app.conn, cities_Continent);
        citiesContinentReport.printCities_By_Continent_Report(citiesContinentList, cities_Continent);

        // Retrieve city region report
        String cities_Region = "Caribbean";
        Cities_Region_Report citiesRegionReport = new Cities_Region_Report();
        List<City> citiesRegionList = citiesRegionReport.getCitiesRegionReport(app.conn, cities_Region);
        citiesRegionReport.printCitiesRegionReport(citiesRegionList, cities_Region);

        //============================================================
        // REPORT: Capital Cities in the World
        //============================================================

        // Create instance of CapitalCities_World class
        CapitalCities_World capitalCitiesWorldReport = new CapitalCities_World();

        // Get all capital cities in the world ordered by population (descending)
        ArrayList<City> capitalsWorldList = capitalCitiesWorldReport.getAllCapitalCitiesInWorldByPopulation(app.conn);

        // Print the capital cities in world report
        capitalCitiesWorldReport.printAllCapitalCitiesInWorldByPopulation(capitalsWorldList);


        //============================================================
        // REPORT: Capital Cities in a Continent
        //============================================================

        // Define the continent for which the report will be generated.
        String capitalCities_Continent = "Asia";

        // Create instance to generate the continent level report
        CapitalCities_Continent capitalCitiesContinentReport = new CapitalCities_Continent();

        // Get all capital cities within the specified continent, ordered by population (Descending)
        ArrayList<City> capitalsContinentList = capitalCitiesContinentReport.getCapitalCitiesInContinentByPopulation(app.conn, capitalCities_Continent);

        // Print the capital cities in the specified continent report.
        capitalCitiesContinentReport.printCapitalCitiesInContinentByPopulation(capitalsContinentList, capitalCities_Continent);


        //============================================================
        // REPORT: Capital Cities in a Region
        //============================================================

        // Define the region for which the report will be generated.
        String capitalCities_Region = "Middle East";

        // Create instance to generate the region-level report.
        CapitalCities_Region capitalCitiesRegionReport = new CapitalCities_Region();

        // Retrieve all capital cities with the specified region, ordered by population (Descending)
        ArrayList<City> capitalsRegionList = capitalCitiesRegionReport.getCapitalCitiesInRegionByPopulation(app.conn, capitalCities_Region);

        // Print the capital cities in the specified region report
        capitalCitiesRegionReport.printCapitalCitiesInRegionByPopulation(capitalsRegionList, capitalCities_Region);


        //------------------------------------------

        // Retrieve population data using the Population_Continent_Report class
        Population_Continent_Report populationContinentReport = new Population_Continent_Report();
        List<Country> populationContinentList = populationContinentReport.getPopulation_Continent_Report(app.conn);
        // Print the report
        populationContinentReport.printPopulation_Continent_Report(populationContinentList);

        // Retrieve population data using the Population_Region_Report class
        Population_Region_Report populationRegionReport = new Population_Region_Report();
        List<Country> populationRegionList = populationRegionReport.getPopulation_Region_Report(app.conn);
        // Print the report
        populationRegionReport.printPopulation_Region_Report(populationRegionList);

        // Retrieve population data using the Population_Country_Report class
        Population_Country_Report populationCountryReport = new Population_Country_Report();
        List<Country> populationCountryList = populationCountryReport.getPopulation_Country_Report(app.conn);
        // Print the report
        populationCountryReport.printPopulation_Country_Report(populationCountryList);


        //============================================================
        // REPORT: Language Population
        //============================================================

        // Create instance of language_population class
        Language_Population languageReport = new Language_Population();

        // Declare arraylist to hold the data
        ArrayList<String> languageNames = new ArrayList<>();
        ArrayList<Long> languageSpeakers = new ArrayList<>();
        ArrayList<Double> languagePercentages = new ArrayList<>();

        // Get language population data from database
        languageReport.getLanguagePopulationReport(app.conn, languageNames, languageSpeakers, languagePercentages);

        // Print the population language report
        languageReport.printLanguagePopulationReport(languageNames, languageSpeakers, languagePercentages);

        app.disconnect();
    }

}