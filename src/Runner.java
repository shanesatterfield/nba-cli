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
				switch( getInput("\nQuery\t\t(q)\nCommit\t\t(c)\nRollback\t(r)\nInsert\t\t(i)\nUpdate\t\t(u)\nDelete\t\t(d)\nQuit\t\t(exit)\n\nEnter a command:") )
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
						Printer.printTableData( Query.deleteRow() );
						break;

					case "q":
						// TODO: Give descriptions of each query.
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
		Class.forName("com.mysql.jdbc.Driver");

		System.out.print("Connecting to database");
		conn = DriverManager.getConnection( URL, USER, PASS );
		/*
		// TODO: Use this instead.
		conn = DriverManager.getConnection(
			getInput("URL String"),
			getInput("User"),
			getInput("Password")
		);
		*/
		conn.setAutoCommit( false );

		System.out.println("\rConnected");
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



	public static Connection conn;
	private static final String URL  = "jdbc:mysql://infoserver.cecs.csulb.edu:3306/fall2014v";
	private static final String USER = "fall2014v";

	// TODO: Security
	private static final String PASS = "Eiquie";
	public static BufferedReader br = null;
}
