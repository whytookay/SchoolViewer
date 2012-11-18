package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.gwt.schoolviewer.server.BCDistricts;
import com.gwt.schoolviewer.server.District;
import com.gwt.schoolviewer.server.School;

public class BCDistrictsTest {
	
	ArrayList<District> districts = BCDistricts.getInstance().getDistricts();

	@Test
	public void testGetInstance() {
		assertFalse(BCDistricts.getInstance() == null);
		assertTrue(BCDistricts.getInstance().getDistricts().size() > 0);
	}

	@Test
	public void testGetDistricts() {
		assertFalse(districts == null);
		assertTrue(districts.size() > 0);
	}
	
	@Test
	public void testDistricts()
	{
		School test1 = testSchool("Isabella Dicken Elementary");
		assertTrue(test1.getDistrict().getName().equals("Southeast Kootenay"));
		
		School test2 = testSchool("Connaught Heights Elementary");
		assertFalse(test1.getDistrict().getName().equals(test2.getDistrict().getName()));
		
		School test3 = testSchool(test1.getName());
		assertTrue(test1.getDistrict().getName().equals(test3.getDistrict().getName()));
	}
	
	public School testSchool(String target)
	{
		School found = null;
		for(int i = 0; i < districts.size(); i++)
		{
			ArrayList<School> schools = districts.get(i).getSchools();
			for(int j = 0; j < schools.size(); j++)
			{
				if(schools.get(j).getName().equals(target))
				{
					found = schools.get(j);
					break;
				}
			}
			if(found != null)
			{
				break;
			}
		}
		
		assertTrue(found != null);
		
		return found;
	}

}
