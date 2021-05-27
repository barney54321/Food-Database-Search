# Database Schemas
## Search
* term: TEXT (PK)
* online: INTEGER (SQLite doesn't have booleans, so use 0,1)
* response: TEXT

## Nutrition
* foodID: TEXT
* measure: TEXT
* quantity: INTEGER
* online: INTEGER
* id: COMPOSITE(foodID, measure, quantity, online)
* response: TEXT