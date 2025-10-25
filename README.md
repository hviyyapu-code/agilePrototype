#Architecture Prototype:
This repo demonstrates an architecture prototype using Use Case UC11 – Create User Story.
The goal is to validate the backend–frontend communication, database integration, and REST API functionality before full system development.
The prototype allows a Business Analyst to:
- Create new user stories
- View all existing user stories
- Store and retrieve them from a MySQL database
Frontend (HTML+JS) - Java+Javalin - Database (MySQL)

How to run? (All commands are for macOS)
1. Clone the repo using the following commands:
    git clone https://github.com/hviyyapu-code/agilePrototype.git
    cd agilePrototype/backend
2. Create a Database using MySQL with name - "agiletool" and create a table with name - user_story. It can be created using the following commands in terminal or can be created manually using MySQL web app.
    create database agiletool;
    use agiletool;
    create table user_story (
      id int auto_increment primary key,
      title varchar(255),
      description text,
      status varchar(50) default 'To-Do';
3. We will now run the backend using the following commands in terminal:
    mvn clean compile
    mvn exec:java -Dexec.mainClass="com.example.App"
4. You will see a success message, then open the frontend index.html file in browser.
5. Add the Title and Description of the User Story and hit 'Create Story'.
6. You can view the story that's added in the below space and can also see in the terminal using the query: select * from user_story; to see that the new user story details are added to the table.

