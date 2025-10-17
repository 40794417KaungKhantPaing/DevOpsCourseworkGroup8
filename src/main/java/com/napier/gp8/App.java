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
        CountriesWorldReport countriesWorld = new CountriesWorldReport();
        ArrayList<Country> countriesWorldList = countriesWorld.getCountries_World_Report(app.conn);
        countriesWorld.printCountries_World_Report(countriesWorldList);

        // Retrieve country continent report
        String countries_Continent = "Asia";
        CountriesContinentReport countriesContinentReport = new CountriesContinentReport();
        ArrayList<Country> countriesContinentList = countriesContinentReport.getCountries_Continent_Report(app.conn, countries_Continent);
        countriesContinentReport.printCountries_Continent_Report(countries_Continent, countriesContinentList);

        // Retrieve country region report
        String countries_Region = "Middle East";
        CountriesRegionReport countriesRegionReport = new CountriesRegionReport();
        ArrayList<Country> countriesRegionList = countriesRegionReport.getCountries_Region_Report(app.conn, countries_Region);
        countriesRegionReport.printCountries_Region_Report(countries_Region, countriesRegionList);

        //Retrieve top n countries report
        ArrayList<Country> topNCountries = countriesWorld.getTopNCountries_World_Report(app.conn,10);
        countriesWorld.printTopNCountries_World_Report(topNCountries,10);

        //Retrieve top n countries in the continent report
        ArrayList<Country> topNCountriesContinent = countriesContinentReport.getTopNCountries_Continent_Report(app.conn,countries_Continent,10);
        countriesContinentReport.printTopNCountries_Continent_Report(countries_Continent,topNCountriesContinent,10);

        //Retrieve top n countries in the region report
        ArrayList<Country> topNCountriesRegion = countriesRegionReport.getTopNCountries_Region_Report(app.conn,countries_Region,10);
        countriesRegionReport.printTopNCountries_Region_Report(countries_Region,topNCountriesRegion,10);


        // -------------------------------------------------------------------------

        CitiesWorldReport citiesWorldReport = new CitiesWorldReport();
        // All cities in world
        ArrayList<City> citiesWorldList = citiesWorldReport.getCitiesWorldReport(app.conn);
        citiesWorldReport.printCitiesWorldReport(citiesWorldList);

        // Retrieve city continent report
        String cities_Continent = "Asia";
        CitiesContinentReport citiesContinentReport = new CitiesContinentReport();
        ArrayList<City> citiesContinentList = citiesContinentReport.getCitiesContinentReport(app.conn, cities_Continent);
        citiesContinentReport.printCitiesContinentReport(citiesContinentList, cities_Continent);


        // Retrieve city region report
        String cities_Region = "Eastern Asia";
        CitiesRegionReport citiesRegionReport = new CitiesRegionReport();
        ArrayList<City> citiesRegionList = citiesRegionReport.getCitiesRegionReport(app.conn, cities_Region);
        citiesRegionReport.printCitiesRegionReport(citiesRegionList, cities_Region);


        // Retrieve city country report
        String cities_Country = "Myanmar";
        CitiesCountryReport citiesCountryReport = new CitiesCountryReport();
        ArrayList<City> cityCountryList = citiesCountryReport.getCitiesCountryReport(app.conn, cities_Country);
        citiesCountryReport.printCitiesCountryReport(cityCountryList, cities_Country);


        // Retrieve city district report
        String cities_District = "Rio de Janeiro";
        CitiesDistrictReport citiesDistrictReport = new CitiesDistrictReport();
        ArrayList<City> cityDistrictList = citiesDistrictReport.getCitiesDistrictReport(app.conn, cities_District);
        citiesDistrictReport.printCitiesDistrictReport(cityDistrictList, cities_District);

        // Top N cities in world
        int cities_World_N = 10;
        ArrayList<City> topNCitiesWorldList = citiesWorldReport.getTopNCitiesWorldReport(app.conn, cities_World_N);
        citiesWorldReport.printTopNCitiesWorldReport(topNCitiesWorldList, cities_World_N);

        // Retrieve top N populated cities in a continent report
        int cities_Continent_N = 10;
        ArrayList<City> topNCitiesContinentList = citiesContinentReport.getTopNCitiesContinentReport(app.conn, cities_Continent, cities_Continent_N);
        citiesContinentReport.printTopNCitiesContinentReport(topNCitiesContinentList, cities_Continent, cities_Continent_N);

        // Retrieve top N populated cities in a region report
        int cities_Region_N = 10;
        ArrayList<City> topNCitiesRegionList = citiesRegionReport.getTopNCitiesRegionReport(app.conn, cities_Region, cities_Region_N);
        citiesRegionReport.printTopNCitiesRegionReport(topNCitiesRegionList, cities_Region, cities_Region_N);

        // Retrieve top N populated cities in a country report
        int cities_Country_N = 10;  // Number of top cities to display
        ArrayList<City> topNCitiesCountryList = citiesCountryReport.getTopNCitiesCountryReport(app.conn, cities_Country, cities_Country_N);
        citiesCountryReport.printTopNCitiesCountryReport(topNCitiesCountryList, cities_Country, cities_Country_N);

        // Retrieve top N populated cities in a district report
        int cities_District_N = 10;  // Number of top cities to display
        ArrayList<City> topNCitiesDistrictList = citiesDistrictReport.getTopNCitiesDistrictReport(app.conn, cities_District, cities_District_N);
        citiesDistrictReport.printTopNCitiesDistrictReport(topNCitiesDistrictList, cities_District, cities_District_N);

        //============================================================
        // REPORT: Capital Cities in the World
        //============================================================

        // Create instance of CapitalCities_World class
        CapitalCitiesWorldReport capitalCitiesWorldReport = new CapitalCitiesWorldReport();

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
        CapitalCitiesContinentReport capitalCitiesContinentReport = new CapitalCitiesContinentReport();

        // Get all capital cities within the specified continent, ordered by population (Descending)
        ArrayList<City> capitalsContinentList = capitalCitiesContinentReport.getAllCapitalCitiesInContinentByPopulation(app.conn, capitalCities_Continent);

        // Print the capital cities in the specified continent report.
        capitalCitiesContinentReport.printAllCapitalCitiesInContinentByPopulation(capitalsContinentList,capitalCities_Continent);


        //============================================================
        // REPORT: Capital Cities in a Region
        //============================================================

        // Define the region for which the report will be generated.
        String capitalCities_Region = "Middle East";

        // Create instance to generate the region-level report.
        CapitalCitiesRegionReport capitalCitiesRegionReport = new CapitalCitiesRegionReport();

        // Retrieve all capital cities with the specified region, ordered by population (Descending)
        ArrayList<City> capitalsRegionList = capitalCitiesRegionReport.getAllCapitalCitiesInRegionByPopulation(app.conn, capitalCities_Region);

        // Print the capital cities in the specified region report
        capitalCitiesRegionReport.printAllCapitalCitiesInRegionByPopulation(capitalsRegionList, capitalCities_Region);


        //============================================================
        // REPORT: Top N Capital Cities in the World
        //============================================================

        //Get top n capital cities in the world, ordered by population (Descending)
        ArrayList<City> topNCapitalsWorldList = capitalCitiesWorldReport.getTopNCapitalCitiesInWorldByPopulation(app.conn, 10);

        //print top n capital cities in the world
        capitalCitiesWorldReport.printTopNCapitalCitiesInWorldByPopulation(topNCapitalsWorldList,10);

        //============================================================
        // REPORT: Top N Capital Cities in a Continent
        //============================================================

        //Get top n capital cities within the specified continent, ordered by population (Descending)
        ArrayList<City> topNCapitalsContinentList = capitalCitiesContinentReport.getTopNCapitalCitiesInContinentByPopulation(app.conn,capitalCities_Continent,10);

        //Print top n capital cities in the specified continent report.
        capitalCitiesContinentReport.printTopNCapitalCitiesInContinentByPopulation(topNCapitalsContinentList,capitalCities_Continent,10);

        //============================================================
        // REPORT: Top N Capital Cities in a Region
        //============================================================

        // Retrieve all capital cities with the specified region, ordered by population (Descending)
        ArrayList<City> topNCapitalsRegionList = capitalCitiesRegionReport.getTopNCapitalCitiesInRegionByPopulation(app.conn, capitalCities_Region,10);

        // Print the capital cities in the specified region report
        capitalCitiesRegionReport.printTopNCapitalCitiesInRegionByPopulation(topNCapitalsRegionList, capitalCities_Region,10);

        //------------------------------------------

        // Retrieve population data using the Population_Continent_Report class
        PopulationContinentReport populationContinentReport = new PopulationContinentReport();
        List<Country> populationContinentList = populationContinentReport.getPopulation_Continent_Report(app.conn);
        // Print the report
        populationContinentReport.printPopulation_Continent_Report(populationContinentList);

        // Retrieve population data using the Population_Region_Report class
        PopulationRegionReport populationRegionReport = new PopulationRegionReport();
        List<Country> populationRegionList = populationRegionReport.getPopulation_Region_Report(app.conn);
        // Print the report
        populationRegionReport.printPopulation_Region_Report(populationRegionList);

        // Retrieve population data using the Population_Country_Report class
        PopulationCountryReport populationCountryReport = new PopulationCountryReport();
        List<Country> populationCountryList = populationCountryReport.getPopulation_Country_Report(app.conn);
        // Print the report
        populationCountryReport.printPopulation_Country_Report(populationCountryList);


        //============================================================
        // REPORT: Language Population
        //============================================================

        // Create instance of language_population class
        LanguagePopulationReport languageReport = new LanguagePopulationReport();

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