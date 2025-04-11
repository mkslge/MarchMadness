package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {

        /*Team maryland = new Team(2025, "Maryland", 4 );

        // Create a TeamCrud instance
        TeamCrud teamCrud = new TeamCrud();
        teamCrud.addWin(maryland);
        // Add Maryland team to the database
        int result = teamCrud.getTeamWins(maryland);
        System.out.println("Maryland has won " + result + " times");*/


        /*Simulation sim = new Simulation();
        MySQLConnection connection = new MySQLConnection();
        connection.getConnection();
        SpringApplication.run(Main.class, args);
        connection.closeConnection();*/
        int count = 0;
        MySQLConnection connection = new MySQLConnection();
        connection.getConnection();
        TeamCrud teamCrud = new TeamCrud();
        while(count < 100000) {


            Region midwest = new Region("/Users/markseeliger/Documents/Springboot/src/main/java/org/example/midwest2024.json");
            Region west = new Region("/Users/markseeliger/Documents/Springboot/src/main/java/org/example/west2024.json");
            Region east = new Region("/Users/markseeliger/Documents/Springboot/src/main/java/org/example/east2024.json");
            Region south = new Region("/Users/markseeliger/Documents/Springboot/src/main/java/org/example/south2024.json");

            System.out.println("Midwest: " + midwest.getWinner().getName());
            System.out.println("West: " + west.getWinner().getName());
            System.out.println("South: " + south.getWinner().getName());
            System.out.println("East: " + east.getWinner().getName());

            FinalFour finalFour = new FinalFour(west, east, south, midwest);
            System.out.println("Winner: " + finalFour.getChampion().getName());



            if(teamCrud.getTeamWins(finalFour.getChampion()) <= 0) {
                System.out.println("Y: " + finalFour.getChampion().getYear());
                teamCrud.add(finalFour.getChampion());
            }

            if (teamCrud.incrementWin(finalFour.getChampion())) {
                System.out.println("Added");
                System.out.println("Year of champ: " + finalFour.getChampion().getYear());
            } else {
                System.out.println("Not added");
            }
            count++;
        }
        connection.closeConnection();
        //System.out.println("Server @ http://localhost:8080");
    }
}
