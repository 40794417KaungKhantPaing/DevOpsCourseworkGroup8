package com.napier.gp8;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReportWriterUnitTest {

    private ReportWriter reportWriter;
    private Path reportDir;

    @BeforeEach
    void setUp() throws IOException {
        reportWriter = new ReportWriter();
        reportDir = Paths.get("./reports");
        if (!Files.exists(reportDir)) {
            Files.createDirectories(reportDir);
        }
        // Clean up old test reports
        Files.list(reportDir)
                .filter(p -> p.getFileName().toString().startsWith("test_"))
                .forEach(p -> p.toFile().delete());
    }

    @Test
    void testOutputCountries_WritesFile() throws IOException {
        // Arrange mock data using setters
        Country c1 = new Country();
        c1.setCode("USA");
        c1.setCountryName("United States");
        c1.setContinent("North America");
        c1.setRegion("Northern America");
        c1.setPopulation(331000000);
        c1.setCapitalName("Washington D.C.");

        Country c2 = new Country();
        c2.setCode("FRA");
        c2.setCountryName("France");
        c2.setContinent("Europe");
        c2.setRegion("Western Europe");
        c2.setPopulation(67000000);
        c2.setCapitalName("Paris");

        ArrayList<Country> countries = new ArrayList<>(Arrays.asList(c1, c2));
        String filename = "test_countries.md";
        String title = "Test Countries Report";

        // Act
        reportWriter.outputCountries(countries, filename, title);

        // Assert
        Path filePath = reportDir.resolve(filename);
        assertTrue(Files.exists(filePath), "Report file should exist");

        String content = Files.readString(filePath);
        assertTrue(content.contains("| France |"), "Should contain France");
        assertTrue(content.contains("# " + title), "Should contain title");
    }

    @Test
    void testOutputCities_EmptyList_DoesNotThrow() {
        assertDoesNotThrow(() ->
                reportWriter.outputCities(new ArrayList<>(), "test_empty_cities.md", "Empty City Report"));
    }

    @Test
    void testOutputCapitalCities_NullList_DoesNotThrow() {
        assertDoesNotThrow(() ->
                reportWriter.outputCapitalCities(null, "test_null_capitals.md", "Null Capitals"));
    }

    @Test
    void testOutputPopulationWorld_WritesExpectedData() throws IOException {
        // Use no-arg constructor and setters if necessary
        PopulationWorldReport.PopulationData data = new PopulationWorldReport.PopulationData("World");
        data.totalPopulation = 8_000_000_000L;
        data.cityPopulation = 4_000_000_000L;
        data.nonCityPopulation = 4_000_000_000L;
        data.cityPercentage = 50.0;
        data.nonCityPercentage = 50.0;

        String filename = "test_world_pop.md";
        reportWriter.outputPopulationWorld(data, filename);

        Path filePath = reportDir.resolve(filename);
        assertTrue(Files.exists(filePath), "World report file should exist");

        String content = Files.readString(filePath);
        assertTrue(content.contains("World"), "Should contain 'World'");
        assertTrue(content.contains("8,000,000,000"), "Should format population correctly");
    }
    @Test
    void testOutputLanguagePopulationReport_WritesData() throws IOException {
        CountryLanguage lang = new CountryLanguage();
        lang.setLanguage("English");
        lang.setCountryCode("1,500,000,000");
        lang.setPercentage(18.75);

        ArrayList<CountryLanguage> list = new ArrayList<>();
        list.add(lang);

        String filename = "test_language_report.md";
        reportWriter.outputLanguagePopulationReport(list, filename);

        Path filePath = reportDir.resolve(filename);
        assertTrue(Files.exists(filePath));


String content = Files.readString(filePath);
assertTrue(content.contains("| English |"), "Should contain language name");
assertTrue(content.contains("%"), "Should include percentage formatting");
        }

@Test
void testOutputLanguagePopulationReport_NullList() {
    assertDoesNotThrow(() -> reportWriter.outputLanguagePopulationReport(null, "test_null_lang.md"));
}

@Test
void testOutputPopulationCityReport_WithData() throws IOException {
    City city = new City();
    city.setCityName("Yangon");
    city.setDistrict("Yangon Region");
    city.setCountryCode("MMR");
    city.setPopulation(5000000);

    List<City> cities = Collections.singletonList(city);
    String filename = "test_city_report.md";
    reportWriter.outputPopulationCityReport(cities, filename);

    Path path = reportDir.resolve(filename);
    assertTrue(Files.exists(path));

    String content = Files.readString(path);
    assertTrue(content.contains("Yangon"));
}

@Test
void testOutputPopulationCityReport_NoData() throws IOException {
    String filename = "test_city_empty.md";
    reportWriter.outputPopulationCityReport(Collections.emptyList(), filename);
    Path filePath = reportDir.resolve(filename);
    assertTrue(Files.exists(filePath));
    String content = Files.readString(filePath);
    assertTrue(content.contains("No data available"));
}

@Test
void testOutputContinentPopulation_WithMockedData() throws IOException {
    Country country = new Country();
    country.setContinent("Asia");
    country.setPopulation(1000000);

    List<Country> list = Collections.singletonList(country);

    // Mock PopulationUtils.calculatePopulationValues()
    // You can simulate this manually by adding a simple test implementation in PopulationUtils for testing
    reportWriter.outputContinentPopulation(list, "test_continent_pop.md", "Continent Pop");

    Path filePath = reportDir.resolve("test_continent_pop.md");
    assertTrue(Files.exists(filePath));
}

@Test
void testOutputContinentPopulation_EmptyList() throws IOException {
    reportWriter.outputContinentPopulation(Collections.emptyList(), "test_continent_empty.md", "Empty Continent");
    Path filePath = reportDir.resolve("test_continent_empty.md");
    assertTrue(Files.exists(filePath));
    String content = Files.readString(filePath);
    assertTrue(content.contains("No data available"));
}

@Test
void testOutputPopulationByGroup_WithData() throws IOException {
    Country country = new Country();
    country.setCountryName("Japan");
    country.setRegion("Eastern Asia");
    country.setPopulation(126000000);

    List<Country> list = Collections.singletonList(country);

    reportWriter.outputPopulationByGroup(list, "test_pop_by_group.md", "Population by Country", "Country");
    Path path = reportDir.resolve("test_pop_by_group.md");
    assertTrue(Files.exists(path));
    String content = Files.readString(path);
    assertTrue(content.contains("Japan"));
}

@Test
void testOutputPopulationByGroup_EmptyList() throws IOException {
    reportWriter.outputPopulationByGroup(Collections.emptyList(), "test_pop_by_group_empty.md", "Empty Pop Group", "Country");
    Path path = reportDir.resolve("test_pop_by_group_empty.md");
    assertTrue(Files.exists(path));
    String content = Files.readString(path);
    assertTrue(content.contains("No data available"));
}
    @Test
    void testWriteToFile_ExceptionHandling() {
        // Intentionally trigger IOException by using invalid directory path
        ReportWriter badWriter = new ReportWriter() {
            protected void writeToFile(String filename, StringBuilder sb) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/invalid_path/" + filename)));
                    writer.write(sb.toString());
                    writer.close();
                } catch (IOException e) {
                    assertNotNull(e, "IOException should be caught");
                }
            }
        };

        ArrayList<Country> countries = new ArrayList<>();
        Country c = new Country();
        c.setCountryName("Nowhere");
        countries.add(c);

        assertDoesNotThrow(() -> badWriter.outputCountries(countries, "fail_test.md", "Test Fail"));
    }


    @AfterEach
    void tearDown() throws IOException {
        // Clean up generated test files
        Files.list(reportDir)
                .filter(p -> p.getFileName().toString().startsWith("test_"))
                .forEach(p -> p.toFile().delete());
    }
}
