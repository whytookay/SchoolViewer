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
		temp = new District("tempdistrict", "Vancouver", "1111111111", "www.google.ca");
		tempschool = new School("tempSchool", "location", "0000000000", "pub",
					 "Vancouver", "elem", "1-7", "V6R2Z0",
					 temp, 1.0, -1.0);
	}

	@Test
	public void testGetName() {
		String name = temp.getName();
		assertTrue(name == "tempdistrict");
	}

	@Test
	public void testGetCity() {
		String city = temp.getCity();
		assertTrue(city == "Vancouver");
	}

	@Test
	public void testGetPhone() {
		String phone = temp.getPhone();
		assertTrue(phone == "1111111111");
	}

	@Test
	public void testGetWeb() {
		String web = temp.getWeb();
		assertTrue(web == "www.google.ca");
	}

	@Test
	public void testGetSchools() {
		ArrayList<School> schools = temp.getSchools();
		assertTrue(schools.size() == 0);
		temp.addSchool(tempschool);
		schools = temp.getSchools();
		assertTrue(schools.size() == 1);
		assertTrue(schools.get(0) == tempschool);
		
	}

}
