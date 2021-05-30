# SOFT3202 Exam: Food Database
SID: 480394196

## APIs
* [Food Database](https://developer.edamam.com/food-database-api)
* [Twilio](https://www.twilio.com)

## TDD Feature Commits
### Controller
* Initial Controller class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/565104a3dde93ecd399d7185da72d7c1c3b44d4f
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/c61d70df42f6d2cbeccd53163207ea1e8a0af11c
* Adding Twilio connection to Controller
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4ca956c4cd9017b7f3a3ddcc9ea647075aa59972
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/607579c581dedfe22a358d48fe6cad8063a98bd6
* Generate Food report with Food instead of message
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/11bf154703c87fbbce11cb6d8dd81072d2958364
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/81eae402ec0ef3b4cc848a8af093c510aeabbc05
* Connecting Controller to ModelFacade instead of directly to APIs
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/97a2ee4dcf5e4ca7f82cadadebe1eed281519560
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/26b77b8f9225dbc92344fea31e86c353a6d88fad
* Adding defensive checks for generate report
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/f70f260e8ecc9081fe35841d533914497b5e9f91
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/b412a2e481b649c1fc7c4c09e04bc822b18f93c1
* Adding cache option to search()
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/b99ef8b8768776628cd13899b015733958a1c4f1
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/79ea9a5c9153af98346514747518674eb9101d4a
* Adding cache option to getNutrition()
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/1f38243eb8e6115d89f68d7eda381193136be53c
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/9cb863813690b6939fd5a847d2f463f4f06b073e
* Connecting Controller to concurrency methods in ModelFacade
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/e5472f0bbaf68c7c534eb7b4a166b9c51d7a4152
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/99fdb35eab4d326dcd22bc340f0a17d650582842
* Adding SQL injection prevention methods for search()
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/64e0e51d8f35992636358fdadb6c325ea97f60cc
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4b703b93d64901467e8b4f47b990dc9b97a67da4
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/57d33dc87659b7ac151d0ec6ee763a3af1d3e21b
### ModelFacade
* Initial ModelFacade class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/40fadd8ef9c6d46a086014bfaa8cdd6409fffbdd
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/03cc684fbda8fe390075558808c3f512aa672be6
* Adding useCache to ModelFacade
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/15805a1040fa5ea0c3b2403c4a293f88248d58e6
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/421836620eaaaedf9f0f1ee5e93b3eee93026a05
* Adding concurrency methods (e.g. queueSearch())
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/d2345a47180c8f7a1a0c91168deeede686936ffb
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a031b627c26d815548fe0d6e343469f711584583
* Adding Platform.runLater()
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a5da6be217a5de8f1b742d9ab4290ed7d40ecc67
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/dac3700427269ab85837222af92d41be8912fa1b
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/584e51faa0fdbf4ed2cf35832189cdf9fe3b2b59
### FoodApi:
* Initial FoodApi class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a0e075d64216d2d6045d40844b928e2455dbb8db
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/2e61dc4ed169f173067b878cee10cd1ab9cb0fbb
* Fixing test cases for SQL queries
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/edb4ac401a9e6ed618bd87e1cd5f73bca9cb83c2
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/012129a3537e83794d41a2c2630a13a0af2f5651
* Adding useCache to search()
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/b4b365ba9319e609ab2db6c8229afa3cb48bdced
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4e125ae4916ff5fe246be254cbb30137f6ec6f15
* Adding useCache to getNutrition()
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/2afdba5bc760dc788f841b3a770e83e029b5e42c
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4cb80fff7dbe2ff6cf40320230d1aa8bc8a4ab46
* Using "replace" instead of "insert" for SQL queries
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/8ca2f4671b10349b04a95727f745e454ba6fbf48
### Food
* Initial Food class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/0ddf24701827e199d4133b342f608d7414448afc
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/dd07d26ac65f34f7e9d0b7a19ae6379b92402704
* Implementing Lazy Load for Food Nutrition
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/501b6a250c6c2b66a7c25623cd19929ef7a868fd
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a172c9a8f353d6354a977d59a67f9096827ce7ec
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/4e3a5af2ec4a001dd23a6a6109aa01b3f63cac14
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/5e32e80438c945379f9a8c61c5c528e5af2ad7b9
* Changing getMeasure() to return URI of measure instead of quantity
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/69b53e5ea2333b6cce37cfecebfeefa02440f514
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/55c1dd9af5c049109b9f119d9311a30af519670b
* Retrieve the Nutrition object based on measure
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/b156253153ee2f5ed432254e7ec07c1be0123772
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/7c51b3eaec322e88e10baac2075cde650e3af15c
* Generate Food report
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/a87c6a33237cec16bd2395b8e3bccc6a96649a5f
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/ef27d3bd9b6b0de1544b6a779807798ced72eb6b
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/ac11daf3fe9a68fff03d2af8b84a1062687ab820
### Nutrient
* Initial Nutrient class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/9ca2a2037ba899ed7f48b8ff513c3ce7e9ea2500
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/1fec3f053597b5747e7a32c978fb099fe8642024
### Nutrition
* Initial Nutrition class
    * **RED**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/58e4dea8232449111172443e5ff7c6fe6a88c5c8
    * **GREEN**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/79001e9ce30646a2393a67dc2ce0a34b51ad234f
    * **REFACTOR**: https://github.sydney.edu.au/aest9988/SCD2_2021_Exam/commit/9c5b75923c4ce705afc8ed505d6c2acdb88d2dad
    
## Setup
* Copy *credentials.json.example* and name the copy *credentials.json*
* Replace the value for "food-id" with the Food-Database-API ID
* Replace the value for "food-key" with the Food-Database-API authentication key
* Replace the value for "twilio-sid" with the Twilio Account SID
* Replace the value for "twilio-token" with the Twilio Application Token
* Replace the value for "twilio-phone-from" with the Twilio source phone number
* Replace the value for "twilio-phone-to" with your mobile number including country code (e.g. +61123456789)
* Download SQLite, following [this tutorial or similar](https://www.tutorialspoint.com/sqlite/sqlite_installation.htm)
    
## How to Run
* Use command `gradle run --args="offline"` to run in Offline mode
    * Offline mode will only work with search term "Hawaiian Pizza"
* Use command `gradle run --agrs="online"` to run in Online mode

## Level Implemented
Distinction

## References
* Placeholder image: https://socialistmodernism.com/placeholder-image/
* SQLite-JDBC usage inspired by: https://github.com/xerial/sqlite-jdbc#usage

## Style
The code abides by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).