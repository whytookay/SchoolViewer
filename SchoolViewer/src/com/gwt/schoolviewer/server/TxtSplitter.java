package com.gwt.schoolviewer.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtSplitter {
	
//	public static void main (String args[]) throws IOException
//	{
//		TxtSplitter parser = new TxtSplitter("http://www.bced.gov.bc.ca/reporting/odefiles/BoardLocations_Current.txt");
//		ArrayList<ArrayList<String>> temp = (parser.split());
//		for(int i = 0; i < temp.size(); i++)
//		{
//			//System.out.println(temp.get(i).get(2));
//			System.out.println(temp.get(i));
//		}
//	}

	public TxtSplitter(String target) throws MalformedURLException {
		url = new URL(target);
	}
	
	URL url;
	
	public ArrayList<ArrayList<String>> split() throws IOException
	{
		ArrayList<ArrayList<String>> processed = new ArrayList<ArrayList<String>>();
		Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
		
		try{
			while (scanner.hasNextLine()) {
				processed.add(procLine(scanner.nextLine()));
			}
		}finally{
			scanner.close();
		}
		processed = transpose(processed);
		return (processed);
	}

	private ArrayList<String> procLine(String pLine)
	{
		Scanner scanner = new Scanner(pLine);
		scanner.useDelimiter("\t");
		ArrayList<String> chLine = new ArrayList<String>();
		while ( scanner.hasNext() )
		{
			chLine.add(scanner.next());
		}
		return chLine;
	}
	
	private ArrayList<ArrayList<String>> transpose(ArrayList<ArrayList<String>> orig){
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < orig.get(0).size(); i++)
		{
			ArrayList<String> tempLine = new ArrayList<String>();
			for(int j = 0; j < orig.size(); j++)
			{
				tempLine.add(orig.get(j).get(i));
			}
			temp.add(tempLine);
		}
		return temp;
	}
}
