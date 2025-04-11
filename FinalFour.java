package org.example;

public class FinalFour {
    Team west;
    Team east;
    Team south;
    Team midwest;

    Game southVSWest;
    Game eastVSMidwest;
    Game championship;

    Team champion;

    public FinalFour(Region west, Region east, Region south, Region midwest) {
        this.west = new Team(west.getWinner());
        this.east = new Team(east.getWinner());
        this.south = new Team(south.getWinner());
        this.midwest = new Team(midwest.getWinner());

        southVSWest = new Game(this.south, this.west);
        eastVSMidwest = new Game(this.east, this.midwest);
        championship = new Game(southVSWest.getWinner(), eastVSMidwest.getWinner());
        champion = championship.getWinner();
    }

    public Game getChampionship() {
        return championship;
    }

    public Game getEastVSMidwest() {
        return eastVSMidwest;
    }

    public Game getSouthVSWest() {
        return southVSWest;
    }

    public Team getChampion() {
        return champion;
    }

    public Team getEast() {
        return east;
    }

    public Team getMidwest() {
        return midwest;
    }

    public Team getSouth() {
        return south;
    }

    public Team getWest() {
        return west;
    }


    public void setChampion(Team champion) {
        this.champion = champion;
    }
}
