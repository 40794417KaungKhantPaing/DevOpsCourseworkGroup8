package com.napier.gp8;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Full integration test suite for App and all report modules,
 * including Top N reports.
 * REQUIREMENTS:
 * - MySQL container or local DB running with 'world' schema.
 * - User: root, Password: root
 * - Port: 33060
 * Run with: mvn test
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppIntegrationTest {

    private static App app;

    @BeforeAll
    static void setUp() {
        app = new App();
        app.connect("localhost:33060", 5000);
        assertNotNull(app, "App instance should not be null");
        assertNotNull(app.getConnection(), "Database connection should be established");
    }

    // Test 1: Connection Establishment
    @Test
    @Order(1)
    void testDatabaseConnection() {
        try {
            Connection conn = app.getConnection();
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        } catch (Exception e) {
            fail("Database connection test failed: " + e.getMessage());
        }
    }

    // =========================
    // Country Reports
    // =========================
    @Test
    @Order(2)
    void testCountriesWorldReportIntegration() {
        CountriesWorldReport report = new CountriesWorldReport();
        ArrayList<Country> countries = report.getCountries_World_Report(app.getConnection());
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
    }

    @Test
    @Order(3)
    void testCountriesContinentReportIntegration() {
        CountriesContinentReport report = new CountriesContinentReport();
        ArrayList<Country> countries = report.getCountries_Continent_Report(app.getConnection(), "Asia");
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
    }

    @Test
    @Order(4)
    void testCountriesRegionReportIntegration() {
        CountriesRegionReport report = new CountriesRegionReport();
        ArrayList<Country> countries = report.getCountries_Region_Report(app.getConnection(), "Middle East");
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
    }

    // =========================
    // Top N Country Reports
    // =========================
    @Test
    @Order(5)
    void testTopNCountriesWorldIntegration() {
        CountriesWorldReport report = new CountriesWorldReport();
        ArrayList<Country> topNCountries = report.getTopNCountries_World_Report(app.getConnection(), 10);
        assertNotNull(topNCountries);
        assertFalse(topNCountries.isEmpty());
        assertTrue(topNCountries.size() <= 10);
    }

    @Test
    @Order(6)
    void testTopNCountriesContinentIntegration() {
        CountriesContinentReport report = new CountriesContinentReport();
        ArrayList<Country> topNCountries = report.getTopNCountries_Continent_Report(app.getConnection(), "Asia", 10);
        assertNotNull(topNCountries);
        assertFalse(topNCountries.isEmpty());
        assertTrue(topNCountries.size() <= 10);
    }

    @Test
    @Order(7)
    void testTopNCountriesRegionIntegration() {
        CountriesRegionReport report = new CountriesRegionReport();
        ArrayList<Country> topNCountries = report.getTopNCountries_Region_Report(app.getConnection(), "Middle East", 10);
        assertNotNull(topNCountries);
        assertFalse(topNCountries.isEmpty());
        assertTrue(topNCountries.size() <= 10);
    }

    // =========================
    // City Reports
    // =========================
    @Test
    @Order(8)
    void testCitiesWorldReportIntegration() {
        CitiesWorldReport report = new CitiesWorldReport();
        ArrayList<City> cities = report.getCitiesWorldReport(app.getConnection());
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    @Order(9)
    void testCitiesContinentReportIntegration() {
        CitiesContinentReport report = new CitiesContinentReport();
        ArrayList<City> cities = report.getCitiesContinentReport(app.getConnection(), "Asia");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    @Order(10)
    void testCitiesRegionReportIntegration() {
        CitiesRegionReport report = new CitiesRegionReport();
        ArrayList<City> cities = report.getCitiesRegionReport(app.getConnection(), "Eastern Asia");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    @Order(11)
    void testCitiesCountryReportIntegration() {
        CitiesCountryReport report = new CitiesCountryReport();
        ArrayList<City> cities = report.getCitiesCountryReport(app.getConnection(), "Myanmar");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    @Order(12)
    void testCitiesDistrictReportIntegration() {
        CitiesDistrictReport report = new CitiesDistrictReport();
        ArrayList<City> cities = report.getCitiesDistrictReport(app.getConnection(), "California");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    // =========================
    // Top N City Reports
    // =========================
    @Test
    @Order(13)
    void testTopNCitiesWorldIntegration() {
        CitiesWorldReport report = new CitiesWorldReport();
        ArrayList<City> topCities = report.getTopNCitiesWorldReport(app.getConnection(), 10);
        assertNotNull(topCities);
        assertFalse(topCities.isEmpty());
        assertTrue(topCities.size() <= 10);
    }

    @Test
    @Order(14)
    void testTopNCitiesContinentIntegration() {
        CitiesContinentReport report = new CitiesContinentReport();
        ArrayList<City> topCities = report.getTopNCitiesContinentReport(app.getConnection(), "Asia", 10);
        assertNotNull(topCities);
        assertFalse(topCities.isEmpty());
        assertTrue(topCities.size() <= 10);
    }

    @Test
    @Order(15)
    void testTopNCitiesRegionIntegration() {
        CitiesRegionReport report = new CitiesRegionReport();
        ArrayList<City> topCities = report.getTopNCitiesRegionReport(app.getConnection(), "Eastern Asia", 10);
        assertNotNull(topCities);
        assertFalse(topCities.isEmpty());
        assertTrue(topCities.size() <= 10);
    }

    @Test
    @Order(16)
    void testTopNCitiesCountryIntegration() {
        CitiesCountryReport report = new CitiesCountryReport();
        ArrayList<City> topCities = report.getTopNCitiesCountryReport(app.getConnection(), "Japan", 10);
        assertNotNull(topCities, "Top N Cities Country list should not be null");
        assertFalse(topCities.isEmpty(), "Top N Cities Country list should contain data");
        assertTrue(topCities.size() <= 10, "Top N Cities Country list should have at most 10 items");
    }

    @Test
    @Order(17)
    void testTopNCitiesDistrictIntegration() {
        CitiesDistrictReport report = new CitiesDistrictReport();
        ArrayList<City> topCities = report.getTopNCitiesDistrictReport(app.getConnection(), "California", 10);
        assertNotNull(topCities, "Top N Cities District list should not be null");
        assertFalse(topCities.isEmpty(), "Top N Cities District list should contain data");
        assertTrue(topCities.size() <= 10, "Top N Cities District list should have at most 10 items");
    }


    // =========================
    // Capital City Reports
    // =========================
    @Test
    @Order(18)
    void testCapitalCitiesWorldIntegration() {
        CapitalCitiesWorldReport report = new CapitalCitiesWorldReport();
        ArrayList<City> capitals = report.getAllCapitalCitiesInWorldByPopulation(app.getConnection());
        assertNotNull(capitals);
        assertFalse(capitals.isEmpty());
    }

    @Test
    @Order(19)
    void testCapitalCitiesContinentIntegration() {
        CapitalCitiesContinentReport report = new CapitalCitiesContinentReport();
        ArrayList<City> capitals = report.getAllCapitalCitiesInContinentByPopulation(app.getConnection(), "Asia");
        assertNotNull(capitals);
        assertFalse(capitals.isEmpty());
    }

    @Test
    @Order(20)
    void testCapitalCitiesRegionIntegration() {
        CapitalCitiesRegionReport report = new CapitalCitiesRegionReport();
        ArrayList<City> capitals = report.getAllCapitalCitiesInRegionByPopulation(app.getConnection(), "Middle East");
        assertNotNull(capitals);
        assertFalse(capitals.isEmpty());
    }

    // =========================
    // Top N Capital Cities
    // =========================
    @Test
    @Order(21)
    void testTopNCapitalsWorldIntegration() {
        CapitalCitiesWorldReport report = new CapitalCitiesWorldReport();
        ArrayList<City> topCapitals = report.getTopNCapitalCitiesInWorldByPopulation(app.getConnection(), 10);
        assertNotNull(topCapitals);
        assertFalse(topCapitals.isEmpty());
        assertTrue(topCapitals.size() <= 10);
    }

    @Test
    @Order(22)
    void testTopNCapitalsContinentIntegration() {
        CapitalCitiesContinentReport report = new CapitalCitiesContinentReport();
        ArrayList<City> topCapitals = report.getTopNCapitalCitiesInContinentByPopulation(app.getConnection(), "Asia", 10);
        assertNotNull(topCapitals);
        assertFalse(topCapitals.isEmpty());
        assertTrue(topCapitals.size() <= 10);
    }

    @Test
    @Order(23)
    void testTopNCapitalsRegionIntegration() {
        CapitalCitiesRegionReport report = new CapitalCitiesRegionReport();
        ArrayList<City> topCapitals = report.getTopNCapitalCitiesInRegionByPopulation(app.getConnection(), "Middle East", 10);
        assertNotNull(topCapitals);
        assertFalse(topCapitals.isEmpty());
        assertTrue(topCapitals.size() <= 10);
    }

    // =========================
    // Population Reports
    // =========================
    @Test
    @Order(24)
    void testPopulationWorldReportIntegration() {
        PopulationWorldReport report = new PopulationWorldReport();
        PopulationWorldReport.PopulationData data = report.getPopulation_World_Report(app.getConnection());
        assertNotNull(data);
    }

    @Test
    @Order(25)
    void testPopulationContinentReportIntegration() {
        PopulationContinentReport report = new PopulationContinentReport();
        List<Country> list = report.getPopulation_Continent_Report(app.getConnection());
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(26)
    void testPopulationRegionReportIntegration() {
        PopulationRegionReport report = new PopulationRegionReport();
        List<Country> list = report.getPopulation_Region_Report(app.getConnection());
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(27)
    void testPopulationCountryReportIntegration() {
        PopulationCountryReport report = new PopulationCountryReport();
        List<Country> list = report.getPopulation_Country_Report(app.getConnection());
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(28)
    void testPopulationCityReportIntegration() {
        PopulationCityReport report = new PopulationCityReport();
        List<City> list = report.getPopulation_City_Report(app.getConnection());
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(29)
    void testPopulationDistrictReportIntegration() {
        PopulationDistrictReport report = new PopulationDistrictReport();
        List<Country> list = report.getPopulation_District_Report(app.getConnection());
        assertNotNull(list, "Population by district list should not be null");
        assertFalse(list.isEmpty(), "Population by district list should contain data");
    }


    // =========================
    // Language Reports
    // =========================
    @Test
    @Order(30)
    void testLanguagePopulationReportIntegration() {
        LanguagePopulationReport report = new LanguagePopulationReport();
        ArrayList<CountryLanguage> list = report.getLanguagePopulationReport(app.getConnection());
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }
    // =========================
    // Catch-Block / SQLException Tests
    // =========================


    // =================================================
    // Shared helper for SQL exception (broken connection)
    // =================================================
    private void breakConnection() {
        try {
            if (app.getConnection() != null && !app.getConnection().isClosed()) {
                app.getConnection().close();
            }
        } catch (SQLException ignored) {
        }
    }

    private void reconnect() {
        app.connect("localhost:33060", 3000);
    }

    // =================================================
    // Example combined SQL Exception Tests
    // =================================================
    @Test
    @Order(31)
    void testAllSQLExceptionCatchBlocks() {
        breakConnection();

        // Country Reports
        assertTrue(new CountriesWorldReport().getCountries_World_Report(app.getConnection()).isEmpty());
        assertTrue(new CountriesContinentReport().getCountries_Continent_Report(app.getConnection(), "Asia").isEmpty());
        assertTrue(new CountriesRegionReport().getCountries_Region_Report(app.getConnection(), "Middle East").isEmpty());

        // City Reports
        assertTrue(new CitiesWorldReport().getCitiesWorldReport(app.getConnection()).isEmpty());
        assertTrue(new CitiesContinentReport().getCitiesContinentReport(app.getConnection(), "Asia").isEmpty());
        assertTrue(new CitiesRegionReport().getCitiesRegionReport(app.getConnection(), "Eastern Asia").isEmpty());
        assertTrue(new CitiesCountryReport().getCitiesCountryReport(app.getConnection(), "Japan").isEmpty());
        assertTrue(new CitiesDistrictReport().getCitiesDistrictReport(app.getConnection(), "California").isEmpty());

        // Capital City Reports
        assertTrue(new CapitalCitiesWorldReport().getAllCapitalCitiesInWorldByPopulation(app.getConnection()).isEmpty());
        assertTrue(new CapitalCitiesContinentReport().getAllCapitalCitiesInContinentByPopulation(app.getConnection(), "Asia").isEmpty());
        assertTrue(new CapitalCitiesRegionReport().getAllCapitalCitiesInRegionByPopulation(app.getConnection(), "Middle East").isEmpty());


        // Top N Country Reports
        assertTrue(new CountriesWorldReport().getTopNCountries_World_Report(app.getConnection(), 10).isEmpty());
        assertTrue(new CountriesContinentReport().getTopNCountries_Continent_Report(app.getConnection(), "Asia", 10).isEmpty());
        assertTrue(new CountriesRegionReport().getTopNCountries_Region_Report(app.getConnection(), "Middle East", 10).isEmpty());

        // Top N City Reports
        assertTrue(new CitiesWorldReport().getTopNCitiesWorldReport(app.getConnection(), 10).isEmpty());
        assertTrue(new CitiesContinentReport().getTopNCitiesContinentReport(app.getConnection(), "Asia", 10).isEmpty());
        assertTrue(new CitiesRegionReport().getTopNCitiesRegionReport(app.getConnection(), "Eastern Asia", 10).isEmpty());
        assertTrue(new CitiesCountryReport().getTopNCitiesCountryReport(app.getConnection(), "Japan", 10).isEmpty());
        assertTrue(new CitiesDistrictReport().getTopNCitiesDistrictReport(app.getConnection(), "California", 10).isEmpty());

        // Top N Capital City Reports
        assertTrue(new CapitalCitiesWorldReport().getTopNCapitalCitiesInWorldByPopulation(app.getConnection(), 10).isEmpty());
        assertTrue(new CapitalCitiesContinentReport().getTopNCapitalCitiesInContinentByPopulation(app.getConnection(), "Asia", 10).isEmpty());
        assertTrue(new CapitalCitiesRegionReport().getTopNCapitalCitiesInRegionByPopulation(app.getConnection(), "Middle East", 10).isEmpty());

        // Population Reports
        assertTrue(new PopulationContinentReport().getPopulation_Continent_Report(app.getConnection()).isEmpty());
        assertTrue(new PopulationRegionReport().getPopulation_Region_Report(app.getConnection()).isEmpty());
        assertTrue(new PopulationCountryReport().getPopulation_Country_Report(app.getConnection()).isEmpty());
        assertTrue(new PopulationCityReport().getPopulation_City_Report(app.getConnection()).isEmpty());
        assertTrue(new PopulationDistrictReport().getPopulation_District_Report(app.getConnection()).isEmpty());

        // Language Reports
        assertTrue(new LanguagePopulationReport().getLanguagePopulationReport(app.getConnection()).isEmpty());

        // finally reconnect
        reconnect();
    }


    // =========================
    // Tear Down
    // =========================
    @AfterAll
    static void tearDown() {
        app.disconnect();
        try {
            assertTrue(app.getConnection() == null || app.getConnection().isClosed());
        } catch (Exception ignored) {
        }
    }
}

