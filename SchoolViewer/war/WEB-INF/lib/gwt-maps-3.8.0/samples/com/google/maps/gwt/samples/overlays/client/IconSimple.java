/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.maps.gwt.samples.overlays.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;

/**
 * Java/GWT-binding version of sample at
 * http://code.google.com/apis/maps/documentation/javascript/examples/icon-simple.html
 *
 * @author dcarlson@google.com (David Carlson)
 */
public class IconSimple implements EntryPoint {

  private static final String BEACH_FLAG = "images/beachflag.png";

  @Override
  public void onModuleLoad() {
    MapOptions myOptions = MapOptions.create();
    myOptions.setZoom(4.0);
    myOptions.setCenter(LatLng.create(-25.363882, 131.044922));
    myOptions.setMapTypeId(MapTypeId.ROADMAP);
    GoogleMap map = GoogleMap.create(Document.get().getElementById("map_canvas"), myOptions);

    MarkerOptions newMarkerOpts = MarkerOptions.create();
    newMarkerOpts.setPosition(LatLng.create(-33.890542, 151.274856));
    newMarkerOpts.setMap(map);
    newMarkerOpts.setIcon(BEACH_FLAG);
    Marker.create(newMarkerOpts);
  }
}
