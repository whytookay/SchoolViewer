package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gwt.schoolviewer.server.District;
import com.gwt.schoolviewer.server.School;

public class SchoolTest {
	
	School temp;
	District tempdist;

	@Before
	public void setUp() throws Exception {
		tempdist = new District("tempdistrict", "Vancouver", "1111111111", "www.google.ca");
		temp = new School("tempSchool", "location", "0000000000", "pub",
						"Vancouver", "elem", "1-7", "V6R2Z0",
							tempdist, 1.0, -1.0);
		tempdist.addSchool(temp);
	}

	@Test
	public void testGetLat() {
		double lat = temp.getLat();
		assertTrue(lat == 1.0);
	}

	@Test
	public void testGetLon() {
		double lon = temp.getLon();
		assertTrue(lon == -1.0);
	}

	@Test
	public void testGetName() {
		String name = temp.getName();
		assertFalse(name == null);
		assertTrue(name == "tempSchool");
	}

	@Test
	public void testGetLocation() {
		String loc = temp.getLocation();
		assertFalse(loc == null);
		assertTrue(loc == "location");
	}

	@Test
	public void testGetPCode() {
		String pcode = temp.getPCode();
		assertFalse(pcode == null);
		assertTrue(pcode == "V6R2Z0");
	}

	@Test
	public void testGetDistrict() {
		District d = temp.getDistrict();
		assertFalse(d == null);
		assertTrue(d == tempdist);
	}

	@Test
	public void testClassSize() {
		temp.setClassSize(37.0);
		double classSize = temp.getClassSize();
		assertTrue(classSize == 37.0);
	}

}
