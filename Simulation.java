package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Character.*;


public class Simulation {
    String FILE_PATH = "/Users/markseeliger/Documents/Springboot/src/main/java/org/example/data.txt";
    private String[] winner;
    private String[] finalTwoExit;
    private String[] finalFourExit;
    private String[] eliteEightExit;
    private String[] sweetSixteenExit;
    private String[] secondRoundExit;
    private String[] firstRoundExit;

    public Simulation() throws IOException{
        //create arrays for each round
        winner = new String[1];
        finalTwoExit = new String[1];
        finalFourExit = new String[2];
        eliteEightExit = new String[4];
        sweetSixteenExit = new String[8];
        secondRoundExit = new String[16];
        firstRoundExit = new String[32];
        FileInputStream fis = new FileInputStream(FILE_PATH);

        String data;
        HashMap<String, Integer> seeding = new HashMap<>();
        Graph<Integer> graph = new Graph<>();
        HashMap<String, Team> teams = new HashMap<>(); //hashmap of teams
        Bracket bracket = new Bracket();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(FILE_PATH));
        int index = 0;
        //add all teams to seed
        graph.addEdge(0,1,true);
        seeding.put("Connecticut",1);
        seeding.put("Houston",1);
        seeding.put("Purdue",1);
        seeding.put("North Carolina",1);
        seeding.put("Arizona",2);
        seeding.put("Iowa St.",2);
        seeding.put("Tennessee",2);
        seeding.put("Marquette",2);
        seeding.put("Illinois",3);
        seeding.put("Creighton",3);
        seeding.put("Baylor",3);
        seeding.put("Kentucky",3);
        seeding.put("Auburn",4);
        seeding.put("Duke",4);
        seeding.put("Alabama",4);
        seeding.put("Kansas",4);
        seeding.put("Gonzaga",5);
        seeding.put("Wisconsin",5);
        seeding.put("Saint Mary's",5);
        seeding.put("San Diego St.",5);
        seeding.put("BYU",6);
        seeding.put("Texas Tech",6);
        seeding.put("Clemson",6);
        seeding.put("South Carolina",6);
        seeding.put("Texas",7);
        seeding.put("Florida",7);
        seeding.put("Dayton",7);
        seeding.put("Washington St.",7);
        seeding.put("Nebraska",8);
        seeding.put("Mississippi St.",8);
        seeding.put("Florida Atlantic",8);
        seeding.put("Utah St.",8);
        seeding.put("Michigan St.",9);
        seeding.put("TCU", 9);
        seeding.put("Northwestern",9);
        seeding.put("Texas A&M",9);
        seeding.put("Colorado",10);
        seeding.put("Nevada",10);
        seeding.put("Drake",10);
        seeding.put("Colorado St.",10);
        seeding.put("New Mexico",11);
        seeding.put("Oregon",11);
        seeding.put("N.C. State",11);
        seeding.put("Duquesne",11);
        seeding.put("Grand Canyon",12);
        seeding.put("James Madison",12);
        seeding.put("McNeese St.",12);
        seeding.put("UAB",12);
        seeding.put("Samford",13);
        seeding.put("Yale",13);
        seeding.put("Charleston",13);
        seeding.put("Vermont",13);
        seeding.put("Morehead St.",14);
        seeding.put("Akron",14);
        seeding.put("Oakland",14);
        seeding.put("Colgate",14);
        seeding.put("Western Kentucky",15);
        seeding.put("South Dakota St.",15);
        seeding.put("Long Beach St.",15);
        seeding.put("Saint Peter's",15);
        seeding.put("Longwood",16);
        seeding.put("Stetson",16);
        seeding.put("Grambling St.",16);
        seeding.put("Wagner",16);

        while (true) {
            try {
                if (!((data = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(String name : seeding.keySet()) {

                if(data.contains(name) && lengthOfNameInLine(data) == name.length()) {
                    double adjEM = getAdjEM(data,name);
                    double adjT  = getAdjT(data,name);
                    teams.put(name, new Team(name,seeding.get(name),adjEM,adjT));
                    System.out.println(name + " AdjEM: " + adjEM + " AdjT: " + adjT);


                    break;
                }
            }

        }

        //what is this doing?
        //sets all the data for each team
        for(String name : seeding.keySet()) {
            //System.out.println(name);
            for(int i = 0; i < 32;i++) {
                if(bracket.bracketArray.get(i).team1.name.equals(name)) {
                    bracket.bracketArray.get(i).team1 = new Team(teams.get(name));
                } else if(bracket.bracketArray.get(i).team2.name.equals(name)) {
                    bracket.bracketArray.get(i).team2 = new Team(teams.get(name));

                }
            }
        }



        //now calculate games for each team
        int teamCount = 0;
        for(int i = 0; i < 32;i++) {
            teamCount++;
            bracket.bracketArray.get(i).calculateOdds();
            bracket.bracketArray.get(i).calculateWinner();
            //bracket.bracketArray.get(i).printGame();


            /*filling up next bracket*/

            int indexOfWinner = bracket.getNextGame(i + 1) ;

            indexOfWinner--;
            System.out.println("Winner: " + bracket.bracketArray.get(i).winner.name);
            System.out.println("Next Index: " + indexOfWinner);
            if(teamCount % 2 == 1) {
                bracket.bracketArray.get(indexOfWinner).setTeam1(bracket.bracketArray.get(i).winner);

            } else {
                bracket.bracketArray.get(indexOfWinner).setTeam2(bracket.bracketArray.get(i).winner);
                //bracket.bracketArray.get(indexOfWinner).printGame();
            }
            this.firstRoundExit[index] = bracket.bracketArray.get(i).loser.name;
            index++;
        }
        System.out.println();



        System.out.println("2nd Round:");

        for(int i = 32; i <= 47;i++) {

            bracket.bracketArray.get(i).printGame();
        }
        System.out.println();




        //2nd round calculations
        teamCount = 0;
        index = 0;
        for(int i = 32; i <= 47;i++) {
            teamCount++;
            bracket.bracketArray.get(i).calculateOdds();
            bracket.bracketArray.get(i).calculateWinner();
            //bracket.bracketArray.get(i).printGame();


            /*filling up next bracket*/

            int indexOfWinner = bracket.getNextGame(i + 1) ;

            indexOfWinner--;
            System.out.println("Winner: " + bracket.bracketArray.get(i).winner.name);
            System.out.println("Next Index: " + indexOfWinner);
            if(teamCount % 2 == 1) {
                bracket.bracketArray.get(indexOfWinner).setTeam1(bracket.bracketArray.get(i).winner);

            } else {
                bracket.bracketArray.get(indexOfWinner).setTeam2(bracket.bracketArray.get(i).winner);
                //bracket.bracketArray.get(indexOfWinner).printGame();
            }
            this.secondRoundExit[index] = bracket.bracketArray.get(i).loser.name;
            index++;
        }
        System.out.println();


        teamCount = 0;
        index = 0;
        for(int i = 48; i <= 55;i++) {
            teamCount++;
            bracket.bracketArray.get(i).calculateOdds();
            bracket.bracketArray.get(i).calculateWinner();
            //bracket.bracketArray.get(i).printGame();


            /*filling up next bracket*/

            int indexOfWinner = bracket.getNextGame(i + 1) ;

            indexOfWinner--;
            System.out.println("Winner: " + bracket.bracketArray.get(i).winner.name);
            System.out.println("Next Index: " + indexOfWinner);
            if(teamCount % 2 == 1) {
                bracket.bracketArray.get(indexOfWinner).setTeam1(bracket.bracketArray.get(i).winner);

            } else {
                bracket.bracketArray.get(indexOfWinner).setTeam2(bracket.bracketArray.get(i).winner);
                //bracket.bracketArray.get(indexOfWinner).printGame();
            }
            this.sweetSixteenExit[index] = bracket.bracketArray.get(i).loser.name;
            index++;
        }
        System.out.println();

        teamCount = 0;
        index = 0;
        for(int i = 56; i <= 59;i++) {
            teamCount++;
            bracket.bracketArray.get(i).calculateOdds();
            bracket.bracketArray.get(i).calculateWinner();
            //bracket.bracketArray.get(i).printGame();


            /*filling up next bracket*/

            int indexOfWinner = bracket.getNextGame(i + 1) ;

            indexOfWinner--;
            System.out.println("Winner: " + bracket.bracketArray.get(i).winner.name);
            System.out.println("Next Index: " + indexOfWinner);
            if(teamCount % 2 == 1) {
                bracket.bracketArray.get(indexOfWinner).setTeam1(bracket.bracketArray.get(i).winner);

            } else {
                bracket.bracketArray.get(indexOfWinner).setTeam2(bracket.bracketArray.get(i).winner);
                //bracket.bracketArray.get(indexOfWinner).printGame();
            }
            this.eliteEightExit[index] = bracket.bracketArray.get(i).loser.name;
            index++;
        }
        System.out.println();

        teamCount = 0;
        index = 0;
        for(int i = 60; i <= 61;i++) {
            teamCount++;
            bracket.bracketArray.get(i).calculateOdds();
            bracket.bracketArray.get(i).calculateWinner();



            /*filling up next bracket*/

            int indexOfWinner = bracket.getNextGame(i + 1) ;

            indexOfWinner--;
            System.out.println("Winner: " + bracket.bracketArray.get(i).winner.name);
            System.out.println("Next Index: " + indexOfWinner);
            if(teamCount % 2 == 1) {
                bracket.bracketArray.get(indexOfWinner).setTeam1(bracket.bracketArray.get(i).winner);

            } else {
                bracket.bracketArray.get(indexOfWinner).setTeam2(bracket.bracketArray.get(i).winner);

            }
            this.finalFourExit[index] = bracket.bracketArray.get(i).loser.name;
            index++;
        }
        System.out.println();

        teamCount = 0;

        for(int i = 62; i <= 62;i++) {
            teamCount++;
            bracket.bracketArray.get(i).calculateOdds();
            bracket.bracketArray.get(i).calculateWinner();



            /*filling up next bracket*/

            int indexOfWinner = bracket.getNextGame(i + 1) ;

            indexOfWinner--;
            System.out.println("Winner: " + bracket.bracketArray.get(i).winner.name + " (" + bracket.bracketArray.get(i).winner.seed + ")");
            this.winner[0] = bracket.bracketArray.get(i).winner.name;
            this.finalTwoExit[0] = bracket.bracketArray.get(i).loser.name;
        }
        System.out.println();
    }

    public static double erf(double x) {
        // Constants
        double a1 = 0.254829592;
        double a2 = -0.284496736;
        double a3 = 1.421413741;
        double a4 = -1.453152027;
        double a5 = 1.061405429;
        double p = 0.3275911;

        // Save the sign of x
        int sign = 1;
        if (x < 0)
            sign = -1;
        x = Math.abs(x);

        // A&S formula 7.1.26
        double t = 1.0 / (1.0 + p * x);
        double y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t * Math.exp(-x * x);

        return sign * y;
    }


    public static int lengthOfNameInLine(String line) {
        int left = 0;
        int right = 0;
        while(!isAlphabetic(line.charAt(left))) {
            left++;
        }
        right = left;
        while(!isDigit(line.charAt(right))) {
            right++;
        }
        right--;
        while(Character.isWhitespace(line.charAt(right)) ) {
            right--;
        }
        return right - left + 1;
    }

    public static double getAdjEM(String line, String name) {
        int index = 0;
        index += name.length();
        //goes up until record
        while(line.charAt(index) != '-') {
            index++;
        }
        index++;
        //goes up until AdjEM
        while(line.charAt(index) != '+' && line.charAt(index) != '-') {
            index++;
        }
        StringBuilder adjEMAsString = new StringBuilder();
        if(line.charAt(index) == '-') {
            adjEMAsString = new StringBuilder("-");
        } else {
            adjEMAsString = new StringBuilder("");
        }
        index++;
        while(!isWhitespace(line.charAt(index))) {

            adjEMAsString.append(line.charAt(index));

            index++;
        }

        return Double.parseDouble(adjEMAsString.toString());

    }

    public static double getAdjT(String line,String name) {
        int index = 0;
        index += name.length();
        //goes up until record
        while(line.charAt(index) != '-') {
            index++;
        }
        index++;
        //goes up until AdjEM
        while(line.charAt(index) != '+' && line.charAt(index) != '-') {
            index++;
        }
        //goes until end of AdjEM
        while(!isWhitespace(line.charAt(index))) {
            index++;
        }
        //goes until start of AdjO
        while(isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until end of AdjO
        while(!isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until rank of AdjO
        while(isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until end rank of AdjO
        while(!isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until AdjD
        while(isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until end of AdjD
        while(!isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until rank of AdjD
        while(isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until end of rank of AdjD
        while(!isWhitespace(line.charAt(index))) {
            index++;
        }

        //goes up until AdjT
        while(isWhitespace(line.charAt(index))) {
            index++;
        }
        StringBuilder adjTAsString = new StringBuilder();
        while(!isWhitespace(line.charAt(index))) {
            adjTAsString.append(line.charAt(index));
            index++;
        }


        return Double.parseDouble(String.valueOf(adjTAsString));
    }

    public String getWinner() {
        return this.winner[0];
    }

    //String FILE_PATH = new String("/Users/markseeliger/Desktop/Springboot/src/main/java/org/example/data.txt");
}
