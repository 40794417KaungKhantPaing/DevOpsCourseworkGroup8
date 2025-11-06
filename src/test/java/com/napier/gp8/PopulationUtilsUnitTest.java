package com.napier.gp8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationUtilsUnitTest {

    @Test
    void testCalculatePopulationValues_NormalCase() {
        Country country = new Country();
        country.setPopulation(1000);
        country.setGnp(400.0);
        country.setGnpOld(600.0);

        PopulationUtils.PopValues values = PopulationUtils.calculatePopulationValues(country);

        assertEquals(1000, values.total());
        assertEquals(400, values.city());
        assertEquals(600, values.nonCity());
        assertEquals(40.0, values.cityPercent(), 0.001);
        assertEquals(60.0, values.nonCityPercent(), 0.001);
    }

    @Test
    void testCalculatePopulationValues_WithNullGnp() {
        Country country = new Country();
        country.setPopulation(800);
        country.setGnp(null);
        country.setGnpOld(400.0);

        PopulationUtils.PopValues values = PopulationUtils.calculatePopulationValues(country);

        assertEquals(800, values.total());
        assertEquals(0, values.city());
        assertEquals(400, values.nonCity());
        assertEquals(0.0, values.cityPercent(), 0.001);
        assertEquals(50.0, values.nonCityPercent(), 0.001);
    }

    @Test
    void testCalculatePopulationValues_WithNullGnpOld() {
        Country country = new Country();
        country.setPopulation(500);
        country.setGnp(200.0);
        country.setGnpOld(null);

        PopulationUtils.PopValues values = PopulationUtils.calculatePopulationValues(country);

        assertEquals(500, values.total());
        assertEquals(200, values.city());
        assertEquals(0, values.nonCity());
        assertEquals(40.0, values.cityPercent(), 0.001);
        assertEquals(0.0, values.nonCityPercent(), 0.001);
    }

    @Test
    void testCalculatePopulationValues_ZeroPopulation() {
        Country country = new Country();
        country.setPopulation(0);
        country.setGnp(100.0);
        country.setGnpOld(50.0);

        PopulationUtils.PopValues values = PopulationUtils.calculatePopulationValues(country);

        assertEquals(0, values.total());
        assertEquals(100, values.city());
        assertEquals(50, values.nonCity());
        assertEquals(0.0, values.cityPercent(), 0.001);
        assertEquals(0.0, values.nonCityPercent(), 0.001);
    }

    @Test
    void testCalculatePopulationValues_AllNulls() {
        Country country = new Country();
        country.setPopulation(0);
        country.setGnp(null);
        country.setGnpOld(null);

        PopulationUtils.PopValues values = PopulationUtils.calculatePopulationValues(country);

        assertEquals(0, values.total());
        assertEquals(0, values.city());
        assertEquals(0, values.nonCity());
        assertEquals(0.0, values.cityPercent(), 0.001);
        assertEquals(0.0, values.nonCityPercent(), 0.001);
    }
}
