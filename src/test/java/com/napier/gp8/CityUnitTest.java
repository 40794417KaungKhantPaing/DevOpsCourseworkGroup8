package com.napier.gp8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CityUnitTest {

    @Test
    void testGettersAndSetters() {
        City city = new City();

        Country country = new Country();
        country.setCountryName("Japan");

        city.setId(1);
        city.setCityName("Tokyo");
        city.setCountryCode("JPN");
        city.setDistrict("Tokyo-to");
        city.setPopulation(37833000);
        city.setCountry(country);

        assertEquals(1, city.getID());
        assertEquals("Tokyo", city.getCityName());
        assertEquals("JPN", city.getCountryCode());
        assertEquals("Tokyo-to", city.getDistrict());
        assertEquals(37833000, city.getPopulation());
        assertEquals(country, city.getCountry());
        assertEquals("Japan", city.getCountry().getCountryName());
    }

    @Test
    void testToString() {
        City city = new City();
        city.setId(2);
        city.setCityName("Paris");
        city.setCountryCode("FRA");
        city.setDistrict("Île-de-France");
        city.setPopulation(2148327);

        Country country = new Country();
        country.setCountryName("France");
        city.setCountry(country);

        String result = city.toString();

        assertTrue(result.contains("Paris"));
        assertTrue(result.contains("FRA"));
        assertTrue(result.contains("Île-de-France"));
        assertTrue(result.contains("2148327"));
        assertTrue(result.contains("France"));
        assertTrue(result.startsWith("City{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    void testDefaultValues() {
        City city = new City();
        assertEquals(0, city.getID());
        assertNull(city.getCityName());
        assertNull(city.getCountryCode());
        assertNull(city.getDistrict());
        assertEquals(0, city.getPopulation());
        assertNull(city.getCountry());
    }
}
