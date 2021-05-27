# Database Schemas
## Search
* term: TEXT (PK)
* response: TEXT

## Nutrition
* food: TEXT
* measure: TEXT
* quantity: INTEGER
* id: COMPOSITE(foodID, measure)
* response: TEXT

## Creation Code
```'*.sqlite3-console
CREATE TABLE IF NOT EXISTS Search (
    term TEXT PRIMARY KEY,
    response TEXT
);

CREATE TABLE IF NOT EXISTS Nutrition (
    food TEXT NOT NULL,
    measure TEXT NOT NULL,
    response TEXT,
    PRIMARY KEY(food, measure)
);
```