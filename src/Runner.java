package src;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class Runner
{
	public static void main( String args[] )
	{
		try
		{
			init();

			boolean quit = false;
			do {

				System.out.println("Query\t\t(q)");
				System.out.println("Commit\t\t(c)");
				System.out.println("Rollback\t(r)");
				System.out.println("Insert\t\t(i)");
				System.out.println("Update\t\t(u)");
				System.out.println("Delete\t\t(d)");
				System.out.println("Quit\t\t(exit)\n");

				switch( getInput("Enter a command:") )
				{
					case "c":
						conn.commit();
						break;

					case "r":
						conn.rollback();
						break;

					case "i":
						Printer.printTableData( Query.insertRow() );
						break;

					case "u":
						Printer.printTableData( Query.updateRow() );
						break;

					case "d":
						System.out.println("This query will delete a row from the Divisions table. Doing so may delete rows in other tables.");
						String ans = getInput("Do you want to continue? (y/n)");
						if( ( ans.equals("y") ) ? true : false )
							Printer.printTableData( Query.deleteRow() );
						break;

					case "q":
						System.out.println("Which query do you want to run?");
						System.out.println("(1)\tFind the teams that have a total weight over 3300 pounds and the staff members of those teams.");
						System.out.println("(2)\tFind all players and staff members in the Clippers team.");
						System.out.println("(3)\tFind all Power Forwards in the database along with their team name.");
						System.out.println("(4)\tFind the team playing the most away games in December and the head coach for this team.");
						System.out.println("(5)\tFind the conference with the highest total ranked teams.");
						System.out.println("(6)\tFind the teams playing 2 or more games at Staples Center that have no games broadcast on NBA TV.");
						runQuery( getInput("") );
						break;

					case "exit":
						quit = true;
				}

			} while( quit == false );

			if( getInput("Would you like to commit changes? (y/n)").equals("y") )
				conn.commit();
			else
				conn.rollback();

			br.close();
		}
		catch( SQLException e ) {
			e.printStackTrace();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally
		{
			try{
				if( conn != null )
					conn.close();
				conn = null;
				
			} catch( Exception e ){}
		}
	}

	public static void init() throws Exception
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("This is NBA Team's CECS 323 Final JDBC Project. Through this command line interface, you can connect to a database and run a number of predetermined queries.");
			System.out.println("Connecting to database");

			conn = DriverManager.getConnection(
				getInput("URL String:"),
				getInput("User:"),
				getInput("Password:")
			);
			conn.setAutoCommit( false );

			System.out.println("\rConnected\n");

			System.out.println("Select one of the options below to run the query.");
		} catch( Exception e ) {
			System.out.println("Error: Could not connect to database.");
			e.printStackTrace();
			throw e;
		}
	}

	public static String getInput( String message )
	{
		String result = "";
		try
		{
			if( br == null )
				br = new BufferedReader( new InputStreamReader(System.in) );

			System.out.println( message );
			System.out.print("> ");
			result = br.readLine();
		} catch( IOException e ){}
		
		return result;	
	}

	public static void runQuery( String query )
	{
		switch( query )
		{
			case "1":
				Printer.printTableData( Query.query1() );
				break;

			case "2":
				Printer.printTableData( Query.query2() );
				break;

			case "3":
				Printer.printTableData( Query.query3() );
				break;

			case "4":
				Printer.printTableData( Query.query4() );
				break;

			case "5":
				Printer.printTableData( Query.query5() );
				break;

			case "6":
				Printer.printTableData( Query.query6() );
				break;
		}
	}

	public static Connection conn   = null;
	public static BufferedReader br = null;
}
