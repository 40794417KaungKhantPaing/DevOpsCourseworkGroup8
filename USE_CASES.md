# Use Cases

This document outlines the key **use cases** of the **Population Reporting System**. Each use case describes a specific
user action, including the required conditions for that action to be executed, the flow of events, and the expected results.

You can also refer to the visual **Use Case Diagram** for a more intuitive understanding of how the system functions.

![Use Case Diagram](https://www.plantuml.com/plantuml/png/hPNHRjem58Rl_HH7kTbLWHJIm5IXqg9DawQf6jq3k8a1Ys17ziagDl7kinFYM8QibTgx7__7_duV7PCxZLHXjIz8mTO8A47npHOXvufboAKWUiT5IHNTGy45mqF9G2hSIi-eDCkePl2N001yaG9N_0-3EAb_BvIIBsvnsgqzRNcs4qnh60tIUlb82CrGAeYM52ai12qE6YEW6fOBGdAp-6n0ZMrLOzqJKj9iHpV6UPHbLL2R77woqYJbOWChSmhRHtK-b_RNGsntHryPGYOhWOep3POH1NNNTTsZuiAlv09mo-15gY8VPb8O4XCuL6nZuQg6wuW4PuqTyqcMyECLyiIHNVqx-OdZFt3imzxu5-RuActOf3eCSwvDk0nxyMbu_VuGq_3k7nnbrdM2bXnfyR-En2E_9Ktzc_BqukCuQCLRKbo7FVcWBDrKVgTYKzcNj4AAN2FFUg9qetbvK_Fwpb7T07fFTNc9u4zXsTNADHJyjttMNBJNbWg4H6zXRLRwHiEr8GIc7X0bqiB7FX-0UaSs7UwPkQ59uM78aP1FiCYm2IMQVrPDbWkukh9WfyPE9KwbJisw7N4dnvrCBEURo8egPmRV9DTRNcfYN1WCvlM_sMUulULDrNnE3FNaJCwSvEJS18wnqfCrFTiqEpdnwCoAuzOBKrXpt9gJ2sRIcc4Mtqi3p_QZ_LRuhRURRC-Mz_RwJYLEfKxDd8hZJeuxsMuwaZicSlif_GS0)

---
# Population Reporting System – Use Case Documentation

## User Description
The user, a Data Analyst interacts with the Population Reporting System to view population, country, city, and language 
information. The User can view data for all countries, top N countries or cities, capital cities, and language 
statistics. The system generates reports at different levels (world, continent, region, country, district, or city).

---

### UC1. Get Countries Data 
Description: Allows the analyst to retrieve all countries from the database by population sorting
**Main Flow:**
1. The user gets country data.
2. System queries data from the database.
3. System returns the data.
4. System calls "Print Countries Reports".

### UC2. Print Countries in the world/continent/region reports
Description: Allows the analyst to generate country reports for different levels 
**Main Flow:**
1. System gets country data.
2. System makes reports for world, continent, or region.
3. System shows the reports.

---

### UC3. Get Top N Countries Data
Description: Allows the analyst to get the top N populated countries by sorted population
**Main Flow:**
1. The user asks for top N countries by population.
2. System fetches top N countries from database.
3. System returns the data.
4. System calls "Print Top N Countries Reports".


### UC4. Print Top N countries in the world/continent/region reports
Description: Generate reports for the top N populated cities by scope
**Main Flow:**
1. System gets top N country data.
2. System makes reports for world, continent, or region.
3. System shows the reports.

---

### UC5. Get Cities Data
Description: Retrieve city data sorted by population in descending order 
**Main Flow:**
1. The User asks for city data.
2. System fetches city data from database.
3. System returns the data.
4. System calls "Print Cities Reports".

### UC6. Print Cities in the world/continent/region/country/district reports
Description: Generate reports of cities for different levels
**Main Flow:**
1. System gets city data.
2. System makes reports for world, continent, region, country, or district.
3. System shows the reports.

---

### UC7. Get Top N Cities Data
Description: Allows user to get the data of top N cities at different levels in population descending order
**Main Flow:**
1. User asks for the data top N cities by population.
2. System fetches top N cities from database.
3. System returns the data.
4. System calls "Print Top N Cities Reports".

### UC8. Print Top N Cities in the world/continent/region/country/district reports
Description: Generate the top N cities reports for different levels
**Main Flow:**
1. System gets top N city data.
2. System makes reports for world, continent, region, country, or district.
3. System shows the reports.

---

### UC9. Get Capital Cities Data
Description: Provide user the capital city data by sorted population from largest to smallest
**Main Flow:**
1. User asks for capital city data.
2. System fetches capital city data from database.
3. System returns the data.
4. System calls "Print Capital Cities Reports".

### UC10. Print Capital Cities in the world/continent/region reports
Description: Display capital cities in format reports 
**Main Flow:**
1. System gets capital city data.
2. System makes reports for the levels - world, continent, or region.
3. System shows the reports.

---

### UC11. Get Top N Capital Cities Data
Description: Allows the user access the top N capital cities data by population
**Main Flow:**
1. User asks for top N capital cities ordered by population.
2. System fetches data from database.
3. System returns the data.
4. System calls "Print Top N Capital Cities Reports".

### UC12. Print Top N Capital Cities in the world/continent/region reports
Description: Generate the sorted top N capital cities reports 
**Main Flow:**
1. System gets top N capital city data.
2. System makes reports for world, continent, or region.
3. System shows the reports.

---

### UC13. Get Language Statistics Data
Description: Allows the user get the data of population for languages - Chinese, English, Hindi, Spanish and Arabic.
**Main Flow:**
1. User asks for population data by language speaking data.
2. System fetches language data from database.
3. System returns the data.
4. System calls "Print Language Statistics Report".

### UC14. Print Language Statistics Report
Description: Display the number of people by speaking languages 
**Main Flow:**
1. System gets language data.
2. System makes the report.
3. System shows the report.

---

### UC15. Get Population Data
Description: Allows the user to get population data from database
**Main Flow:**
1. User asks for population data.
2. System fetches population data from database.
3. System returns the data.
4. System calls "Print People Population Reports" and "Print Total Population".

### UC16. Print population of living in cities or not in cities for continent/region/country reports
Description: Display the population data of people live in cities and not in cities area for different levels
**Main Flow:**
1. System gets population data.
2. System makes reports for continent, region, or country.
3. System shows the reports.

### UC17. Print Total Population by a continent/world/region/country/district/city reports
Description: Display the total population of a specific defined continent or other levels
**Main Flow:** 
1. System gets population data.
2. System calculates total population for world, continent, region, country, district, and city.
3. System shows the reports.

---

## Notes
- All `Get Data` use cases fetch data from the database.
- `Print` use cases are automatically called after fetching data.
- All actions follow a main flow from request to report generation.

---

### **Link to Use Case Diagram**
You can also refer to the detailed **Use Case Diagram** for visual clarity:

[Use Case Diagram](./use_case_diagram.puml)

---

This **USE_CASES.md** document outlines all the core user actions within the **Population Reporting System**. 
Each use case provides a detailed overview of what happens during the process, including pre-conditions, main flow, 
and post-conditions.
