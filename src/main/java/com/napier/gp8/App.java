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
        List<Country> countriesWorldList = countriesWorld.getCountries_World_Report(app.conn);
        countriesWorld.printCountries_World_Report(countriesWorldList);

        // Retrieve country continent report
        String countries_Continent = "Asia";
        CountriesContinentReport countriesContinentReport = new CountriesContinentReport();
        List<Country> countriesContinentList = countriesContinentReport.getCountries_Continent_Report(app.conn, countries_Continent);
        countriesContinentReport.printCountries_Continent_Report(countries_Continent, countriesContinentList);

        // Retrieve country region report
        String countries_Region = "Middle East";
        CountriesRegionReport countriesRegionReport = new CountriesRegionReport();
        List<Country> countriesRegionList = countriesRegionReport.getCountries_Region_Report(app.conn, countries_Region);
        countriesRegionReport.printCountries_Region_Report(countries_Region, countriesRegionList);

        // Retrieve city world report
        CitiesWorldReport citiesWorldReport = new CitiesWorldReport();
        List<City> citiesWorldList = citiesWorldReport.getCitiesWorldReport(app.conn);
        citiesWorldReport.printCitiesWorldReport(citiesWorldList);

        // Retrieve city continent report
        String cities_Continent = "Asia";
        CitiesContinentReport citiesContinentReport = new CitiesContinentReport();
        List<City> citiesContinentList = citiesContinentReport.getCities_By_Continent_Report(app.conn, cities_Continent);
        citiesContinentReport.printCities_By_Continent_Report(citiesContinentList, cities_Continent);

        // Retrieve city region report
        String cities_Region = "Caribbean";
        CitiesRegionReport citiesRegionReport = new CitiesRegionReport();
        List<City> citiesRegionList = citiesRegionReport.getCitiesRegionReport(app.conn, cities_Region);
        citiesRegionReport.printCitiesRegionReport(citiesRegionList, cities_Region);

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
        ArrayList<City> capitalsContinentList = capitalCitiesContinentReport.getCapitalCitiesInContinentByPopulation(app.conn, capitalCities_Continent);

        // Print the capital cities in the specified continent report.
        capitalCitiesContinentReport.printCapitalCitiesInContinentByPopulation(capitalsContinentList, capitalCities_Continent);


        //============================================================
        // REPORT: Capital Cities in a Region
        //============================================================

        // Define the region for which the report will be generated.
        String capitalCities_Region = "Middle East";

        // Create instance to generate the region-level report.
        CapitalCitiesRegionReport capitalCitiesRegionReport = new CapitalCitiesRegionReport();

        // Retrieve all capital cities with the specified region, ordered by population (Descending)
        ArrayList<City> capitalsRegionList = capitalCitiesRegionReport.getCapitalCitiesInRegionByPopulation(app.conn, capitalCities_Region);

        // Print the capital cities in the specified region report
        capitalCitiesRegionReport.printCapitalCitiesInRegionByPopulation(capitalsRegionList, capitalCities_Region);

        //------------------------------------------


        // 23 Retrieve population data using the PopulationContinentReport class
        PopulationContinentReport continentReport = new PopulationContinentReport();
        List<Country> populations = continentReport.getPopulation_City_vs_NonCity_ByContinent(app.conn);
        // Print the report
        continentReport.printPopulation_City_vs_NonCity_ByContinent(populations);

        //24 Retrieve and print population data by region
        PopulationRegionReport regionReport = new PopulationRegionReport();
        List<Country> regions = regionReport.getPopulation_Region_Details_Report(app.conn);
        // Print the report
        regionReport.printPopulation_Region_Details_Report(regions);

        // 25 Retrieve and print population data by country (city vs non-city)
        PopulationCountryReport countryReport = new PopulationCountryReport();
        List<Country> countryPopulations = countryReport.getPopulation_City_vs_NonCity_ByCountry(app.conn);
        // Print the report
        countryReport.printPopulation_City_vs_NonCity_ByCountry(countryPopulations);

        //26 Retrieve population data using the PopulationWorldReport class
        PopulationWorldReport worldReport = new PopulationWorldReport();
        PopulationWorldReport.PopulationData worldPop = worldReport.getPopulation_World_Report(app.conn);
        // Print the report
        worldReport.printPopulation_World_Report(worldPop);

        // 27. Retrieve population data for a specific continent
        String continentVariable = "Asia"; // Replace with any continent you want
        PopulationContinentReport populationContinentReport = new PopulationContinentReport();
        List<Country> populationContinentList = populationContinentReport.getPopulation_Continent_Report(app.conn);
        // Filter the list to only include the chosen continent
        List<Country> filteredList = populationContinentList.stream()
                .filter(c -> c.getContinent().equalsIgnoreCase(continentVariable))
                .toList(); // Java 16+, or use Collectors.toList() for older versions
        // Print the report for that continent
        populationContinentReport.printPopulation_Continent_Report(filteredList);

        // 28. Retrieve population data for a specific region
        String regionVariable = "Southern Europe"; // Replace with any region you want
        PopulationRegionReport populationRegionReport = new PopulationRegionReport();
        List<Country> populationRegionList = populationRegionReport.getPopulation_Region_Report(app.conn);
        // Print the report for the selected region only
        populationRegionReport.printPopulation_Region_Report(populationRegionList, regionVariable);


        // 29. Retrieve population data for a specific country
        String countryVariable = "Japan"; // Replace with the country you want
        PopulationCountryReport populationCountryReport = new PopulationCountryReport();
        List<Country> populationCountryList = populationCountryReport.getPopulation_Country_Report(app.conn);
        // Print the report for the selected country only
        populationCountryReport.printPopulation_Country_Report(populationCountryList, countryVariable);


        // 30. Retrieve population data for a specific district
        String districtVariable = "California"; // Replace with your desired district
        PopulationDistrictReport populationDistrictReport = new PopulationDistrictReport();
        List<Country> populationDistrictList = populationDistrictReport.getPopulation_District_Report(app.conn);
        // Print the report for the selected district including percentages
        populationDistrictReport.printPopulation_District_Report(populationDistrictList, districtVariable);


        //31 Retrieve population data using the PopulationCityReport class
        String cityVariable = "Tokyo";
        PopulationCityReport cityReport = new PopulationCityReport();
        List<City> cityList = cityReport.getPopulation_City_Report(app.conn);
        // Print a specific city
        cityReport.printPopulation_City_Report(cityList,cityVariable);

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