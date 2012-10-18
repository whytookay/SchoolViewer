package com.gwt.schoolviewer.server;

import java.io.IOException;
import java.util.ArrayList;

public class BCDistricts {
	
	public static void main(String args[]) throws IOException{
		BCDistricts bcDistricts = new BCDistricts();
		bcDistricts.printDistricts();
	}

	ArrayList<District> districts = new ArrayList<District>();
	
	public BCDistricts() throws IOException {
		populateDistricts(pageToList());
	}
	
	private void populateDistricts(ArrayList<ArrayList<String>> temp) {
		System.out.println(temp.get(0).size());
		ArrayList<ArrayList<String>> disList = transpose(temp);
		System.out.println(disList.size());
		for(int i = 1; i < disList.size(); i++)
		{
			ArrayList<String> tempList = disList.get(i);
			District tempDistrict = new District(tempList.get(0), tempList.get(1), tempList.get(2), tempList.get(3));
			districts.add(tempDistrict);
		}
	}
	
	private ArrayList<ArrayList<String>> pageToList() throws IOException {
		TxtSplitter splitter = new TxtSplitter("http://www.bced.gov.bc.ca/reporting/odefiles/BoardLocations_Current.txt");
		ArrayList<ArrayList<String>> temp = splitter.split();
		for(int i = 0; i < temp.size();) {
			if (requiredLine(temp.get(i))){
				i++;
			}else{
				temp.remove(i);
			}
		}
		return temp;
	}
	
	private Boolean requiredLine(ArrayList<String> temp) {
		String title = temp.get(0);
		String[] accepted = new String[4];
		accepted[0] = "DISTRICT_NAME";
		accepted[1] = "DISTRICT_CITY";
		accepted[2] = "DISTRICT_PHONE_NUMBER";
		accepted[3] = "DISTRICT_WEBSITE";

		for(int i = 0; i < accepted.length; i++){
			if(title.equals(accepted[i]))
				return true;
		}
		return false;
	}
	
	public void printDistricts(){
		for(int i = 0; i < districts.size(); i++){
			District temp = districts.get(i);
			System.out.println(temp.getName() + " " + temp.getCity() + " " + temp.getPhone() + " " + temp.getWeb());
		}
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
