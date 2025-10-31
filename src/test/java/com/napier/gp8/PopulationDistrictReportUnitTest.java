package com.napier.gp8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PopulationDistrictReport.
 * Matches the style of other population report tests.
 */
public class PopulationDistrictReportUnitTest
{
    static PopulationDistrictReport report;

    @BeforeAll
    static void init()
    {
        report = new PopulationDistrictReport();
    }

    // ---------------------------------------------------------------------
    // Test: getPopulation_District_Report with null connection
    // ---------------------------------------------------------------------
    @Test
    void getPopulationDistrictReportTestNullConnection()
    {
        List<Country> results = report.getPopulation_District_Report(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_District_Report with null list
    // ---------------------------------------------------------------------
    @Test
    void printPopulationDistrictReportTestNull()
    {
        List<Country> results = new ArrayList<>();
        report.printPopulation_District_Report(results, "TestDistrict");
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_District_Report with sample data
    // ---------------------------------------------------------------------
    @Test
    void printPopulationDistrictReportTestData()
    {
        List<Country> countries = new ArrayList<>();

        Country c = new Country();
        c.setCountryName("Yangon");
        c.setPopulation(8_000_000);
        c.setGnp(7_500_000.0);
        c.setGnpOld(500_000.0);
        countries.add(c);

        report.printPopulation_District_Report(countries, "Yangon");
    }

    // ---------------------------------------------------------------------
    // Test: printPopulation_District_Report with no matching district
    // ---------------------------------------------------------------------
    @Test
    void printPopulationDistrictReportTestNoMatch()
    {
        List<Country> countries = new ArrayList<>();

        Country c = new Country();
        c.setCountryName("Mandalay");
        c.setPopulation(3_000_000);
        c.setGnp(2_700_000.0);
        c.setGnpOld(300_000.0);
        countries.add(c);

        // selectedDistrict does not match → nothing should print but no error
        report.printPopulation_District_Report(countries, "Yangon");
    }

    // ---------------------------------------------------------------------
    // Test: getPopulation_District_Report with fake connection (null-like)
    // ---------------------------------------------------------------------
    @Test
    void getPopulationDistrictReportFakeConnection()
    {
        Connection conn = null;
        List<Country> results = report.getPopulation_District_Report(conn);
        assertNotNull(results);
    }
}
