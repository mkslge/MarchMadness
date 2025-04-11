package org.example;
import javax.print.attribute.standard.MediaSize;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TeamCrud {
    public static final int YEAR_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int SEED_INDEX = 3;
    public static final int WIN_INDEX = 4;
    /*
    * This function adds a team to the MySQL Database
    *
    *
    * */
    public boolean add(Team team) {
        //check team isn't null
        if(team == null) {
            return false;
        }

        //syntax for SQL query
        String query = "INSERT INTO team (year, name, seed, wins) VALUES (?, ?, ?, ?)";


        try {
            //connect to database
            Connection connection = MySQLConnection.getConnection();

            //set statement variables
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(YEAR_INDEX, team.getYear());
            System.out.println("Team year:" + team.getYear());
            statement.setString(NAME_INDEX,team.getName());
            statement.setInt(SEED_INDEX, team.getSeed());


            //0 wins by default
            statement.setInt(WIN_INDEX, 0);
            //complete query
            int rowsAffected = statement.executeUpdate();
            //check if update worked
            return rowsAffected > 0;
        } catch(SQLException e) {
            //print stack trace in case of error
            e.printStackTrace();
        }


        return false;
    }
    /*
    * This function adds a team to the MySQL database, but specificies the number of wins to add
    * */
    public boolean add(Team team, int wins) {
        //check team isn't null
        if(team == null) {
            return false;
        }

        //syntax for SQL query
        String query = "INSERT INTO team (year, name, seed, wins) VALUES (?, ?, ?, ?)";


        try {
            //connect to database
            Connection connection = MySQLConnection.getConnection();

            //set statement variables
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(YEAR_INDEX, team.getYear());
            statement.setString(NAME_INDEX,team.getName());
            statement.setInt(SEED_INDEX, team.getSeed());


            //0 wins by default
            statement.setInt(WIN_INDEX, wins);
            //complete query
            int rowsAffected = statement.executeUpdate();
            //check if update worked
            return rowsAffected > 0;
        } catch(SQLException e) {
            //print stack trace in case of error
            e.printStackTrace();
        }

        //if we get this far nothing was added
        return false;
    }

    /*this function deletes a team from our MySQL Database*/
    public boolean delete(Team team) {
        //if its null we cant delete it
        if(team == null) {
            return false;
        }
        //team with bracket and year will be unique
        String query  = "DELETE FROM team WHERE name = ? AND year = ?";
        try {
            //connect to our database
            Connection connection = MySQLConnection.getConnection();

            //set variables
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, team.getName());
            statement.setInt(2, team.getYear());


            //check how many rows are affected by update, if none delete failed
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            //print in case of error
            e.printStackTrace();
        }

        //if we made it here an error occured
        return false;
    }

    /*
    * This function gets the amount of simulated tournament victories it has (given that)
    * the team is in the database.
    * FUNCTION RETURNS -1 on error
    * */
    public int getTeamWins(Team team) {
        //if team is null we can't see how kmany wins it has
        if(team == null) {
            return -1;
        }
        //set to -1 as a placeholder
        int wins = -1;

        String query = "SELECT wins FROM team WHERE name = ? AND year = ?";

        try {
            //connect to database
            Connection connection = MySQLConnection.getConnection();

            //set up name and year for query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, team.getName());
            statement.setInt(2, team.getYear());


            //execute query
            ResultSet set = statement.executeQuery();
            //if at least one exists grab the wins (there will only ever be 0 or 1)
            if(set.next()) {
                wins = set.getInt("wins");
            }

        } catch(SQLException e) {
            //print for debugging in case of error
            e.printStackTrace();
            wins = 0;
        }

        System.out.println("Wins: " + wins);
        return wins;
    }

    /*adds win to a team in MySQL database, returns false on failure and true on success*/
    public boolean addWin(Team team) {
        TeamCrud teamCrud = new TeamCrud();
        //get the current wins
        int currWins = teamCrud.getTeamWins(team);
        //if it doesnt exist number of wins are implicitly 0
        if(currWins == -1) {
            currWins = 0;
        }
        //add one
        int newWins = currWins + 1;
        //delete old team
        boolean deleteOutcome = teamCrud.delete(team);

        //add new team
        return teamCrud.add(team, newWins);
    }

    public boolean incrementWin(Team team) {
        String query = "UPDATE team SET wins = wins + 1 WHERE name = ? AND year = ?";

        try {
            Connection connection = MySQLConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,team.getName());
            statement.setInt(2, team.getYear());
            int rowsIncremented = statement.executeUpdate();
            return rowsIncremented > 0;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
