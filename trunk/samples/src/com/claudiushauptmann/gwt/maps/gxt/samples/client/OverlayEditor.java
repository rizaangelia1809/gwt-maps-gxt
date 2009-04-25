/*
 * Copyright 2008 Claudius Hauptmann
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
package com.claudiushauptmann.gwt.maps.gxt.samples.client;

import com.claudiushauptmann.gwt.maps.gxt.client.MapGXTController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.maps.client.overlay.PolygonOptions;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.maps.client.overlay.PolylineOptions;
import com.google.gwt.user.client.ui.RootPanel;

public class OverlayEditor implements EntryPoint {
	private MapWidget mapWidget;
	
	@SuppressWarnings("unused")
	private MyMarkerEditController myMarkerEditController;
	
	@SuppressWarnings("unused")
	private MyPolylineEditController myPolylineEditController;
	
	@SuppressWarnings("unused")
	private MyPolygonEditController myPolygonEditController;

	public void onModuleLoad() {
		// Map
		mapWidget = new MapWidget();
		mapWidget.setCenter(LatLng.newInstance(48.136559, 11.576318), 13);
		mapWidget.setWidth("100%");
		mapWidget.setHeight("100%");
		mapWidget.addControl(new LargeMapControl());
		mapWidget.setContinuousZoom(true);
		mapWidget.setScrollWheelZoomEnabled(true);
		RootPanel.get().add(mapWidget);

		// Necessary for the "OverlayGXTControllers"
		MapGXTController mapGxtController = new MapGXTController(mapWidget);

		// Marker
		MarkerOptions mo = MarkerOptions.newInstance();
		mo.setClickable(true);
		mo.setDraggable(true);
		Marker marker = new Marker(mapWidget.getCenter(), mo);
		mapWidget.addOverlay(marker);
		myMarkerEditController = new MyMarkerEditController(mapGxtController,
				marker);

		// Polyline
		LatLng[] llline = new LatLng[3];
		llline[0] = LatLng.newInstance(48.131955, 11.527061);
		llline[1] = LatLng.newInstance(48.11809, 11.579247);
		llline[2] = LatLng.newInstance(48.127143, 11.638298);
		PolylineOptions plo = PolylineOptions.newInstance(true, false);
		Polyline line = new Polyline(llline, "#FF0000", 2, 1.0, plo);
		mapWidget.addOverlay(line);
		myPolylineEditController = new MyPolylineEditController(
				mapGxtController, line);

		// Polygon
		LatLng[] llpolygon = new LatLng[4];
		llpolygon[0] = LatLng.newInstance(48.119809, 11.539936);
		llpolygon[1] = LatLng.newInstance(48.158185, 11.541138);
		llpolygon[2] = LatLng.newInstance(48.155894, 11.569118);
		llpolygon[3] = llpolygon[0];
		PolygonOptions pgo = PolygonOptions.newInstance(true);
		Polygon polygon = new Polygon(llpolygon, "#0000FF", 2, 1.0, "#0000FF",
				0.3, pgo);
		mapWidget.addOverlay(polygon);
		myPolygonEditController = new MyPolygonEditController(mapGxtController,
				polygon);
	}
}
