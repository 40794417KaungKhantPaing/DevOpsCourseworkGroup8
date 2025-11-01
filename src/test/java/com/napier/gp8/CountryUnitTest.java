package com.napier.gp8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountryUnitTest {

    @Test
    void testGettersAndSetters() {
        Country country = new Country();

        country.setCode("JPN");
        country.setCountryName("Japan");
        country.setContinent("Asia");
        country.setRegion("Eastern Asia");
        country.setSurfaceArea(377975.0);
        country.setIndepYear(660);
        country.setPopulation(125800000);
        country.setLifeExpectancy(84.6);
        country.setGnp(4937000.0);
        country.setGnpOld(4883000.0);
        country.setLocalName("Nippon");
        country.setGovernmentForm("Constitutional Monarchy");
        country.setHeadOfState("Emperor Naruhito");
        country.setCapital(1532);
        country.setCode2("JP");

        assertEquals("JPN", country.getCode());
        assertEquals("Japan", country.getCountryName());
        assertEquals("Asia", country.getContinent());
        assertEquals("Eastern Asia", country.getRegion());
        assertEquals(377975.0, country.getSurfaceArea());
        assertEquals(660, country.getIndepYear());
        assertEquals(125800000, country.getPopulation());
        assertEquals(84.6, country.getLifeExpectancy());
        assertEquals(4937000.0, country.getGnp());
        assertEquals(4883000.0, country.getGnpOld());
        assertEquals("Nippon", country.getLocalName());
        assertEquals("Constitutional Monarchy", country.getGovernmentForm());
        assertEquals("Emperor Naruhito", country.getHeadOfState());
        assertEquals(1532, country.getCapital());
        assertEquals("JP", country.getCode2());
    }

    @Test
    void testToString() {
        Country country = new Country();
        country.setCode("FRA");
        country.setCountryName("France");
        country.setContinent("Europe");
        country.setRegion("Western Europe");
        country.setSurfaceArea(551695.0);
        country.setIndepYear(843);
        country.setPopulation(67800000);
        country.setLifeExpectancy(82.5);
        country.setGnp(2715518.0);
        country.setGnpOld(2600000.0);
        country.setLocalName("France");
        country.setGovernmentForm("Republic");
        country.setHeadOfState("Emmanuel Macron");
        country.setCapital(2974);
        country.setCode2("FR");

        String result = country.toString();

        assertTrue(result.contains("France"));
        assertTrue(result.contains("Europe"));
        assertTrue(result.contains("Republic"));
        assertTrue(result.startsWith("Country{"));
        assertTrue(result.endsWith("}"));
    }
}
