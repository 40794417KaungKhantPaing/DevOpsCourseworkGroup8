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


    //-------------------------
    // Country Reports
    // -------------------------
    @Test
    @Order(31)
    void testCountriesWorldSQLExceptionCatch() {
        CountriesWorldReport report = new CountriesWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<Country> countries = report.getCountries_World_Report(app.getConnection());
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(32)
    void testCountriesContinentSQLExceptionCatch() {
        CountriesContinentReport report = new CountriesContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<Country> countries = report.getCountries_Continent_Report(app.getConnection(), "Asia");
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(33)
    void testCountriesRegionSQLExceptionCatch() {
        CountriesRegionReport report = new CountriesRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<Country> countries = report.getCountries_Region_Report(app.getConnection(), "Middle East");
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // Top N Country Reports
    // -------------------------
    @Test
    @Order(34)
    void testTopNCountriesWorldSQLExceptionCatch() {
        CountriesWorldReport report = new CountriesWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<Country> topNCountries = report.getTopNCountries_World_Report(app.getConnection(), 10);
        assertNotNull(topNCountries);
        assertTrue(topNCountries.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(35)
    void testTopNCountriesContinentSQLExceptionCatch() {
        CountriesContinentReport report = new CountriesContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<Country> topNCountries = report.getTopNCountries_Continent_Report(app.getConnection(), "Asia", 10);
        assertNotNull(topNCountries);
        assertTrue(topNCountries.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(36)
    void testTopNCountriesRegionSQLExceptionCatch() {
        CountriesRegionReport report = new CountriesRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<Country> topNCountries = report.getTopNCountries_Region_Report(app.getConnection(), "Middle East", 10);
        assertNotNull(topNCountries);
        assertTrue(topNCountries.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // City Reports
    // -------------------------
    @Test
    @Order(37)
    void testCitiesWorldSQLExceptionCatch() {
        CitiesWorldReport report = new CitiesWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> cities = report.getCitiesWorldReport(app.getConnection());
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(38)
    void testCitiesContinentSQLExceptionCatch() {
        CitiesContinentReport report = new CitiesContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> cities = report.getCitiesContinentReport(app.getConnection(), "Asia");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(39)
    void testCitiesRegionSQLExceptionCatch() {
        CitiesRegionReport report = new CitiesRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> cities = report.getCitiesRegionReport(app.getConnection(), "Eastern Asia");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(40)
    void testCitiesCountrySQLExceptionCatch() {
        CitiesCountryReport report = new CitiesCountryReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> cities = report.getCitiesCountryReport(app.getConnection(), "Japan");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(41)
    void testCitiesDistrictSQLExceptionCatch() {
        CitiesDistrictReport report = new CitiesDistrictReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> cities = report.getCitiesDistrictReport(app.getConnection(), "California");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // Top N City Reports
    // -------------------------
    @Test
    @Order(42)
    void testTopNCitiesWorldSQLExceptionCatch() {
        CitiesWorldReport report = new CitiesWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCities = report.getTopNCitiesWorldReport(app.getConnection(), 10);
        assertNotNull(topCities);
        assertTrue(topCities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(43)
    void testTopNCitiesContinentSQLExceptionCatch() {
        CitiesContinentReport report = new CitiesContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCities = report.getTopNCitiesContinentReport(app.getConnection(), "Asia", 10);
        assertNotNull(topCities);
        assertTrue(topCities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(44)
    void testTopNCitiesRegionSQLExceptionCatch() {
        CitiesRegionReport report = new CitiesRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCities = report.getTopNCitiesRegionReport(app.getConnection(), "Eastern Asia", 10);
        assertNotNull(topCities);
        assertTrue(topCities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(45)
    void testTopNCitiesCountrySQLExceptionCatch() {
        CitiesCountryReport report = new CitiesCountryReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCities = report.getTopNCitiesCountryReport(app.getConnection(), "Japan", 10);
        assertNotNull(topCities);
        assertTrue(topCities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(46)
    void testTopNCitiesDistrictSQLExceptionCatch() {
        CitiesDistrictReport report = new CitiesDistrictReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCities = report.getTopNCitiesDistrictReport(app.getConnection(), "California", 10);
        assertNotNull(topCities);
        assertTrue(topCities.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // Capital City Reports
    // -------------------------
    @Test
    @Order(47)
    void testCapitalCitiesWorldSQLExceptionCatch() {
        CapitalCitiesWorldReport report = new CapitalCitiesWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> capitals = report.getAllCapitalCitiesInWorldByPopulation(app.getConnection());
        assertNotNull(capitals);
        assertTrue(capitals.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(48)
    void testCapitalCitiesContinentSQLExceptionCatch() {
        CapitalCitiesContinentReport report = new CapitalCitiesContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> capitals = report.getAllCapitalCitiesInContinentByPopulation(app.getConnection(), "Asia");
        assertNotNull(capitals);
        assertTrue(capitals.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(49)
    void testCapitalCitiesRegionSQLExceptionCatch() {
        CapitalCitiesRegionReport report = new CapitalCitiesRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> capitals = report.getAllCapitalCitiesInRegionByPopulation(app.getConnection(), "Middle East");
        assertNotNull(capitals);
        assertTrue(capitals.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // Top N Capital Cities
    // -------------------------
    @Test
    @Order(50)
    void testTopNCapitalsWorldSQLExceptionCatch() {
        CapitalCitiesWorldReport report = new CapitalCitiesWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCapitals = report.getTopNCapitalCitiesInWorldByPopulation(app.getConnection(), 10);
        assertNotNull(topCapitals);
        assertTrue(topCapitals.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(51)
    void testTopNCapitalsContinentSQLExceptionCatch() {
        CapitalCitiesContinentReport report = new CapitalCitiesContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCapitals = report.getTopNCapitalCitiesInContinentByPopulation(app.getConnection(), "Asia", 10);
        assertNotNull(topCapitals);
        assertTrue(topCapitals.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(52)
    void testTopNCapitalsRegionSQLExceptionCatch() {
        CapitalCitiesRegionReport report = new CapitalCitiesRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<City> topCapitals = report.getTopNCapitalCitiesInRegionByPopulation(app.getConnection(), "Middle East", 10);
        assertNotNull(topCapitals);
        assertTrue(topCapitals.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // Population Reports
    // -------------------------
    @Test
    @Order(53)
    void testPopulationWorldSQLExceptionCatch() {
        PopulationWorldReport report = new PopulationWorldReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        PopulationWorldReport.PopulationData data = report.getPopulation_World_Report(app.getConnection());
        assertNotNull(data);
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(54)
    void testPopulationContinentSQLExceptionCatch() {
        PopulationContinentReport report = new PopulationContinentReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        List<Country> list = report.getPopulation_Continent_Report(app.getConnection());
        assertNotNull(list);
        assertTrue(list.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(55)
    void testPopulationRegionSQLExceptionCatch() {
        PopulationRegionReport report = new PopulationRegionReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        List<Country> list = report.getPopulation_Region_Report(app.getConnection());
        assertNotNull(list);
        assertTrue(list.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(56)
    void testPopulationCountrySQLExceptionCatch() {
        PopulationCountryReport report = new PopulationCountryReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        List<Country> list = report.getPopulation_Country_Report(app.getConnection());
        assertNotNull(list);
        assertTrue(list.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(57)
    void testPopulationCitySQLExceptionCatch() {
        PopulationCityReport report = new PopulationCityReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        List<City> list = report.getPopulation_City_Report(app.getConnection());
        assertNotNull(list);
        assertTrue(list.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    @Test
    @Order(58)
    void testPopulationDistrictSQLExceptionCatch() {
        PopulationDistrictReport report = new PopulationDistrictReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        List<Country> list = report.getPopulation_District_Report(app.getConnection());
        assertNotNull(list);
        assertTrue(list.isEmpty());
        app.connect("localhost:33060", 3000);
    }

    // -------------------------
    // Language Report
    // -------------------------
    @Test
    @Order(59)
    void testLanguagePopulationSQLExceptionCatch() {
        LanguagePopulationReport report = new LanguagePopulationReport();
        try {
            app.getConnection().close();
        } catch (SQLException ignored) {
        }
        ArrayList<CountryLanguage> list = report.getLanguagePopulationReport(app.getConnection());
        assertNotNull(list);
        assertTrue(list.isEmpty());
        app.connect("localhost:33060", 3000);
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
