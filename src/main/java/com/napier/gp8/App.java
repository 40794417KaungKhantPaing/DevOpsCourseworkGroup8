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

        //============================================================
        // REPORT 1. Country world report
        //============================================================
        CountriesWorldReport countriesWorld = new CountriesWorldReport();
        ArrayList<Country> countriesWorldList = countriesWorld.getCountries_World_Report(app.conn);
        countriesWorld.printCountries_World_Report(countriesWorldList);

        //============================================================
        // REPORT 2. Country Continent report
        //============================================================
        String countries_Continent = "Asia";
        CountriesContinentReport countriesContinentReport = new CountriesContinentReport();
        ArrayList<Country> countriesContinentList = countriesContinentReport.getCountries_Continent_Report(app.conn, countries_Continent);
        countriesContinentReport.printCountries_Continent_Report(countries_Continent, countriesContinentList);

        //============================================================
        // REPORT 3. Country Region report
        //============================================================
        String countries_Region = "Middle East";
        CountriesRegionReport countriesRegionReport = new CountriesRegionReport();
        ArrayList<Country> countriesRegionList = countriesRegionReport.getCountries_Region_Report(app.conn, countries_Region);
        countriesRegionReport.printCountries_Region_Report(countries_Region, countriesRegionList);

        //============================================================
        // REPORT 4: Top N Countries in the World
        //============================================================
        ArrayList<Country> topNCountries = countriesWorld.getTopNCountries_World_Report(app.conn,10);
        countriesWorld.printTopNCountries_World_Report(topNCountries,10);

        //============================================================
        // REPORT 5: Top N Countries in the Continent
        //============================================================
        ArrayList<Country> topNCountriesContinent = countriesContinentReport.getTopNCountries_Continent_Report(app.conn,countries_Continent,10);
        countriesContinentReport.printTopNCountries_Continent_Report(countries_Continent,topNCountriesContinent,10);

        //============================================================
        // REPORT 6: Top N Countries in the Region
        //============================================================
        ArrayList<Country> topNCountriesRegion = countriesRegionReport.getTopNCountries_Region_Report(app.conn,countries_Region,10);
        countriesRegionReport.printTopNCountries_Region_Report(countries_Region,topNCountriesRegion,10);


        // -------------------------------------------------------------------------

        //============================================================
        // REPORT 7: All Cities in the world
        //============================================================
        CitiesWorldReport citiesWorldReport = new CitiesWorldReport();
        ArrayList<City> citiesWorldList = citiesWorldReport.getCitiesWorldReport(app.conn);
        citiesWorldReport.printCitiesWorldReport(citiesWorldList);

        //============================================================
        // REPORT 8: All Cities in the continent
        //============================================================
        String cities_Continent = "Asia";
        CitiesContinentReport citiesContinentReport = new CitiesContinentReport();
        ArrayList<City> citiesContinentList = citiesContinentReport.getCitiesContinentReport(app.conn, cities_Continent);
        citiesContinentReport.printCitiesContinentReport(citiesContinentList, cities_Continent);


        //============================================================
        // REPORT 9: All Cities in the region
        //============================================================
        String cities_Region = "Eastern Asia";
        CitiesRegionReport citiesRegionReport = new CitiesRegionReport();
        ArrayList<City> citiesRegionList = citiesRegionReport.getCitiesRegionReport(app.conn, cities_Region);
        citiesRegionReport.printCitiesRegionReport(citiesRegionList, cities_Region);


        //============================================================
        // REPORT 10: All Cities in the country
        //============================================================
        String cities_Country = "Myanmar";
        CitiesCountryReport citiesCountryReport = new CitiesCountryReport();
        ArrayList<City> cityCountryList = citiesCountryReport.getCitiesCountryReport(app.conn, cities_Country);
        citiesCountryReport.printCitiesCountryReport(cityCountryList, cities_Country);


        //============================================================
        // REPORT 11: All Cities in the district
        //============================================================
        String cities_District = "Rio de Janeiro";
        CitiesDistrictReport citiesDistrictReport = new CitiesDistrictReport();
        ArrayList<City> cityDistrictList = citiesDistrictReport.getCitiesDistrictReport(app.conn, cities_District);
        citiesDistrictReport.printCitiesDistrictReport(cityDistrictList, cities_District);

        //============================================================
        // REPORT 12: Top N Cities in the world
        //============================================================
        int cities_World_N = 10;
        ArrayList<City> topNCitiesWorldList = citiesWorldReport.getTopNCitiesWorldReport(app.conn, cities_World_N);
        citiesWorldReport.printTopNCitiesWorldReport(topNCitiesWorldList, cities_World_N);

        //============================================================
        // REPORT 13: Top N Cities in the continent
        //============================================================
        int cities_Continent_N = 10;
        ArrayList<City> topNCitiesContinentList = citiesContinentReport.getTopNCitiesContinentReport(app.conn, cities_Continent, cities_Continent_N);
        citiesContinentReport.printTopNCitiesContinentReport(topNCitiesContinentList, cities_Continent, cities_Continent_N);

        //============================================================
        // REPORT 14: Top N Cities in the region
        //============================================================
        int cities_Region_N = 10;
        ArrayList<City> topNCitiesRegionList = citiesRegionReport.getTopNCitiesRegionReport(app.conn, cities_Region, cities_Region_N);
        citiesRegionReport.printTopNCitiesRegionReport(topNCitiesRegionList, cities_Region, cities_Region_N);

        //============================================================
        // REPORT 15: Top N Cities in the country
        //============================================================
        int cities_Country_N = 10;  // Number of top cities to display
        ArrayList<City> topNCitiesCountryList = citiesCountryReport.getTopNCitiesCountryReport(app.conn, cities_Country, cities_Country_N);
        citiesCountryReport.printTopNCitiesCountryReport(topNCitiesCountryList, cities_Country, cities_Country_N);

        //============================================================
        // REPORT 16: Top N Cities in the district
        //============================================================
        int cities_District_N = 10;  // Number of top cities to display
        ArrayList<City> topNCitiesDistrictList = citiesDistrictReport.getTopNCitiesDistrictReport(app.conn, cities_District, cities_District_N);
        citiesDistrictReport.printTopNCitiesDistrictReport(topNCitiesDistrictList, cities_District, cities_District_N);

        //============================================================
        // REPORT 17: Capital Cities in the World
        //============================================================
        // Create instance of CapitalCities_World class
        CapitalCitiesWorldReport capitalCitiesWorldReport = new CapitalCitiesWorldReport();

        // Get all capital cities in the world ordered by population (descending)
        ArrayList<City> capitalsWorldList = capitalCitiesWorldReport.getAllCapitalCitiesInWorldByPopulation(app.conn);

        // Print the capital cities in world report
        capitalCitiesWorldReport.printAllCapitalCitiesInWorldByPopulation(capitalsWorldList);

        //============================================================
        // REPORT 18: Capital Cities in a Continent
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
        // REPORT 19: Capital Cities in a Region
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
        // REPORT 20: Top N Capital Cities in the World
        //============================================================
        //Get top n capital cities in the world, ordered by population (Descending)
        ArrayList<City> topNCapitalsWorldList = capitalCitiesWorldReport.getTopNCapitalCitiesInWorldByPopulation(app.conn, 10);

        //print top n capital cities in the world
        capitalCitiesWorldReport.printTopNCapitalCitiesInWorldByPopulation(topNCapitalsWorldList,10);

        //============================================================
        // REPORT 21: Top N Capital Cities in a Continent
        //============================================================
        //Get top n capital cities within the specified continent, ordered by population (Descending)
        ArrayList<City> topNCapitalsContinentList = capitalCitiesContinentReport.getTopNCapitalCitiesInContinentByPopulation(app.conn,capitalCities_Continent,10);

        //Print top n capital cities in the specified continent report.
        capitalCitiesContinentReport.printTopNCapitalCitiesInContinentByPopulation(topNCapitalsContinentList,capitalCities_Continent,10);

        //============================================================
        // REPORT 22: Top N Capital Cities in a Region
        //============================================================
        // Retrieve all capital cities with the specified region, ordered by population (Descending)
        ArrayList<City> topNCapitalsRegionList = capitalCitiesRegionReport.getTopNCapitalCitiesInRegionByPopulation(app.conn, capitalCities_Region,10);

        // Print the capital cities in the specified region report
        capitalCitiesRegionReport.printTopNCapitalCitiesInRegionByPopulation(topNCapitalsRegionList, capitalCities_Region,10);

        //------------------------------------------

        //================================================================
        // REPORT 23: Population of a continent (live in/not live in city)
        //================================================================
        PopulationContinentReport continentReport = new PopulationContinentReport();
        List<Country> populations = continentReport.getPopulation_City_vs_NonCity_ByContinent(app.conn);
        // Print the report
        continentReport.printPopulation_City_vs_NonCity_ByContinent(populations);

        //================================================================
        // REPORT 24: Population of a region (live in/not live in city)
        //================================================================
        PopulationRegionReport regionReport = new PopulationRegionReport();
        List<Country> regions = regionReport.getPopulation_Region_Details_Report(app.conn);
        // Print the report
        regionReport.printPopulation_Region_Details_Report(regions);

        //================================================================
        // REPORT 25: Population of a country (live in/not live in city)
        //================================================================
        PopulationCountryReport countryReport = new PopulationCountryReport();
        List<Country> countryPopulations = countryReport.getPopulation_City_vs_NonCity_ByCountry(app.conn);
        // Print the report
        countryReport.printPopulation_City_vs_NonCity_ByCountry(countryPopulations);

        //========================================
        // REPORT 26: Population of the world
        //========================================
        PopulationWorldReport worldReport = new PopulationWorldReport();
        PopulationWorldReport.PopulationData worldPop = worldReport.getPopulation_World_Report(app.conn);
        // Print the report
        worldReport.printPopulation_World_Report(worldPop);

        //========================================
        // REPORT 27: Population of the continent
        //========================================
        String continentVariable = "Asia"; // Replace with any continent you want
        PopulationContinentReport populationContinentReport = new PopulationContinentReport();
        List<Country> populationContinentList = populationContinentReport.getPopulation_Continent_Report(app.conn);
        // Filter the list to only include the chosen continent
        List<Country> filteredList = populationContinentList.stream()
                .filter(c -> c.getContinent().equalsIgnoreCase(continentVariable))
                .toList(); // Java 16+, or use Collectors.toList() for older versions
        // Print the report for that continent
        populationContinentReport.printPopulation_Continent_Report(filteredList);

        //========================================
        // REPORT 28: Population of the region
        //========================================
        String regionVariable = "Southern Europe"; // Replace with any region you want
        PopulationRegionReport populationRegionReport = new PopulationRegionReport();
        List<Country> populationRegionList = populationRegionReport.getPopulation_Region_Report(app.conn);
        // Print the report for the selected region only
        populationRegionReport.printPopulation_Region_Report(populationRegionList, regionVariable);


        //========================================
        // REPORT 29: Population of the country
        //========================================
        String countryVariable = "Japan"; // Replace with the country you want
        PopulationCountryReport populationCountryReport = new PopulationCountryReport();
        List<Country> populationCountryList = populationCountryReport.getPopulation_Country_Report(app.conn);
        // Print the report for the selected country only
        populationCountryReport.printPopulation_Country_Report(populationCountryList, countryVariable);

        //========================================
        // REPORT 30: Population of the district
        //========================================
        String districtVariable = "California"; // Replace with your desired district
        PopulationDistrictReport populationDistrictReport = new PopulationDistrictReport();
        List<Country> populationDistrictList = populationDistrictReport.getPopulation_District_Report(app.conn);
        // Print the report for the selected district including percentages
        populationDistrictReport.printPopulation_District_Report(populationDistrictList, districtVariable);

        //========================================
        // REPORT 31: Population of the city
        //========================================
        String cityVariable = "Tokyo";
        PopulationCityReport cityReport = new PopulationCityReport();
        List<City> cityList = cityReport.getPopulation_City_Report(app.conn);
        // Print a specific city
        cityReport.printPopulation_City_Report(cityList,cityVariable);

        //============================================================
        // REPORT 32: Language Population
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

        //disconnect from database
        app.disconnect();
    }

}