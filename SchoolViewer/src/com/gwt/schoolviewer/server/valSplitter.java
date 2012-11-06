package com.gwt.schoolviewer.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

public class valSplitter extends TxtSplitter {
	
//	public static void main (String args[])
//	{
//		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
//		try {
//			valSplitter splitter = new valSplitter("http://www.bced.gov.bc.ca/reporting/odefiles/ClassSizeCurr.txt");
//			try {
//				temp = splitter.split();
//			} catch (IOException e) {
//				System.out.println("crashed");
//			}
//			for(int i = 1; i < temp.size(); i++)
//			{
//				System.out.println(temp.get(i).size());
//			}
//		} catch (MalformedURLException e) {
//			System.out.println("This didn't work");
//		}
//	}

	public valSplitter(String target) throws MalformedURLException {
		super(target);
	}
	
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
		processed = snip(processed);
		processed = transpose(processed);
		return (processed);
	}
	
	private ArrayList<ArrayList<String>> snip (ArrayList<ArrayList<String>> target)
	{
		int length = target.get(0).size();
		for(int i = 1; i < target.size(); i++)
		{
			if(target.get(i).size() < length)
			{
				target.get(i).add("");
			}
		}
		return target;
	}

}
