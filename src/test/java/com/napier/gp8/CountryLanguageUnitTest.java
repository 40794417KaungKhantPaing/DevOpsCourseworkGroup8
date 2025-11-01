package com.napier.gp8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountryLanguageUnitTest {

    @Test
    void testGettersAndSetters() {
        CountryLanguage cl = new CountryLanguage();

        Country country = new Country();
        country.setCountryName("Japan");

        cl.setCountryCode("JPN");
        cl.setLanguage("Japanese");
        cl.setIsOfficial("T");
        cl.setPercentage(99.2);
        cl.setCountry(country);

        assertEquals("JPN", cl.getCountryCode());
        assertEquals("Japanese", cl.getLanguage());
        assertEquals("T", cl.getIsOfficial());
        assertEquals(99.2, cl.getPercentage());
        assertEquals(country, cl.getCountry());
        assertEquals("Japan", cl.getCountry().getCountryName());
    }

    @Test
    void testToString() {
        CountryLanguage cl = new CountryLanguage();
        cl.setCountryCode("FRA");
        cl.setLanguage("French");
        cl.setIsOfficial("T");
        cl.setPercentage(100.0);

        Country country = new Country();
        country.setCountryName("France");
        cl.setCountry(country);

        String result = cl.toString();

        assertTrue(result.contains("FRA"));
        assertTrue(result.contains("French"));
        assertTrue(result.contains("T"));
        assertTrue(result.contains("100.0"));
        assertTrue(result.contains("France"));
        assertTrue(result.startsWith("CountryLanguage{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    void testDefaultValues() {
        CountryLanguage cl = new CountryLanguage();
        assertNull(cl.getCountryCode());
        assertNull(cl.getLanguage());
        assertNull(cl.getIsOfficial());
        assertEquals(0.0, cl.getPercentage());
        assertNull(cl.getCountry());
    }
}
