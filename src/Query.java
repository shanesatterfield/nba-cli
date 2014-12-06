package src;

import java.util.*;
import java.sql.*;

public class Query {
	public static ArrayList<ArrayList<String>> insertRow() {
		final String sqlCode  = "INSERT INTO Division (divisionName, divisionConfer)\n" +
		"\tVALUES('Rift', 'Eastern');\n\n";
		final String sqlCode2 = "INSERT INTO Team (teamName,teamCity,teamState,teamHomeColors,teamAwayColors,teamRanking,teamDivision,teamConference)\n" +
		"\tVALUES ('Tune Squad', 'Tune Land', 'TL', 'Blue', 'Orange', 30, 'Rift', 'Eastern');";
		query(sqlCode, false);
		query(sqlCode2, false);

		ArrayList<ArrayList<String>> result = query("SELECT teamName AS Team, teamCity AS City, teamState AS State, teamHomeColors AS 'Home Colors', teamAwayColors AS 'Away Colors', teamRanking AS Ranking, teamDivision AS Division, teamConference AS Conference FROM Team;", true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode + sqlCode2); }});

		return result;
	}

	public static ArrayList<ArrayList<String>> updateRow() {
		final String sqlCode = "UPDATE Team SET teamState = 'NY' WHERE teamName = 'Tune Squad';";
		query(sqlCode, false);

		ArrayList<ArrayList<String>> result = query("SELECT teamName AS Team, teamCity AS City, teamState AS State, teamHomeColors AS 'Home Colors', teamAwayColors AS 'Away Colors', teamRanking AS Ranking, teamDivision AS Division, teamConference AS Conference FROM Team;", true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});

		return result;
	}

	public static ArrayList<ArrayList<String>> deleteRow() {
		final String sqlCode = "DELETE FROM Division WHERE divisionName = 'Rift';";
		query(sqlCode, false);

		ArrayList<ArrayList<String>> result = query("SELECT teamName AS Team, teamCity AS City, teamState AS State, teamHomeColors AS 'Home Colors', teamAwayColors AS 'Away Colors', teamRanking AS Ranking, teamDivision AS Division, teamConference AS Conference FROM Team;", true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});

		return result;
	}

	public static ArrayList<ArrayList<String>> query1() {		
		final String sqlCode = "SELECT T.teamName AS 'Team Name', SUM(P.playerWeight) AS 'Total Weight', COUNT(S.staffID) AS 'Staff Members'\n" +
		"FROM Person PS\n" +
		"LEFT JOIN Player P\n" +
		"\tON PS.personID = P.playerID\n" +
		"LEFT JOIN Staff S\n" +
		"\tON PS.personID = S.staffID\n" +
		"INNER JOIN Employment E\n" +
		"\tON PS.personID = E.employmentID\n" +
		"INNER JOIN Team T\n" +
		"\tON E.employmentTeam = T.teamName\n" +
		"GROUP BY T.teamName\n" +
		"HAVING SUM(P.playerWeight) > 3300";
		ArrayList<ArrayList<String>> result = query(sqlCode, true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});
		return result;
	}

	public static ArrayList<ArrayList<String>> query2() {		
		final String sqlCode = "SELECT T.teamName AS 'Team Name', PS.personFirstName AS 'First Name', PS.personLastName AS 'Last Name', COALESCE(P.playerPosition, S.staffTitle) AS 'Position/Title'\n" +
		"FROM Person PS\n" +
		"LEFT JOIN Player P\n" +
		"\tON PS.personID = P.playerID\n" +
		"LEFT JOIN Staff S\n" +
		"\tON PS.personID = S.staffID\n" +
		"INNER JOIN Employment E\n" +
		"\tON PS.personID = E.employmentID\n" +
		"INNER JOIN Team T\n" +
		"\tON E.employmentTeam = T.teamName\n" +
		"WHERE T.teamName = 'Clippers'\n" +
		"ORDER BY T.TeamName";
		ArrayList<ArrayList<String>> result = query(sqlCode, true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});
		return result;
	}

	public static ArrayList<ArrayList<String>> query3() {		
		final String sqlCode = "SELECT T.teamName AS 'Team Name', PS.personFirstName AS 'Player First Name', PS.personLastName AS 'Player Last Name'\n" +
		"FROM Team T\n" +
		"INNER JOIN Employment E\n" +
		"\tON T.teamName = E.employmentTeam\n" +
		"INNER JOIN Person PS\n" +
		"\tON E.employmentID = PS.personID\n" +
		"INNER JOIN Player P\n" +
		"\tON PS.personID = P.playerID\n" +
		"WHERE P.playerPosition = 'Power Forward'";
		ArrayList<ArrayList<String>> result = query(sqlCode, true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});
		return result;
	}

	public static ArrayList<ArrayList<String>> query4() {		
		final String sqlCode = "SELECT  G.gameAwayTeam AS Team, COUNT(G.gameNumber) AS 'Game Number', MAX(C.CoachName) AS 'Head Coach'\n" +
		"FROM    Game G\n" +
		"INNER JOIN Team T\n" +
		"ON G.gameAwayTeam = T.teamName\n" +
		"LEFT JOIN (SELECT E.employmentTeam AS CoachTeam, CONCAT(P.personFirstName, ' ', P.personLastName) AS CoachName FROM Person P INNER JOIN Staff S ON P.personID = S.staffID INNER JOIN Employment E ON P.personID = E.employmentID WHERE S.staffTitle = 'Head Coach') C\n" +
		"ON T.teamName = C. CoachTeam\n" +
		"WHERE   G.GameDate <= '2014-12-31'\n" +
		"        AND G.GameDate >= '2014-12-01'\n" +
		"GROUP BY G.gameAwayTeam\n" +
		"HAVING COUNT(G.gameAwayTeam) = (SELECT MAX(X.GameCount)\n" +
		"            FROM (SELECT COUNT(D.gameNumber) AS GameCount\n" +
		"                    FROM Game D\n" +
		"                    WHERE D.GameDate <= '2014-12-31'\n" +
		"                    AND D.GameDate >= '2014-12-01'\n" +
		"                    GROUP BY D.gameAwayTeam) X\n" +
		"            )";
		ArrayList<ArrayList<String>> result = query(sqlCode, true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});
		return result;
	}

	public static ArrayList<ArrayList<String>> query5() {		
		final String sqlCode = "SELECT conferenceName AS Conference, MIN(total) AS 'Total Rankings'\n" +
		"FROM\n" +
		"(\n" +
		"	(SELECT conferenceName, SUM(teamRanking) AS total\n" +
		"	 FROM Team\n" +
		"	 INNER JOIN Division\n" +
		"	 ON Team.teamDivision = Division.divisionName\n" +
		"	 INNER JOIN Conference\n" +
		"	 ON Division.divisionConfer = Conference.conferenceName\n" +
		"	 WHERE conferenceName = 'Eastern'\n" +
		"     )\n" +
		"	 UNION(\n" +
		"	 	SELECT conferenceName, SUM(teamRanking) AS total\n" +
		"	 	FROM Team\n" +
		"	 	INNER JOIN Division\n" +
		"	 	ON Team.teamDivision = Division.divisionName\n" +
		"	 	INNER JOIN Conference\n" +
		"	 	ON Division.divisionConfer = Conference.conferenceName\n" +
		"	 	WHERE conferenceName = 'Western'\n" +
		"     )\n" +
		")AS sums";
		ArrayList<ArrayList<String>> result = query(sqlCode, true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});
		return result;
	}

	public static ArrayList<ArrayList<String>> query6() {		
		final String sqlCode = "SELECT teamName AS 'Team Name', venueName AS 'Venue', COUNT(Game.gameNumber) AS 'Number of Games'\n" +
		"FROM Team\n" +
		"LEFT OUTER JOIN Game\n" +
		"\tON Team.teamName = Game.gameHomeTeam OR Team.teamName = Game.gameAwayTeam\n" +
		"LEFT OUTER JOIN Venue\n" +
		"\tON Game.gameVenue = Venue.venueName\n" +
		"LEFT OUTER JOIN Network\n" +
		"\tON Game.gameNetwork = Network.networkName\n" +
		"WHERE Network.networkName IN ( SELECT networkName FROM Network WHERE networkName <> 'NBA TV') AND Venue.venueName='Staples Center'\n" +
		"GROUP BY Team.teamName\n" +
		"HAVING COUNT(Game.gameNumber) >=2;";
		
		ArrayList<ArrayList<String>> result = query(sqlCode, true);
		result.add(0, new ArrayList<String>(){{ add(sqlCode); }});
		return result;
	}


	public static ArrayList<ArrayList<String>> query( String sqlCode, boolean isQuery ) {
		Connection conn = null;
		Statement stmnt = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> result = null;
		try {

			conn = Runner.conn;
			stmnt = conn.createStatement();

			if( isQuery )
				rs = stmnt.executeQuery( sqlCode );
			else
				stmnt.executeUpdate( sqlCode );

			result = getQueryData( rs );

		} catch( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null )
					rs.close();

				if( stmnt != null )
					stmnt.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static ArrayList<ArrayList<String>> getQueryData( ResultSet rs ) throws SQLException
	{
		if( rs == null )
			return null;

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		// Do the headers.
		ArrayList<String> headers = new ArrayList<String>();
		ResultSetMetaData rsmd = rs.getMetaData();
		for( int i = 1; i <= rsmd.getColumnCount(); ++i )
			headers.add( rsmd.getColumnLabel(i) );
		result.add( headers );

		// Do the body of the query.		
		while( rs.next() ) {
			ArrayList<String> currRow = new ArrayList<String>();
			for( int i = 1; i <= headers.size(); ++i )
				currRow.add( rs.getString(i) );
			result.add( currRow );
		}

		return result;
	}
}