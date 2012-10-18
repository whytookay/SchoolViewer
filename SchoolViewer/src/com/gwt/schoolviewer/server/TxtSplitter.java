package com.gwt.schoolviewer.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtSplitter {

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
		
		return processed;
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
}
