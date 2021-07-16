# Food Database Search

This program was made for the 2021 SOFT3202: Software Construction and Design 2 exam at the University of Sydney. 
The specifications required us to create a program that read in data from an input API (in my case, Food Database) 
and present in both a GUI application and via an output API (Twilio). Note that permission was granted by the lecturer 
for us to publish our work as the exam will not be re-used by the university.

## APIs
* [Food Database](https://developer.edamam.com/food-database-api)
* [Twilio](https://www.twilio.com)
    
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
* Use command `gradle run --args="offline offline"` to run in Offline mode
    * Offline mode will only work with search term "Hawaiian Pizza"
    * Offline mode for Twilio will print the message to terminal
* Use command `gradle run --agrs="online online"` to run in Online mode

## References
* Placeholder image: https://socialistmodernism.com/placeholder-image/
* SQLite-JDBC usage inspired by: https://github.com/xerial/sqlite-jdbc#usage

## Style
The code abides by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).