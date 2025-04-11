package org.example;
import java.util.Random;
import static java.lang.Math.sqrt;
import static org.example.Simulation.erf;

public class Game {
    int standardDeviation = 11;
    Team team1;
    Team team2;
    Team winner;
    Team loser;

    double oddsOutOf100;

    Game (String name1, int seed1, double adjEM1, double adjT1,
          String name2, int seed2, double adjEM2, double adjT2) {
        team1 = new Team(name1, seed1, adjEM1, adjT1);
        team2 = new Team(name2, seed2, adjEM2, adjT2);
        this.calculateOdds();
        this.calculateWinner();
    }
    Game (Team team1, Team team2) {
        this.team1 = new Team(team1);
        this.team2 = new Team(team2);
        this.calculateOdds();
        this.calculateWinner();

    }

    Game (String name1, int seed1, String name2, int seed2) {
        team1 = new Team(name1, seed1);
        team2 = new Team(name2, seed2);
    }

    Game() {
        team1 = new Team();
        team2 = new Team();
        winner = null;
        loser = null;
    }
    public Game(int year) {
        team1 = new Team(year);
        team2 = new Team(year);
        winner = null;
        loser = null;
    }
    public double calculateOdds() {
        double pointDiff = (team1.adjEM - team2.adjEM) * (team1.adjT + team2.adjT) / 200;
        double CDF = 0.5 * (1 + erf((0 - pointDiff)/(standardDeviation * sqrt(2))));
        oddsOutOf100 = CDF * 100;
        return oddsOutOf100;
    }
    public String calculateWinner() {
        Random random = new Random();

        double randomDouble = (100) * random.nextDouble();
        if(oddsOutOf100 < randomDouble) {
            winner = new Team(team1);
            loser = new Team(team2);
        } else {
            winner = new Team(team2);
            loser = new Team(team1);
        }
        return winner.name;
    }
    public void addTeams(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }
    void printGame() {
        System.out.println("Game: " + team1.name + " v.s " + team2.name);
    }

    void setTeam1(Team other) {
        team1 = new Team(other);

    }

    void setTeam2(Team other) {
        team2 = new Team(other);
    }

    Team getWinner() {
        return this.winner;
    }

    Team getLoser() {
        return this.loser;
    }




}
