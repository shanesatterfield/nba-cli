package src;

import java.util.*;

public class Printer
{
	public static void printTableData( ArrayList<ArrayList<String>> tableData )
	{
		int[] maxStringLengths = new int[ tableData.get(1).size() ];
		for( int n = 0; n < maxStringLengths.length; ++n )
			maxStringLengths[n] = getMax( getColumnLengths( tableData, n ) );

		String midLine = genMidLine( maxStringLengths );

		System.out.println("\nSQL Code");
		System.out.println(  "--------");
		System.out.println( tableData.get(0).get(0) + "\n\n" );

		System.out.println("Result Set");
		System.out.println( midLine );
		for( int i = 1; i < tableData.size(); ++i )
		{
			ArrayList<String> currRow = tableData.get(i);
			for( int j = 0; j < currRow.size(); ++j )
				System.out.printf("| %-" + maxStringLengths[j] + "s ", currRow.get(j));
			System.out.println('|');
			System.out.println( midLine );
		}
	}

	private static int[] getColumnLengths( ArrayList<ArrayList<String>> tableData, int columnIndex )
	{
		int[] col = new int[ tableData.size() ];
		for( int i = 1; i < col.length; ++i )
		{
			String curr = tableData.get(i).get(columnIndex);
			if( curr != null )
				col[i] = curr.length();
			else
				col[i] = 0;
		}
		return col;
	}

	private static int getMax( int[] arr )
	{
		int maximus = -1;
		for( int i = 0; i < arr.length; ++i )
			if( arr[i] > maximus)
				maximus = arr[i];
		return maximus;
	}

	private static int getSum( int[] arr )
	{
		int sum = 0;
		for( int i = 0; i < arr.length; ++i )
			sum += arr[i];
		return sum;
	}

	private static String genMidLine( int[] maxStringLengths )
	{
		int totalTableSize = getSum( maxStringLengths );
		char[] midLineTemp = new char[totalTableSize + 2*(maxStringLengths.length)];
		Arrays.fill( midLineTemp, '-');
		String midLine = new String( midLineTemp );

		int curr = 0;
		midLine = insertAt( midLine, curr, "+");
		for( int i: maxStringLengths )
		{
			curr += i + 3;
			midLine = insertAt( midLine, curr, "+");
		}

		return midLine;
	}

	private static String insertAt( String string, int index, String toInsert )
	{
		return new StringBuilder( string ).insert( index, toInsert ).toString();
	}
}