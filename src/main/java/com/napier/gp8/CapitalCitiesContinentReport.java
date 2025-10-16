package com.napier.gp8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * CapitalCitiesContinentReport extends CapitalCitiesReportBase and provides
 * methods to retrieve and print capital cities in a specific continent by population.
 */
public class CapitalCitiesContinentReport extends CapitalCitiesReportBase {

    /**
     * Get all capital cities in a specific continent, ordered by population descending
     *
     * @param conn Database connection object
     * @param continent Continent name to filter countries
     * @return ArrayList of City objects
     */
    public ArrayList<City> getAllCapitalCitiesInContinentByPopulation(Connection conn, String continent) {
        ArrayList<City> capitals = new ArrayList<>(); // ArrayList to hold the retrieved capital cities data

        // SQL query to get capitals in the specified continent, ordered by population
        String query = "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                "co.Name AS CountryName FROM country co " +
                "JOIN city c ON co.Capital = c.ID " +
                "WHERE co.Continent = ? ORDER BY c.Population DESC";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setString(1, continent); // safe binding
            try (ResultSet rs = pStmt.executeQuery()) {

                //calling method form base class for building list of capitals
                capitals = buildCapitalCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            //Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving capital cities report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState()); //SQL state error
            e.printStackTrace();
            return capitals; //return safely with an empty list.
        }

        return capitals;
    }

    /**
     * Get top N capital cities in a specific continent by population
     *
     * @param conn Database connection object
     * @param continent Continent name to filter countries
     * @param numberOfCapitalCities Number of top capitals to retrieve
     * @return ArrayList of City objects
     */
    public ArrayList<City> getTopNCapitalCitiesInContinentByPopulation(Connection conn, String continent, int numberOfCapitalCities) {
        ArrayList<City> capitals = new ArrayList<>();
        String query = "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                "co.Name AS CountryName FROM country co " +
                "JOIN city c ON co.Capital = c.ID " +
                "WHERE co.Continent = ? ORDER BY c.Population DESC LIMIT ?";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setString(1, continent); // safe binding
            pStmt.setInt(2, numberOfCapitalCities); //safe binding         // safe binding
            try (ResultSet rs = pStmt.executeQuery()) {

                capitals = buildCapitalCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            //Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving capital cities report due to a database issue:");
            System.err.println("Error Code: " + e.getErrorCode()); //Error code
            e.printStackTrace();
            return capitals; //return safely with an empty list.
        }

        return capitals;
    }

    /**
     * Print all capital cities in a specific continent
     *
     * @param capitals List of City objects to print
     * @param continent Continent name for the report title
     */
    public void printAllCapitalCitiesInContinentByPopulation(ArrayList<City> capitals, String continent) {
        //call general print method from base class
        printCapitalCities(capitals, "All Capital Cities in Continent: " + continent + " Report");
    }

    /**
     * Print top N capital cities in a specific continent
     *
     * @param capitals List of City objects to print
     * @param continent Continent name for the report title
     * @param numberOfCapitalCities Number of top capitals displayed
     */
    public void printTopNCapitalCitiesInContinentByPopulation(ArrayList<City> capitals, String continent, int numberOfCapitalCities) {
        printCapitalCities(capitals, "Top " + numberOfCapitalCities + " Capital Cities in Continent: " + continent + " Report");
    }
}
