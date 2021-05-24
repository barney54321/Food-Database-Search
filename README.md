# SOFT3202 Exam: Food Database
SID: 480394196

## APIs
* [Food Database](https://developer.edamam.com/food-database-api)
* [Twilio](https://www.twilio.com)

## TDD Feature Commits
* Food
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/0ddf24701827e199d4133b342f608d7414448afc
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/dd07d26ac65f34f7e9d0b7a19ae6379b92402704
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/501b6a250c6c2b66a7c25623cd19929ef7a868fd
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a172c9a8f353d6354a977d59a67f9096827ce7ec
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4e3a5af2ec4a001dd23a6a6109aa01b3f63cac14
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/5e32e80438c945379f9a8c61c5c528e5af2ad7b9
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/69b53e5ea2333b6cce37cfecebfeefa02440f514
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/55c1dd9af5c049109b9f119d9311a30af519670b
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/b156253153ee2f5ed432254e7ec07c1be0123772
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/7c51b3eaec322e88e10baac2075cde650e3af15c
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a87c6a33237cec16bd2395b8e3bccc6a96649a5f
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/ef27d3bd9b6b0de1544b6a779807798ced72eb6b
* Ingredient
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/24fad954f287c716eecec2947e7e690cc64a921f
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/8ebf54a5311e505f3ac570d4ed14961dc3d36e4a
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/79001e9ce30646a2393a67dc2ce0a34b51ad234f
* Nutrient
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/9ca2a2037ba899ed7f48b8ff513c3ce7e9ea2500
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/1fec3f053597b5747e7a32c978fb099fe8642024
* Nutrition
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/58e4dea8232449111172443e5ff7c6fe6a88c5c8
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/79001e9ce30646a2393a67dc2ce0a34b51ad234f
* Controller
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/565104a3dde93ecd399d7185da72d7c1c3b44d4f
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/c61d70df42f6d2cbeccd53163207ea1e8a0af11c
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4ca956c4cd9017b7f3a3ddcc9ea647075aa59972
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/607579c581dedfe22a358d48fe6cad8063a98bd6
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/11bf154703c87fbbce11cb6d8dd81072d2958364
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/81eae402ec0ef3b4cc848a8af093c510aeabbc05
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/97a2ee4dcf5e4ca7f82cadadebe1eed281519560
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/26b77b8f9225dbc92344fea31e86c353a6d88fad
* ModelFacade
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/40fadd8ef9c6d46a086014bfaa8cdd6409fffbdd
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/03cc684fbda8fe390075558808c3f512aa672be6
    
## Setup
* Copy *credentials.json.example* and name the copy *credentials.json*
* Replace the value for "food-id" with the Food-Database-API ID
* Replace the value for "food-key" with the Food-Database-API authentication key
* Replace the value for "twilio-sid" with the Twilio Account SID
* Replace the value for "twilio-token" with the Twilio Application Token
* Replace the value for "twilio-phone-from" with the Twilio source phone number
* Replace the value for "twilio-phone-to" with your mobile number including country code (e.g. +61123456789)
    
## How to Run
* Use command `gradle run --args="offline"` to run in Offline mode
    * Offline mode will only work with search term "Hawaiian Pizza"
* Use command `gradle run --agrs="online"` to run in Online mode

## Level Implemented
PASS (for now)

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
* Placeholder image: https://socialistmodernism.com/placeholder-image/

## Style
The code abides by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).