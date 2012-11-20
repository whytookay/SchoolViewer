package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;

public class GoogleMapImpl {
	private ArrayList<SchoolValue> ListOfSchools;
	private MapOptions options = MapOptions.create();
	private ArrayList<Marker> Markers = new ArrayList<Marker>();
	private InfoWindow IW = InfoWindow.create();
	private GoogleMap theMap;
	
	public GoogleMapImpl(){
		// do nothing
	}
	
	public GoogleMap CreateMap(SimplePanel mapPanel){
		
		options.setCenter(LatLng.create(54.826008, -124.848633));
		options.setZoom(5);
		options.setMapTypeId(MapTypeId.ROADMAP);
		options.setDraggable(true);
		options.setMapTypeControl(true);
		options.setScaleControl(true);
		options.setScrollwheel(true);
		theMap = GoogleMap.create(mapPanel.getElement(), options);
		
		return theMap;
		
	}
	public void AddMarker(String name) {
		for (SchoolValue s : ListOfSchools) {
			if (name.equals(s.getName())) {
				MarkerOptions markerOptions = MarkerOptions.create();
				markerOptions.setMap(theMap);
				markerOptions.setTitle(s.getName());
				markerOptions.setDraggable(false);
				markerOptions.setPosition(LatLng.create(s.getLatitude(),
						s.getLongitude()));
				Marker marker = Marker.create(markerOptions);
				String pre = "<div id=\"content\">"
						+ "<p style=\"text-align:left; color:BLACK; font-size: 16pt \"><b>"
						+ s.getName()
						+ "</b></p>"
						+ "<p style=\"text-align:left;color: BLACK;\">"
						+ s.getPubOrInd() + ". Education Level: " + s.getEduLevel()
						+ "</b></p>"
						+ "<p style=\"text-align:left;color: BLACK;\">"
						+ "District: "+s.getDistrict()
						+ "</b></p>"
						+ "<p style=\"text-align:left;color: BLACK;\">"
						+ "Address: " + s.getLocation()
						+ "</b></p>"
						+ "<p style=\"text-align:left;color: BLACK;\">"
						+ "Phone Number: " + s.getPhone()
						+ "</b></p>";
                String Sub;
                if (s.getClassSize().intValue() < 0){
                	Sub = "<p style=\"text-align:left;color: BLACK;\">"
                			+ "Class size: N/A" 
                			+ "</b></p>"  
                			+ "</div>";
                }else{
                	Sub = "<p style=\"text-align:left;color: BLACK;\">"
                			+ "Class size: " + s.getClassSize().intValue() 
                			+ "</b></p>"  
                			+ "</div>";
                }

				final String txt = pre+Sub;

				final LatLng pos = LatLng.create(s.getLatitude(),
						s.getLongitude());
				marker.addClickListener(new Marker.ClickHandler() {

					@Override
					public void handle(MouseEvent event) {
						SetIW(txt,pos,theMap);

					}
				});
				Markers.add(marker);

			}

		}

	}

	public void RemoveMarker(String name) {
		for (int k = 0; k < Markers.size(); k++) {
			if (name.equals(Markers.get(k).getTitle())) {
				Markers.get(k).setMap((GoogleMap) null);
				if (Markers.get(k).getPosition().equals(IW.getPosition()))
					IW.close();
				Markers.remove(k);

			}
		}
	}
	
	public void SetIW(String txt, LatLng pos, GoogleMap map){
		IW.close();
		IW.setPosition(pos);
		IW.setContent(txt);
		IW.open(map);
	}
	
	public void setSchools(ArrayList<SchoolValue> ListOfSchools){
		this.ListOfSchools =ListOfSchools;
	}
	
	
}
