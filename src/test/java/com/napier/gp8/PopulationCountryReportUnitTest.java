package com.napier.gp8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PopulationCountryReport.
 * Matches the same simple style as AppTest and other Population report tests.
 */
public class PopulationCountryReportUnitTest
{
    static PopulationCountryReport report;

    @BeforeAll
    static void init()
    {
        report = new PopulationCountryReport();
    }

    // ---------------------------------------------------------------------
    // Test: getPopulation_Country_Report with null connection
    // ---------------------------------------------------------------------
    @Test
    void getPopulationCountryReportTestNullConnection()
    {
        List<Country> result = report.getPopulation_Country_Report(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ---------------------------------------------------------------------
    // Test: getPopulation_City_vs_NonCity_ByCountry with null connection
    // ---------------------------------------------------------------------
    @Test
    void getPopulationCityVsNonCityByCountryTestNullConnection()
    {
        List<Country> result = report.getPopulation_City_vs_NonCity_ByCountry(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Country_Report with null list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCountryReportTestNull()
    {
        report.printPopulation_Country_Report(null, null);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Country_Report with empty list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCountryReportTestEmpty()
    {
        List<Country> list = new ArrayList<>();
        report.printPopulation_Country_Report(list, null);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Country_Report with data
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCountryReportTestData()
    {
        List<Country> list = new ArrayList<>();
        Country japan = new Country();
        japan.setCountryName("Japan");
        japan.setPopulation(126000000L);
        japan.setGnp(90000000.0);
        japan.setGnpOld(36000000.0);
        list.add(japan);

        report.printPopulation_Country_Report(list, null);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_Country_Report with specific country filter
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCountryReportTestFilter()
    {
        List<Country> list = new ArrayList<>();

        Country usa = new Country();
        usa.setCountryName("United States");
        usa.setPopulation(331000000L);
        usa.setGnp(220000000.0);
        usa.setGnpOld(111000000.0);
        list.add(usa);

        Country canada = new Country();
        canada.setCountryName("Canada");
        canada.setPopulation(38000000L);
        canada.setGnp(21000000.0);
        canada.setGnpOld(17000000.0);
        list.add(canada);

        // Should print only United States
        report.printPopulation_Country_Report(list, "United States");
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_City_vs_NonCity_ByCountry with valid data
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCityVsNonCityByCountryTestData()
    {
        List<Country> list = new ArrayList<>();

        Country france = new Country();
        france.setCountryName("France");
        france.setPopulation(67000000L);
        france.setGnp(45000000.0);
        france.setGnpOld(22000000.0);
        list.add(france);

        report.printPopulation_City_vs_NonCity_ByCountry(list);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_City_vs_NonCity_ByCountry with null list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCityVsNonCityByCountryTestNull()
    {
        report.printPopulation_City_vs_NonCity_ByCountry(null);
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_City_vs_NonCity_ByCountry with empty list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationCityVsNonCityByCountryTestEmpty()
    {
        List<Country> list = new ArrayList<>();
        report.printPopulation_City_vs_NonCity_ByCountry(list);
    }
}
