package com.napier.gp8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple JUnit 5 test class for PopulationContinentReport.
 * Matches the same structure and style as AppTest.
 */
public class PopulationContinentReportUnitTest
{
    static PopulationContinentReport report;

    @BeforeAll
    static void init()
    {
        report = new PopulationContinentReport();
    }

    // ---------------------------------------------------------------------
    // Test: null connection handling
    // ---------------------------------------------------------------------
    @Test
    void getPopulationContinentReportTestNullConnection()
    {
        List<Country> result = report.getPopulation_Continent_Report(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Continent_Report with null list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationContinentReportTestNull()
    {
        report.printPopulation_Continent_Report(null);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Continent_Report with empty list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationContinentReportTestEmpty()
    {
        List<Country> list = new ArrayList<>();
        report.printPopulation_Continent_Report(list);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Continent_Report with valid data
    // ---------------------------------------------------------------------
    @Test
    void printPopulationContinentReportTestData()
    {
        List<Country> list = new ArrayList<>();
        Country asia = new Country();
        asia.setContinent("Asia");
        asia.setPopulation(4000000000L);
        asia.setGnp(1500000000.0);
        asia.setGnpOld(2500000000.0);
        list.add(asia);

        report.printPopulation_Continent_Report(list);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_City_vs_NonCity_ByContinent with valid data
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCityVsNonCityByContinentTestData()
    {
        List<Country> list = new ArrayList<>();
        Country europe = new Country();
        europe.setContinent("Europe");
        europe.setPopulation(1000000000L);
        europe.setGnp(600000000.0);
        europe.setGnpOld(400000000.0);
        list.add(europe);

        report.printPopulation_City_vs_NonCity_ByContinent(list);
    }

    // ---------------------------------------------------------------------
    // Test: getPopulation_City_vs_NonCity_ByContinent with null connection
    // ---------------------------------------------------------------------
    @Test
    void getPopulationCityVsNonCityByContinentTestNullConnection()
    {
        List<Country> result = report.getPopulation_City_vs_NonCity_ByContinent(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
