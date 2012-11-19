package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gwt.schoolviewer.server.District;
import com.gwt.schoolviewer.server.School;
import com.gwt.schoolviewer.server.SchoolValueServiceImpl;

public class SchoolValueServiceImplTest {

	@Test
	public void testStringMatchesTo() {
		Boolean testOne = SchoolValueServiceImpl.stringMatchesTo("PARk-viLle dR. wAy asdf", "Park-Ville Secondary");
		assertTrue(testOne);
		Boolean testTwo = SchoolValueServiceImpl.stringMatchesTo("PARk-viLle waycademy", "porkval waycademy");
		assertTrue(testTwo);
		Boolean testThree = SchoolValueServiceImpl.stringMatchesTo("way ville", "way");
		assertFalse(testThree);
	}
	
	@Test
	public void testAreWithinRange() {
		Boolean testOne = SchoolValueServiceImpl.areWithinRange((Double) 51.05, new Double(-114.07), (Double) 49.25, new Double(-123.11), (Double) 1200.0);
		assertTrue(testOne);
		Boolean testTwo = SchoolValueServiceImpl.areWithinRange((Double) 51.05, new Double(-114.07), (Double) 49.25, new Double(-123.11), (Double) 300.0);
		assertFalse(testTwo);
		Boolean testThree = SchoolValueServiceImpl.areWithinRange((Double) 51.05, new Double(114.07), (Double) 49.25, new Double(-123.11), (Double) 10000.0);
		assertFalse(testThree);
	}
	
	// TODO: TEST MORE
}
