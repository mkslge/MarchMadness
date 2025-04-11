package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
public class Region {
    ArrayList<Team> teams;
    String regionName;
    ArrayList<Game> games;
    Team regionWinner;
    public Region(String regionName) throws IOException {

        this.regionName = regionName;
        teams = new ArrayList<>();
        games = new ArrayList<>();
        setTeams(regionName);
        for(int i = 0;  i < 15;i++) {
            games.add(new Game(2024));
        }
        int j = 15;
        for(int i = 0; i < 8;i++) {
            games.set(i, new Game(teams.get(i), teams.get(j - i)));
        }

        //setting 2nd round games
        games.set(8, new Game(games.get(0).getWinner(), games.get(7).getWinner()));
        games.set(9, new Game(games.get(1).getWinner(), games.get(6).getWinner()));
        games.set(10, new Game(games.get(2).getWinner(), games.get(5).getWinner()));
        games.set(11, new Game(games.get(3).getWinner(), games.get(4).getWinner()));

        games.set(12, new Game(games.get(8).getWinner(), games.get(9).getWinner()));
        games.set(13, new Game(games.get(10).getWinner(), games.get(11).getWinner()));


        games.set(14, new Game(games.get(12).getWinner(), games.get(13).getWinner()));

        regionWinner = games.get(14).getWinner();

    }

    public void setTeams(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(fileName);
        JsonNode rootNode = mapper.readTree(file);

        JsonNode regionArray = rootNode.get("Region");

        teams = mapper.readValue(
            regionArray.toString(),
            new TypeReference<ArrayList<Team>>() {}
        );
        int fileNameSize = fileName.length();
        for(Team team : teams) {
            int year = Integer.parseInt(fileName.substring(fileNameSize - 9,fileNameSize - 5));
            //System.out.println(Integer.parseInt(fileName.substring(fileNameSize - 9,fileNameSize - 5)));
            team.setYear(year);
            //System.out.println(team.getYear());
        }

    }

    public void printRegion() {
        for(Team team : teams) {
            System.out.println(team.getName());
        }
    }

    public Team getWinner() {
        return regionWinner;
    }

}
