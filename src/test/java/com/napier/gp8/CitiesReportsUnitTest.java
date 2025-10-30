package com.napier.gp8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Combined unit tests for all city report classes in one file.
 */
public class CitiesReportsUnitTest {

    static CitiesContinentReport continentReport;
    static CitiesCountryReport countryReport;
    static CitiesDistrictReport districtReport;
    static CitiesRegionReport regionReport;
    static CitiesWorldReport worldReport;
    static CitiesReportBase baseReport;

    @BeforeAll
    static void init() {
        continentReport = new CitiesContinentReport();
        countryReport = new CitiesCountryReport();
        districtReport = new CitiesDistrictReport();
        regionReport = new CitiesRegionReport();
        worldReport = new CitiesWorldReport();
        baseReport = new CitiesReportBase();
    }

    // ---------------------------------------------------------------------
    // Continent report tests
    // ---------------------------------------------------------------------
    @Test
    void getCitiesContinentReportTestNullConnection() {
        List<City> cities = continentReport.getCitiesContinentReport(null, "Asia");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void getTopNCitiesContinentReportTestNullConnection() {
        List<City> cities = continentReport.getTopNCitiesContinentReport(null, "Europe", 5);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void printCitiesContinentReportTestEmptyList() {
        continentReport.printCitiesContinentReport(new ArrayList<>(), "Asia");
    }

    @Test
    void printTopNCitiesContinentReportTestEmptyList() {
        continentReport.printTopNCitiesContinentReport(new ArrayList<>(), "Europe", 5);
    }

    @Test
    void printCitiesContinentReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setId(1);
        city.setCityName("Tokyo");
        city.setCountryCode("JPN");
        city.setDistrict("Tokyo-to");
        city.setPopulation(37_833_000);
        Country country = new Country();
        country.setCountryName("Japan");
        city.setCountry(country);
        cities.add(city);
        continentReport.printCitiesContinentReport(cities, "Asia");
    }

    @Test
    void printTopNCitiesContinentReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setId(1);
        city.setCityName("Shanghai");
        city.setCountryCode("CHN");
        city.setDistrict("Shanghai");
        city.setPopulation(26_317_104);
        Country country = new Country();
        country.setCountryName("China");
        city.setCountry(country);
        cities.add(city);
        continentReport.printTopNCitiesContinentReport(cities, "Asia", 1);
    }

    @Test
    void getCitiesContinentReportFakeConnection() {
        List<City> results = continentReport.getCitiesContinentReport(null, "Africa");
        assertNotNull(results);
    }

    @Test
    void getTopNCitiesContinentReportFakeConnection() {
        List<City> results = continentReport.getTopNCitiesContinentReport(null, "North America", 10);
        assertNotNull(results);
    }

    // ---------------------------------------------------------------------
    // Country report tests
    // ---------------------------------------------------------------------
    @Test
    void getCitiesCountryReportTestNullConnection() {
        List<City> cities = countryReport.getCitiesCountryReport(null, "Japan");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void getTopNCitiesCountryReportTestNullConnection() {
        List<City> cities = countryReport.getTopNCitiesCountryReport(null, "China", 5);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void printCitiesCountryReportTestEmptyList() {
        countryReport.printCitiesCountryReport(new ArrayList<>(), "Japan");
    }

    @Test
    void printTopNCitiesCountryReportTestEmptyList() {
        countryReport.printTopNCitiesCountryReport(new ArrayList<>(), "China", 5);
    }

    @Test
    void printCitiesCountryReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setId(1);
        city.setCityName("Tokyo");
        city.setCountryCode("JPN");
        city.setDistrict("Tokyo-to");
        city.setPopulation(37_833_000);
        Country country = new Country();
        country.setCountryName("Japan");
        city.setCountry(country);
        cities.add(city);
        countryReport.printCitiesCountryReport(cities, "Japan");
    }

    @Test
    void printTopNCitiesCountryReportTestData() {
        ArrayList<City> cities = new ArrayList<>();

        City city1 = new City();
        city1.setId(1);
        city1.setCityName("Shanghai");
        city1.setCountryCode("CHN");
        city1.setDistrict("Shanghai");
        city1.setPopulation(26_317_104);
        Country country1 = new Country();
        country1.setCountryName("China");
        city1.setCountry(country1);
        cities.add(city1);

        City city2 = new City();
        city2.setId(2);
        city2.setCityName("Beijing");
        city2.setCountryCode("CHN");
        city2.setDistrict("Beijing");
        city2.setPopulation(21_893_095);
        Country country2 = new Country();
        country2.setCountryName("China");
        city2.setCountry(country2);
        cities.add(city2);

        countryReport.printTopNCitiesCountryReport(cities, "China", 2);
    }

    @Test
    void getCitiesCountryReportFakeConnection() {
        List<City> results = countryReport.getCitiesCountryReport(null, "India");
        assertNotNull(results);
    }

    @Test
    void getTopNCitiesCountryReportFakeConnection() {
        List<City> results = countryReport.getTopNCitiesCountryReport(null, "Australia", 10);
        assertNotNull(results);
    }

    // ---------------------------------------------------------------------
    // District report tests
    // ---------------------------------------------------------------------
    @Test
    void getCitiesDistrictReportTestNullConnection() {
        List<City> cities = districtReport.getCitiesDistrictReport(null, "Central");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void getTopNCitiesDistrictReportTestNullConnection() {
        List<City> cities = districtReport.getTopNCitiesDistrictReport(null, "Central", 5);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void printCitiesDistrictReportTestEmptyList() {
        districtReport.printCitiesDistrictReport(new ArrayList<>(), "Central");
    }

    @Test
    void printTopNCitiesDistrictReportTestEmptyList() {
        districtReport.printTopNCitiesDistrictReport(new ArrayList<>(), "Central", 5);
    }

    @Test
    void printCitiesDistrictReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setId(1);
        city.setCityName("Springfield");
        city.setCountryCode("USA");
        city.setDistrict("Illinois");
        city.setPopulation(116_250);
        Country country = new Country();
        country.setCountryName("United States");
        city.setCountry(country);
        cities.add(city);
        districtReport.printCitiesDistrictReport(cities, "Illinois");
    }

    @Test
    void printTopNCitiesDistrictReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city1 = new City();
        city1.setId(1);
        city1.setCityName("Houston");
        city1.setCountryCode("USA");
        city1.setDistrict("Texas");
        city1.setPopulation(2_310_000);
        Country country1 = new Country();
        country1.setCountryName("United States");
        city1.setCountry(country1);
        cities.add(city1);

        City city2 = new City();
        city2.setId(2);
        city2.setCityName("Dallas");
        city2.setCountryCode("USA");
        city2.setDistrict("Texas");
        city2.setPopulation(1_343_000);
        Country country2 = new Country();
        country2.setCountryName("United States");
        city2.setCountry(country2);
        cities.add(city2);

        districtReport.printTopNCitiesDistrictReport(cities, "Texas", 2);
    }

    @Test
    void getCitiesDistrictReportFakeConnection() {
        List<City> results = districtReport.getCitiesDistrictReport(null, "California");
        assertNotNull(results);
    }

    @Test
    void getTopNCitiesDistrictReportFakeConnection() {
        List<City> results = districtReport.getTopNCitiesDistrictReport(null, "Florida", 10);
        assertNotNull(results);
    }

    // ---------------------------------------------------------------------
    // Region report tests
    // ---------------------------------------------------------------------
    @Test
    void getCitiesRegionReportTestNullConnection() {
        List<City> cities = regionReport.getCitiesRegionReport(null, "Southern Europe");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void getTopNCitiesRegionReportTestNullConnection() {
        List<City> cities = regionReport.getTopNCitiesRegionReport(null, "Eastern Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void printCitiesRegionReportTestEmptyList() {
        regionReport.printCitiesRegionReport(new ArrayList<>(), "Southern Europe");
    }

    @Test
    void printTopNCitiesRegionReportTestEmptyList() {
        regionReport.printTopNCitiesRegionReport(new ArrayList<>(), "Eastern Asia", 5);
    }

    @Test
    void printCitiesRegionReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setId(1);
        city.setCityName("Rome");
        city.setCountryCode("ITA");
        city.setDistrict("Lazio");
        city.setPopulation(2_873_000);
        Country country = new Country();
        country.setCountryName("Italy");
        city.setCountry(country);
        cities.add(city);
        regionReport.printCitiesRegionReport(cities, "Southern Europe");
    }

    @Test
    void printTopNCitiesRegionReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city1 = new City();
        city1.setId(1);
        city1.setCityName("Beijing");
        city1.setCountryCode("CHN");
        city1.setDistrict("Beijing");
        city1.setPopulation(21_893_095);
        Country country1 = new Country();
        country1.setCountryName("China");
        city1.setCountry(country1);
        cities.add(city1);

        City city2 = new City();
        city2.setId(2);
        city2.setCityName("Shanghai");
        city2.setCountryCode("CHN");
        city2.setDistrict("Shanghai");
        city2.setPopulation(26_317_104);
        Country country2 = new Country();
        country2.setCountryName("China");
        city2.setCountry(country2);
        cities.add(city2);

        regionReport.printTopNCitiesRegionReport(cities, "Eastern Asia", 2);
    }

    @Test
    void getCitiesRegionReportFakeConnection() {
        List<City> results = regionReport.getCitiesRegionReport(null, "Northern Europe");
        assertNotNull(results);
    }

    @Test
    void getTopNCitiesRegionReportFakeConnection() {
        List<City> results = regionReport.getTopNCitiesRegionReport(null, "Western Europe", 10);
        assertNotNull(results);
    }

    // ---------------------------------------------------------------------
    // World report tests
    // ---------------------------------------------------------------------
    @Test
    void getCitiesWorldReportTestNullConnection() {
        List<City> cities = worldReport.getCitiesWorldReport(null);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void getTopNCitiesWorldReportTestNullConnection() {
        List<City> cities = worldReport.getTopNCitiesWorldReport(null, 5);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void printCitiesWorldReportTestEmptyList() {
        worldReport.printCitiesWorldReport(new ArrayList<>());
    }

    @Test
    void printTopNCitiesWorldReportTestEmptyList() {
        worldReport.printTopNCitiesWorldReport(new ArrayList<>(), 5);
    }

    @Test
    void printCitiesWorldReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setId(1);
        city.setCityName("New York");
        city.setCountryCode("USA");
        city.setDistrict("New York");
        city.setPopulation(8_804_000);
        Country country = new Country();
        country.setCountryName("United States");
        city.setCountry(country);
        cities.add(city);
        worldReport.printCitiesWorldReport(cities);
    }

    @Test
    void printTopNCitiesWorldReportTestData() {
        ArrayList<City> cities = new ArrayList<>();
        City city1 = new City();
        city1.setId(1);
        city1.setCityName("Tokyo");
        city1.setCountryCode("JPN");
        city1.setDistrict("Tokyo-to");
        city1.setPopulation(37_833_000);
        Country country1 = new Country();
        country1.setCountryName("Japan");
        city1.setCountry(country1);
        cities.add(city1);

        City city2 = new City();
        city2.setId(2);
        city2.setCityName("Shanghai");
        city2.setCountryCode("CHN");
        city2.setDistrict("Shanghai");
        city2.setPopulation(26_317_104);
        Country country2 = new Country();
        country2.setCountryName("China");
        city2.setCountry(country2);
        cities.add(city2);

        worldReport.printTopNCitiesWorldReport(cities, 2);
    }

    @Test
    void getCitiesWorldReportFakeConnection() {
        List<City> results = worldReport.getCitiesWorldReport(null);
        assertNotNull(results);
    }

    @Test
    void getTopNCitiesWorldReportFakeConnection() {
        List<City> results = worldReport.getTopNCitiesWorldReport(null, 10);
        assertNotNull(results);
    }

    // Test: buildCitiesFromResultSet with null ResultSet
    @Test
    void buildCitiesFromResultSetTest_NullResultSet() {
        ArrayList<City> result = baseReport.buildCitiesFromResultSet(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // Test: printCities with empty list
    @Test
    void printCitiesTest_EmptyList() {
        baseReport.printCities(new ArrayList<>(), "Empty Report");
    }

    // Test: printCities with sample data
    @Test
    void printCitiesTest_SampleData() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setCityName("Tokyo");
        city.setDistrict("Tokyo-to");
        city.setPopulation(37_833_000);
        Country country = new Country();
        country.setCountryName("Japan");
        city.setCountry(country);
        cities.add(city);

        baseReport.printCities(cities, "Sample Report");
    }
}
