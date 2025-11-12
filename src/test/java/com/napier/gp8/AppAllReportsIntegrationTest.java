package com.napier.gp8;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Integration-style test that runs the full sequence of reports (1..32)
 * exactly as in your main() method.
 *
 * IMPORTANT:
 * - This test connects to your real database using App.connect(String, int).
 * - Update the location and delay in setup() if needed to match your environment.
 * - The test will create the same markdown output files as your main() flow.
 */
public class AppAllReportsIntegrationTest {

    private static App app;
    private static ReportWriter writer;

    @BeforeAll
    public static void setup() {
        app = new App();
        writer = new ReportWriter();

        // Use same defaults as your main() when no args provided.
        // Adjust the location ("localhost:33060") and delay (30000) if your DB runs elsewhere.
        app.connect("localhost:33060", 30000);

        Assertions.assertNotNull(app.getConnection(), "Failed to establish DB connection in setup()");
    }

    @AfterAll
    public static void teardown() {
        if (app != null) {
            app.disconnect();
        }
    }

    @Test
    public void runAllReportsSequentially() {
        // We'll fail the test immediately if any unexpected exception occurs.
        try {
            //============================================================
            // REPORT 1. Country world report
            //============================================================
            CountriesWorldReport countriesWorld = new CountriesWorldReport();
            ArrayList<Country> countriesWorldList = countriesWorld.getCountries_World_Report(app.getConnection());
            countriesWorld.printCountries_World_Report(countriesWorldList);
            writer.outputCountries(countriesWorldList, "01_Countries_World_Report.md", "Report 1: Countries in the World");

            //============================================================
            // REPORT 2. Country Continent report
            //============================================================
            String countries_Continent = "Asia";
            CountriesContinentReport countriesContinentReport = new CountriesContinentReport();
            ArrayList<Country> countriesContinentList = countriesContinentReport.getCountries_Continent_Report(app.getConnection(), countries_Continent);
            countriesContinentReport.printCountries_Continent_Report(countries_Continent, countriesContinentList);
            writer.outputCountries(countriesContinentList, "02_Countries_Continent_Report.md", "Report 2: Countries in " + countries_Continent);

            //============================================================
            // REPORT 3. Country Region report
            //============================================================
            String countries_Region = "Middle East";
            CountriesRegionReport countriesRegionReport = new CountriesRegionReport();
            ArrayList<Country> countriesRegionList = countriesRegionReport.getCountries_Region_Report(app.getConnection(), countries_Region);
            countriesRegionReport.printCountries_Region_Report(countries_Region, countriesRegionList);
            writer.outputCountries(countriesRegionList, "03_Countries_Region_Report.md", "Report 3: Countries in " + countries_Region);

            //============================================================
            // REPORT 4: Top N Countries in the World
            //============================================================
            ArrayList<Country> topNCountries = countriesWorld.getTopNCountries_World_Report(app.getConnection(),10);
            countriesWorld.printTopNCountries_World_Report(topNCountries,10);
            writer.outputCountries(topNCountries, "04_TopN_Countries_World_Report.md", "Report 4: Top 10 Countries in the World");

            //============================================================
            // REPORT 5: Top N Countries in the Continent
            //============================================================
            ArrayList<Country> topNCountriesContinent = countriesContinentReport.getTopNCountries_Continent_Report(app.getConnection(),countries_Continent,10);
            countriesContinentReport.printTopNCountries_Continent_Report(countries_Continent,topNCountriesContinent,10);
            writer.outputCountries(topNCountriesContinent, "05_TopN_Countries_Continent_Report.md", "Report 5: Top 10 Countries in " + countries_Continent);

            //============================================================
            // REPORT 6: Top N Countries in the Region
            //============================================================
            ArrayList<Country> topNCountriesRegion = countriesRegionReport.getTopNCountries_Region_Report(app.getConnection(),countries_Region,10);
            countriesRegionReport.printTopNCountries_Region_Report(countries_Region,topNCountriesRegion,10);
            writer.outputCountries(topNCountriesRegion, "06_TopN_Countries_Region_Report.md", "Report 6: Top 10 Countries in " + countries_Region);

            // -------------------------------------------------------------------------

            //============================================================
            // REPORT 7: All Cities in the world
            //============================================================
            CitiesWorldReport citiesWorldReport = new CitiesWorldReport();
            ArrayList<City> citiesWorldList = citiesWorldReport.getCitiesWorldReport(app.getConnection());
            citiesWorldReport.printCitiesWorldReport(citiesWorldList);
            writer.outputCities(citiesWorldList, "07_Cities_World_Report.md", "Report 7: Cities in the World");

            //============================================================
            // REPORT 8: All Cities in the continent
            //============================================================
            String cities_Continent = "Asia";
            CitiesContinentReport citiesContinentReport = new CitiesContinentReport();
            ArrayList<City> citiesContinentList = citiesContinentReport.getCitiesContinentReport(app.getConnection(), cities_Continent);
            citiesContinentReport.printCitiesContinentReport(citiesContinentList, cities_Continent);
            writer.outputCities(citiesContinentList, "08_Cities_Continent_Report.md", "Report 8: Cities in " + cities_Continent);

            //============================================================
            // REPORT 9: All Cities in the region
            //============================================================
            String cities_Region = "Eastern Asia";
            CitiesRegionReport citiesRegionReport = new CitiesRegionReport();
            ArrayList<City> citiesRegionList = citiesRegionReport.getCitiesRegionReport(app.getConnection(), cities_Region);
            citiesRegionReport.printCitiesRegionReport(citiesRegionList, cities_Region);
            writer.outputCities(citiesRegionList, "09_Cities_Region_Report.md", "Report 9: Cities in " + cities_Region);

            //============================================================
            // REPORT 10: All Cities in the country
            //============================================================
            String cities_Country = "Myanmar";
            CitiesCountryReport citiesCountryReport = new CitiesCountryReport();
            ArrayList<City> cityCountryList = citiesCountryReport.getCitiesCountryReport(app.getConnection(), cities_Country);
            citiesCountryReport.printCitiesCountryReport(cityCountryList, cities_Country);
            writer.outputCities(cityCountryList, "10_Cities_Country_Report.md", "Report 10: Cities in " + cities_Country);

            //============================================================
            // REPORT 11: All Cities in the district
            //============================================================
            String cities_District = "Rio de Janeiro";
            CitiesDistrictReport citiesDistrictReport = new CitiesDistrictReport();
            ArrayList<City> cityDistrictList = citiesDistrictReport.getCitiesDistrictReport(app.getConnection(), cities_District);
            citiesDistrictReport.printCitiesDistrictReport(cityDistrictList, cities_District);
            writer.outputCities(cityDistrictList, "11_Cities_District_Report.md", "Report 11: Cities in " + cities_District);

            //============================================================
            // REPORT 12: Top N Cities in the world
            //============================================================
            int cities_World_N = 10;
            ArrayList<City> topNCitiesWorldList = citiesWorldReport.getTopNCitiesWorldReport(app.getConnection(), cities_World_N);
            citiesWorldReport.printTopNCitiesWorldReport(topNCitiesWorldList, cities_World_N);
            writer.outputCities(topNCitiesWorldList, "12_TopN_Cities_World_Report.md", "Report 12: Top 10 Cities in the World");

            //============================================================
            // REPORT 13: Top N Cities in the continent
            //============================================================
            int cities_Continent_N = 10;
            ArrayList<City> topNCitiesContinentList = citiesContinentReport.getTopNCitiesContinentReport(app.getConnection(), cities_Continent, cities_Continent_N);
            citiesContinentReport.printTopNCitiesContinentReport(topNCitiesContinentList, cities_Continent, cities_Continent_N);
            writer.outputCities(topNCitiesContinentList, "13_TopN_Cities_Continent_Report.md", "Report 13: Top 10 Cities in " + cities_Continent);

            //============================================================
            // REPORT 14: Top N Cities in the region
            //============================================================
            int cities_Region_N = 10;
            ArrayList<City> topNCitiesRegionList = citiesRegionReport.getTopNCitiesRegionReport(app.getConnection(), cities_Region, cities_Region_N);
            citiesRegionReport.printTopNCitiesRegionReport(topNCitiesRegionList, cities_Region, cities_Region_N);
            writer.outputCities(topNCitiesRegionList, "14_TopN_Cities_Region_Report.md", "Report 14: Top 10 Cities in " + cities_Region);

            //============================================================
            // REPORT 15: Top N Cities in the country
            //============================================================
            int cities_Country_N = 10;  // Number of top cities to display
            ArrayList<City> topNCitiesCountryList = citiesCountryReport.getTopNCitiesCountryReport(app.getConnection(), cities_Country, cities_Country_N);
            citiesCountryReport.printTopNCitiesCountryReport(topNCitiesCountryList, cities_Country, cities_Country_N);
            writer.outputCities(topNCitiesCountryList, "15_TopN_Cities_Country_Report.md", "Report 15: Top 10 Cities in " + cities_Country);

            //============================================================
            // REPORT 16: Top N Cities in the district
            //============================================================
            int cities_District_N = 10;  // Number of top cities to display
            ArrayList<City> topNCitiesDistrictList = citiesDistrictReport.getTopNCitiesDistrictReport(app.getConnection(), cities_District, cities_District_N);
            citiesDistrictReport.printTopNCitiesDistrictReport(topNCitiesDistrictList, cities_District, cities_District_N);
            writer.outputCities(topNCitiesDistrictList, "16_TopN_Cities_District_Report.md", "Report 16: Top 10 Cities in " + cities_District);

            //============================================================
            // REPORT 17: Capital Cities in the World
            //============================================================
            CapitalCitiesWorldReport capitalCitiesWorldReport = new CapitalCitiesWorldReport();
            ArrayList<City> capitalsWorldList = capitalCitiesWorldReport.getAllCapitalCitiesInWorldByPopulation(app.getConnection());
            capitalCitiesWorldReport.printAllCapitalCitiesInWorldByPopulation(capitalsWorldList);
            writer.outputCapitalCities(capitalsWorldList, "17_CapitalCities_World_Report.md", "Report 17: Capital Cities in the World");

            //============================================================
            // REPORT 18: Capital Cities in a Continent
            //============================================================
            String capitalCities_Continent = "Asia";
            CapitalCitiesContinentReport capitalCitiesContinentReport = new CapitalCitiesContinentReport();
            ArrayList<City> capitalsContinentList = capitalCitiesContinentReport.getAllCapitalCitiesInContinentByPopulation(app.getConnection(), capitalCities_Continent);
            capitalCitiesContinentReport.printAllCapitalCitiesInContinentByPopulation(capitalsContinentList,capitalCities_Continent);
            writer.outputCapitalCities(capitalsContinentList, "18_CapitalCities_Continent_Report.md", "Report 18: Capital Cities in " + capitalCities_Continent);

            //============================================================
            // REPORT 19: Capital Cities in a Region
            //============================================================
            String capitalCities_Region = "Middle East";
            CapitalCitiesRegionReport capitalCitiesRegionReport = new CapitalCitiesRegionReport();
            ArrayList<City> capitalsRegionList = capitalCitiesRegionReport.getAllCapitalCitiesInRegionByPopulation(app.getConnection(), capitalCities_Region);
            capitalCitiesRegionReport.printAllCapitalCitiesInRegionByPopulation(capitalsRegionList, capitalCities_Region);
            writer.outputCapitalCities(capitalsRegionList, "19_CapitalCities_Region_Report.md", "Report 19: Capital Cities in " + capitalCities_Region);

            //============================================================
            // REPORT 20: Top N Capital Cities in the World
            //============================================================
            ArrayList<City> topNCapitalsWorldList = capitalCitiesWorldReport.getTopNCapitalCitiesInWorldByPopulation(app.getConnection(), 10);
            capitalCitiesWorldReport.printTopNCapitalCitiesInWorldByPopulation(topNCapitalsWorldList,10);
            writer.outputCapitalCities(topNCapitalsWorldList, "20_TopN_CapitalCities_World_Report.md", "Report 20: Top 10 Capital Cities in the World");

            //============================================================
            // REPORT 21: Top N Capital Cities in a Continent
            //============================================================
            ArrayList<City> topNCapitalsContinentList = capitalCitiesContinentReport.getTopNCapitalCitiesInContinentByPopulation(app.getConnection(),capitalCities_Continent,10);
            capitalCitiesContinentReport.printTopNCapitalCitiesInContinentByPopulation(topNCapitalsContinentList,capitalCities_Continent,10);
            writer.outputCapitalCities(topNCapitalsContinentList, "21_TopN_CapitalCities_Continent_Report.md", "Report 21: Top 10 Capital Cities in " + capitalCities_Continent);

            //============================================================
            // REPORT 22: Top N Capital Cities in a Region
            //============================================================
            ArrayList<City> topNCapitalsRegionList = capitalCitiesRegionReport.getTopNCapitalCitiesInRegionByPopulation(app.getConnection(), capitalCities_Region,10);
            capitalCitiesRegionReport.printTopNCapitalCitiesInRegionByPopulation(topNCapitalsRegionList, capitalCities_Region,10);
            writer.outputCapitalCities(topNCapitalsRegionList, "22_TopN_CapitalCities_Region_Report.md", "Report 22: Top 10 Capital Cities in " + capitalCities_Region);

            //------------------------------------------

            //================================================================
            // REPORT 23: Population of a continent (live in/not live in city)
            //================================================================
            PopulationContinentReport continentReport = new PopulationContinentReport();
            List<Country> populations = continentReport.getPopulation_City_vs_NonCity_ByContinent(app.getConnection());
            continentReport.printPopulation_City_vs_NonCity_ByContinent(populations);
            writer.outputContinentPopulation(populations, "23_Population_Continent_Report.md", "Report 23: Population by Continent");

            //================================================================
            // REPORT 24: Population of a region (live in/not live in city)
            //================================================================
            PopulationRegionReport regionReport = new PopulationRegionReport();
            List<Country> regions = regionReport.getPopulation_Region_Details_Report(app.getConnection());
            regionReport.printPopulation_Region_Details_Report(regions);
            writer.outputPopulationByGroup(regions, "24_Population_Region_Report.md", "Report 24: Population by Region", "Region");

            //================================================================
            // REPORT 25: Population of a country (live in/not live in city)
            //================================================================
            PopulationCountryReport countryReport = new PopulationCountryReport();
            List<Country> countryPopulations = countryReport.getPopulation_City_vs_NonCity_ByCountry(app.getConnection());
            countryReport.printPopulation_City_vs_NonCity_ByCountry(countryPopulations);
            writer.outputPopulationByGroup(countryPopulations, "25_Population_Country_Report.md", "Report 25: Population by Country", "Country");

            //========================================
            // REPORT 26: Population of the world
            //========================================
            PopulationWorldReport worldReport = new PopulationWorldReport();
            PopulationWorldReport.PopulationData worldPop = worldReport.getPopulation_World_Report(app.getConnection());
            worldReport.printPopulation_World_Report(worldPop);
            writer.outputPopulationWorld(worldPop, "26_Population_World_Report.md");

            //========================================
            // REPORT 27: Population of the continent
            //========================================
            String continentVariable = "Asia"; // Replace with any continent you want
            PopulationContinentReport populationContinentReport = new PopulationContinentReport();
            List<Country> populationContinentList = populationContinentReport.getPopulation_Continent_Report(app.getConnection());
            List<Country> filteredList = populationContinentList.stream()
                    .filter(c -> c.getContinent().equalsIgnoreCase(continentVariable))
                    .toList(); // Java 16+, or use Collectors.toList() for older versions
            populationContinentReport.printPopulation_Continent_Report(filteredList);
            writer.outputContinentPopulation(filteredList, "27_Population_Specific_Continent_Report.md", "Report 27: Population of Continent (" + continentVariable + ")");

            //========================================
            // REPORT 28: Population of the region
            //========================================
            String regionVariable = "Southern Europe"; // Replace with any region you want
            PopulationRegionReport populationRegionReport = new PopulationRegionReport();
            List<Country> populationRegionList = populationRegionReport.getPopulation_Region_Report(app.getConnection());
            populationRegionReport.printPopulation_Region_Report(populationRegionList, regionVariable);
            List<Country> filteredRegionList = populationRegionList.stream()
                    .filter(c -> c.getRegion().equalsIgnoreCase(regionVariable))
                    .toList(); // For Java 16+, otherwise use Collectors.toList()
            writer.outputPopulationByGroup(filteredRegionList, "28_Population_Specific_Region_Report.md", "Report 28: Population of Region (" + regionVariable + ")", "Region");

            //========================================
            // REPORT 29: Population of the country
            //========================================
            String countryVariable = "Japan"; // Replace with the country you want
            PopulationCountryReport populationCountryReport = new PopulationCountryReport();
            List<Country> populationCountryList = populationCountryReport.getPopulation_Country_Report(app.getConnection());
            populationCountryReport.printPopulation_Country_Report(populationCountryList, countryVariable);
            List<Country> filteredCountryList = populationCountryList.stream()
                    .filter(c -> c.getCountryName().equalsIgnoreCase(countryVariable))
                    .toList();
            writer.outputPopulationByGroup(filteredCountryList, "29_Population_Specific_Country_Report.md", "Report 29: Population of Country (" + countryVariable + ")", "Country");

            //========================================
            // REPORT 30: Population of the district
            //========================================
            String districtVariable = "California"; // Replace with your desired district
            PopulationDistrictReport populationDistrictReport = new PopulationDistrictReport();
            List<Country> populationDistrictList = populationDistrictReport.getPopulation_District_Report(app.getConnection());
            populationDistrictReport.printPopulation_District_Report(populationDistrictList, districtVariable);
            List<Country> filteredDistrictList = populationDistrictList.stream()
                    .filter(c -> c.getCountryName().equalsIgnoreCase(districtVariable))
                    .toList();
            writer.outputPopulationByGroup(filteredDistrictList,
                    "30_Population_District_Report.md",
                    "Report 30: Population of District (" + districtVariable + ")",
                    "District");

            //========================================
            // REPORT 31: Population of the city
            //========================================
            String cityVariable = "Tokyo";
            PopulationCityReport cityReport = new PopulationCityReport();
            List<City> cityList = cityReport.getPopulation_City_Report(app.getConnection());
            cityReport.printPopulation_City_Report(cityList,cityVariable);
            List<City> filteredCityList = cityList.stream()
                    .filter(c -> c.getCityName().equalsIgnoreCase(cityVariable))
                    .toList();
            writer.outputPopulationCityReport(filteredCityList, "31_Population_City_Report.md");

            //============================================================
            // REPORT 32: Language Population
            //============================================================
            LanguagePopulationReport report = new LanguagePopulationReport();
            ArrayList<CountryLanguage> languageReport = report.getLanguagePopulationReport(app.getConnection());
            report.printLanguagePopulationReport(languageReport);
            writer.outputLanguagePopulationReport(languageReport, "32_Language_Population_Report.md");

            // If we reach here, all report calls completed without throwing
            Assertions.assertTrue(true, "All reports executed successfully.");

        } catch (Exception e) {
            // Fail the test and log the exception (keeps behavior identical to running main())
            e.printStackTrace();
            Assertions.fail("An exception occurred while running reports: " + e.getMessage());
        }
    }
}
