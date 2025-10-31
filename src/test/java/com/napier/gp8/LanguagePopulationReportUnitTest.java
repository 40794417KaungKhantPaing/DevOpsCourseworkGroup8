package com.napier.gp8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LanguagePopulationReport.
 * Consistent with style used for other report unit tests.
 */
public class LanguagePopulationReportUnitTest
{
    static LanguagePopulationReport report;

    @BeforeAll
    static void init()
    {
        report = new LanguagePopulationReport();
    }

    // ---------------------------------------------------------------------
    // Test: getLanguagePopulationReport with null connection
    // ---------------------------------------------------------------------
    @Test
    void getLanguagePopulationReportTestNullConnection()
    {
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<Long> speakers = new ArrayList<>();
        ArrayList<Double> worldPercent = new ArrayList<>();

        report.getLanguagePopulationReport(null, languages, speakers, worldPercent);

        assertTrue(languages.isEmpty());
        assertTrue(speakers.isEmpty());
        assertTrue(worldPercent.isEmpty());
    }

    // ---------------------------------------------------------------------
    // Test: printLanguagePopulationReport with null lists
    // ---------------------------------------------------------------------
    @Test
    void printLanguagePopulationReportTestNullLists()
    {
        report.printLanguagePopulationReport(null, null, null);
    }

    // ---------------------------------------------------------------------
    // Test: printLanguagePopulationReport with empty lists
    // ---------------------------------------------------------------------
    @Test
    void printLanguagePopulationReportTestEmptyLists()
    {
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<Long> speakers = new ArrayList<>();
        ArrayList<Double> worldPercent = new ArrayList<>();

        report.printLanguagePopulationReport(languages, speakers, worldPercent);
    }

    // ---------------------------------------------------------------------
    // Test: printLanguagePopulationReport with sample data
    // ---------------------------------------------------------------------
    @Test
    void printLanguagePopulationReportTestData()
    {
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<Long> speakers = new ArrayList<>();
        ArrayList<Double> worldPercent = new ArrayList<>();

        languages.add("Chinese");
        speakers.add(1200000000L);
        worldPercent.add(15.5);

        languages.add("English");
        speakers.add(950000000L);
        worldPercent.add(12.3);

        report.printLanguagePopulationReport(languages, speakers, worldPercent);
    }

    // ---------------------------------------------------------------------
    // Test: getLanguagePopulationReport with empty connection-like input
    // ---------------------------------------------------------------------
    @Test
    void getLanguagePopulationReportFakeConnection()
    {
        Connection conn = null;
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<Long> speakers = new ArrayList<>();
        ArrayList<Double> worldPercent = new ArrayList<>();

        report.getLanguagePopulationReport(conn, languages, speakers, worldPercent);
        assertNotNull(languages);
        assertNotNull(speakers);
        assertNotNull(worldPercent);
    }

    // ---------------------------------------------------------------------
    // Test: printLanguagePopulationReport with mismatched list sizes
    // ---------------------------------------------------------------------
    @Test
    void printLanguagePopulationReportMismatchedSizes()
    {
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<Long> speakers = new ArrayList<>();
        ArrayList<Double> worldPercent = new ArrayList<>();
        languages.add("Hindi");
        speakers.add(800000000L);
        // Missing worldPercent entry

        // Should print without crashing
        assertDoesNotThrow(() ->
                report.printLanguagePopulationReport(languages, speakers, worldPercent));
    }
}