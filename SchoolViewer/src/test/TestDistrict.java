package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.gwt.schoolviewer.server.District;
import com.gwt.schoolviewer.server.School;

public class TestDistrict {
	
	District temp;
	School tempschool;

	@Before
	public void setUp() throws Exception {
		temp = new District("tempdistrict", "www.google.ca");
		tempschool = new School("tempSchool", "location", "0000000000", "pub",
					 "Vancouver", "elem", "1-7", "V6R2Z0",
					 temp, 1.0, -1.0);
	}

	@Test
	public void testGetName() {
		String name = temp.getName();
		assertFalse(name == null);
		assertTrue(name == "tempdistrict");
	}

	@Test
	public void testGetWeb() {
		String web = temp.getWeb();
		assertFalse(web == null);
		assertTrue(web == "www.google.ca");
	}

	@Test
	public void testGetSchools() {
		ArrayList<School> schools = temp.getSchools();
		assertTrue(schools.size() == 0);
		temp.addSchool(tempschool);
		schools = temp.getSchools();
		assertTrue(schools.size() == 1);
		School testschool = schools.get(0);
		assertFalse(testschool == null);
		assertTrue(testschool == tempschool);
		
	}

}
