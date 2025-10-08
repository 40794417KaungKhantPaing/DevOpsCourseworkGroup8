package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 *The Language population generates a report of the amount of speakers of major languages and how much they occupy the world population.
 * Coding Standards:
 * - Class names: PascalCase
 * - Method names: camelCase
 * - Descriptive variable names
 * - Validate inputs before use
 * - Statement and ResultSet Use try-with-resources.
 * - Use ArrayList consistently
 */
public class Language_Population {

    /**
     * Retrieves the population and world percentage of speakers for major languages.
     * Languages: Chinese, English, Hindi, Spanish, Arabic.
     * @param conn Active database connection (must not be null)
     * @param languages ArrayList to store language names
     * @param speakers ArrayList to store number of speakers
     * @param worldPercent ArrayList to store world population percentage
     */

    public void getLanguagePopulationReport(Connection conn,
                                            ArrayList<String> languages,
                                            ArrayList<Long> speakers,
                                            ArrayList<Double> worldPercent) {

        // Validate database connection
        if (conn == null) {
            System.err.println("Database connection is null. Cannot generate language population report.");
            return;
        }

        // Use try-with-resources for Statement
        try (Statement stmt = conn.createStatement()) {

            // SQL query in strSelect style
            String strSelect =
                    "SELECT cl.Language, " +
                            "SUM((c.Population * cl.Percentage) / 100) AS Speakers, " +
                            "ROUND(SUM((c.Population * cl.Percentage) / 100) / " +
                            "(SELECT SUM(Population) FROM country) * 100, 2) AS WorldPercentage " +
                            "FROM countrylanguage cl " +
                            "JOIN country c ON cl.CountryCode = c.Code " +
                            "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                            "GROUP BY cl.Language " +
                            "ORDER BY Speakers DESC";

            // Try-with-resources for ResultSet
            try (ResultSet rs = stmt.executeQuery(strSelect)) {

                // Extract data into the three lists
                while (rs.next()) {
                    languages.add(rs.getString("Language"));
                    speakers.add(rs.getLong("Speakers"));
                    worldPercent.add(rs.getDouble("WorldPercentage"));
                }
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQL Error retrieving language population report.");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();

            // Clear lists on failure
            languages.clear();
            speakers.clear();
            worldPercent.clear();
        }

        // Check if no data was retrieved
        if (languages.isEmpty()) {
            System.out.println("Warning: No language population data found.");
        }
    }

    /**
     * Prints the language population report in a formatted table.
     * @param languages ArrayList of language names
     * @param speakers ArrayList of number of speakers
     * @param worldPercent ArrayList of world population percentages
     */
    public void printLanguagePopulationReport(ArrayList<String> languages,
                                              ArrayList<Long> speakers,
                                              ArrayList<Double> worldPercent) {

        // Validate lists
        if (languages == null || languages.isEmpty()) {
            System.out.println("No data available for language population report.");
            return;
        }
        // Print header
        System.out.println("\nLanguage Population Report:");
        System.out.printf("%-15s %-20s %-20s%n", "Language", "Speakers", "% of World Population");
        System.out.println("------------------------------------------------------------");

        // Print each row
        for (int i = 0; i < languages.size(); i++) {
            System.out.printf("%-15s %-20d %-20.2f%n",
                    languages.get(i),
                    speakers.get(i),
                    worldPercent.get(i));
        }

        System.out.println("------------------------------------------------------------");
    }
}