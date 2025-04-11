
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
        /*if(!deleteOutcome) {
            return false;
        }*/
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
