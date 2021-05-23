# SOFT3202 Exam: Food Database
SID: 480394196

## APIs
* [Food Database](https://developer.edamam.com/food-database-api)
* [Twilio](https://www.twilio.com)

## TDD Feature Commits
* Base FoodImpl Class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/0ddf24701827e199d4133b342f608d7414448afc
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/dd07d26ac65f34f7e9d0b7a19ae6379b92402704
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/501b6a250c6c2b66a7c25623cd19929ef7a868fd
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a172c9a8f353d6354a977d59a67f9096827ce7ec
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4e3a5af2ec4a001dd23a6a6109aa01b3f63cac14
* Base Ingredient Class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/24fad954f287c716eecec2947e7e690cc64a921f
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/8ebf54a5311e505f3ac570d4ed14961dc3d36e4a
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/79001e9ce30646a2393a67dc2ce0a34b51ad234f
* Base Nutrient Class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/9ca2a2037ba899ed7f48b8ff513c3ce7e9ea2500
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/1fec3f053597b5747e7a32c978fb099fe8642024
* Base Nutrition Class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/58e4dea8232449111172443e5ff7c6fe6a88c5c8
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/79001e9ce30646a2393a67dc2ce0a34b51ad234f
* Base Facade Class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/565104a3dde93ecd399d7185da72d7c1c3b44d4f
    
## Setup
* Copy *credentials.json.example* and name the copy *credentials.json*
* Replace the value for "food-id" with the Food-Database-API ID
* Replace the value for "food-key" with the Food-Database-API authentication key
* Replace the value for "twilio-sid" with the Twilio Account SID
* Replace the value for "twilio-token" with the Twilio Application Token
    
## How to Run
* Use command `gradle run --args="offline"` to run in Offline mode
    * Offline mode will only work with search term "Hawaiian Pizza"
* Use command `gradle run --agrs="online"` to run in Online mode

## Level Implemented
DISTINCTION (hopefully)

### Pass
You will be given an entity, and you need to display information about it. This entity is derived from your input
API. There may be multiple steps required to build this entity. You may need to perform an operation on this
entity after you have derived it. Your task is to display this entity in a way that would be useful to a user –
detailed UI requirements will not be mandated, but you should ensure that the presented information of your
entity is easily readable, and the interaction is simple.

Instructions are given about the report that should be produced for your output API. This report is related to
the entity you have built. 

### Credit
Cached items: this is something you need to cache in your local SQLite database each time it is retrieved from
the API. If a user requests a matching item, you need to ask the user if they would like to just use the cached
version, or grab a fresh copy from the API (and update the DB version along with it). 

For those wishing to achieve a Credit score for their exam or higher (65% and up) you will need to include a
SQLite database as part of your application. You can use the [SQLite JDBC library](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.34.0) to communicate with this
database which has sample code [here](https://github.com/xerial/sqlite-jdbc). 

You will need to decide on the database schema and other properties yourself. You can either have your
application set this schema up on first run, or you may include a .SQL file to do this that your marker will run
manually.
Information on what to use this database for is dependent on your Input API and listed in that section. 

### Distinction
The distinction requirement is the same for all: you need to separate out the GUI thread from the API requests
/ database access thread. The GUI must remain live and responsive during API request. It is suggested to use
the provisions in your chosen GUI library to help with this.

For those wishing to achieve a Distinction score for their exam or higher (75% and up) you will need to include
some basic concurrency. This should allow the GUI/View to operate independently of the operations the
Model is conducting. You should have the GUI drawing and events on 1 thread, and the Model operations
occurring on another thread. Most GUI libraries include dedicated support for this paradigm, and will usually
refer to the Model thread or threads as ‘Workers’.

You do not need to achieve true concurrency in your Model for this requirement – a single thread GUI thread
and a single thread handling Model calls is all that is required. This should limit the need for handling race
conditions or dealing with resource contention. 

## References

## Style
The code abides by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).