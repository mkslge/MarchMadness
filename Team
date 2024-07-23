package org.example;

public class Team {

    int seed;
    String name;
    double adjT;
    double adjEM;


    Team(String name, int seed) {
        this.name = name;
        this.seed = seed;
    }
    Team(String name, int seed, double adjEM, double adjT) {
        this.name = name;
        this.seed = seed;
        this.adjEM = adjEM;
        this.adjT = adjT;
    }

    public Team() {
        this.name = "";
    }

    public Team(Team other) {
        if (other == null) {
            throw new IllegalArgumentException("The provided team cannot be null");
        }
        this.name =  other.name;
        this.seed = other.seed;
        this.adjEM = other.adjEM;
        this.adjT = other.adjT;
    }

    void setTeam(Team other) {
        if(other == null) {
            //System.out.println("What?");
            return;
        }

        this.name = other.name;
        this.seed = other.seed;
        this.adjEM = other.adjEM;
        this.adjT = other.adjT;
    }

}
