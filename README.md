# FilmQueryProject

![](./film-query.png)

Week 7 homework by Justin Martz

## Description

Film Data Searcher 3000 presents the user with three options: look up film by ID, look up film keyword, and quit. Looking up a film by ID takes a film ID number and returns the appropriate response. Looking up a film by keyword searches the film's title and description for an instance of the keyword and returns the results of any titles and descriptions that match. The third option lets the user quit the program.

Film results display the film's title, release year, rating, description, language, and cast.

## Technologies Used

- Java
- MySQL
- Java Database Connectivity 

## Lessons Learned

- How to craft and use database queries to pinpoint results from a database

- How to use the Java Database Connectivity API

- Figuring how to code the logic after the ResultSet cursor had been moved by testing for false.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;When using an if statement to check if the ResultSet was empty or not like this `if (searchResult.next() == false)` the results would only show the second row and beyond. I changed the logic to a `do-while` loop to work on the ResultSet at least once in the body of the `do` before iterating through it in the `while`