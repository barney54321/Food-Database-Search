# Database Schemas
## Search
* id: INTEGER (PK)
* term: VARCHAR (unique)

## Food
* id: VARCHAR (PK)
* label: VARCHAR
* brand: VARCHAR
* category: VARCHAR
* categoryLabel: VARCHAR
* foodContentsLabel: VARCHAR
* imagePath: VARCHAR
* servingsPerContainer: DOUBLE
* enerckcal: DOUBLE
* procnt: DOUBLE
* fat: DOUBLE
* chocdf: DOUBLE
* fibtg: DOUBLE
    
## Measure
* size: VARCHAR (PK)
* url: VARCHAR (unique)

## Food-Measure
As food-mesaure is a many-to-many relation, a separate table is required
* foodID: VARCHAR
* measureSize: VARCHAR
* joinID: COMPOSITE(foodID, measureSize) (PK)

## Search-Result
As search-result is a many-to-many relation, a separate table is required
to show the join.
* searchID: INTEGER
* foodID: VARCHAR
* joinID: COMPOSITE(searchID, foodID) (PK)

## Nutrition
Food-nutrition is a one-to-many relation, so each Nutrition object can
just store their associated food object.
* foodID: VARCHAR
* size: VARCHAR
* id: COMPOSITE(foodID, size) (PK)
* uri: VARCHAR
* calories: INTEGER
* weight: DOUBLE

## DietLabel
* label: VARCHAR (PK)

## NutritionDiet
Nutrition-dietLabel is a many-to-many relation.
* label: FOREIGN(DietLabel)
* nutritionID: FOREIGN(Nutrition)
* joinID: COMPOSITE(label, nutritionID) (PK) 

## HealthLabel
* label: VARCHAR (PK)

## NutritionHealth
Nutrition-healthLabel is a many-to-many relation.
* label: FOREIGN(HealthLabel)
* nutritionID: FOREIGN(Nutrition)
* joinID: COMPOSITE(label, nutritionID) (PK) 

## CautionLabel
* label: VARCHAR (PK)

## NutritionCaution
Nutrition-cautionLabel is a many-to-many relation.
* label: FOREIGN(CautionLabel)
* nutritionID: FOREIGN(Nutrition)
* joinID: COMPOSITE(label, nutritionID) (PK) 


    /**
     * Returns the Nutrients present in the item.
     * @return The map of nutrients in the item.
     */
    Map<String, Nutrient> getTotalNutrients();

    /**
     * Returns the number of nutrients consumed everyday in item.
     * @return The map of daily nutrients in the item.
     */
    Map<String, Nutrient> getTotalDaily();

    /**
     * Returns the list of ingredients in the item.
     * @return The list of ingredients in the item.
     */
    List<Ingredient> getIngredients();