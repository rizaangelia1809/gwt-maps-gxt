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

import com.claudiushauptmann.gwt.maps.gxt.client.MarkerGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.OverlayTip;
import com.claudiushauptmann.gwt.maps.gxt.client.PolygonGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.PolylineGXTController;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
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

public class GwtMapsGxt_Sample implements EntryPoint {
	private MapWidget mapWidget;

	public void onModuleLoad() {
		// Map
		mapWidget = new MapWidget();
		mapWidget.setCenter(LatLng.newInstance(48.136559, 11.576318), 13);
		mapWidget.setWidth("80%");
		mapWidget.setHeight("80%");
		mapWidget.addControl(new LargeMapControl());
		mapWidget.setContinuousZoom(true);
		mapWidget.setScrollWheelZoomEnabled(true);
		RootPanel.get("map").add(mapWidget);
		
		
		//Marker
		MarkerOptions mo = MarkerOptions.newInstance();
		mo.setClickable(true);
		mo.setDraggable(true);
		Marker marker = new Marker(mapWidget.getCenter(), mo);
		mapWidget.addOverlay(marker);
		
		OverlayTip overlayTip = new OverlayTip();
		overlayTip.setTitle("Marienplatz");
		overlayTip.setDescription("Marienplatz is a central square in the"
				+ " city center of Munich, Germany since 1158.<br/>"
				+ " In the Middle Ages markets and tournaments were held in this"
				+ " city square. The Glockenspiel in the new city hall was inspired"
				+ " by these tournaments, and draws millions of tourists a year.");
		
		Menu popupMenu = new Menu();		
		MenuItem item1 = new MenuItem();
		item1.setText("Marker");
		popupMenu.add(item1);		
		
		MarkerGXTController markerGXTController = new MarkerGXTController(mapWidget, marker);
		markerGXTController.setOverlayTip(overlayTip);
		markerGXTController.setMenu(popupMenu);
		
		
		//Polyline
		LatLng[] llline = new LatLng[3];		
		llline[0] = LatLng.newInstance(48.131955, 11.527061);
		llline[1] = LatLng.newInstance(48.11809, 11.579247);
		llline[2] = LatLng.newInstance(48.127143, 11.638298);
		PolylineOptions plo = PolylineOptions.newInstance(true, false);
		Polyline line = new Polyline(llline, "#FF0000", 2, 1.0, plo);
		line.setEditingEnabled(true);
		mapWidget.addOverlay(line);
		
		OverlayTip polylineOverlayTip = new OverlayTip();
		polylineOverlayTip.setTitle("Polyline");
		polylineOverlayTip.setDescription("This is the description");

		Menu lineStandardMenu = new Menu();		
		MenuItem lineMenuItem = new MenuItem();
		lineMenuItem.setText("Polyline");
		lineStandardMenu.add(lineMenuItem);		

		Menu lineMenuVertex = new Menu();		
		MenuItem lineMenuItemVertex = new MenuItem();
		lineMenuItemVertex.setText("PolylineVertex");
		lineMenuVertex.add(lineMenuItemVertex);		

		Menu lineMenuStart = new Menu();		
		MenuItem lineMenuItemStart = new MenuItem();
		lineMenuItemStart.setText("PolylineStart");
		lineMenuStart.add(lineMenuItemStart);		

		Menu lineMenuEnd = new Menu();		
		MenuItem lineMenuItemEnd = new MenuItem();
		lineMenuItemEnd.setText("PolylineEnd");
		lineMenuEnd.add(lineMenuItemEnd);		
		
		PolylineGXTController ptc = new PolylineGXTController(mapWidget, line);
		ptc.setOverlayTip(polylineOverlayTip);
		ptc.setStandardMenu(lineStandardMenu);
		ptc.setVertexMenu(lineMenuVertex);
		ptc.setStartMenu(lineMenuStart);
		ptc.setEndMenu(lineMenuEnd);

		
		//Polygon
		LatLng[] llpolygon = new LatLng[4];
		llpolygon[0] = LatLng.newInstance(48.119809, 11.539936);
		llpolygon[1] = LatLng.newInstance(48.158185, 11.541138);
		llpolygon[2] = LatLng.newInstance(48.155894, 11.569118);
		llpolygon[3] = llpolygon[0];
		PolygonOptions pgo = PolygonOptions.newInstance(true);
		Polygon polygon = new Polygon(llpolygon, "#0000FF", 2, 1.0, "#0000FF", 0.3, pgo);
		polygon.setEditingEnabled(true);
		mapWidget.addOverlay(polygon);
		
		OverlayTip polygonOverlayTip = new OverlayTip();
		polygonOverlayTip.setTitle("Polygon");
		polygonOverlayTip.setDescription("This is the description");

		Menu polygonStandardMenu = new Menu();		
		MenuItem polygonMenuItem = new MenuItem();
		polygonMenuItem.setText("Polygon");
		polygonStandardMenu.add(polygonMenuItem);		

		Menu polygonMenuVertex = new Menu();		
		MenuItem polylineMenuItemVertex = new MenuItem();
		polylineMenuItemVertex.setText("PolygonVertex");
		polygonMenuVertex.add(polylineMenuItemVertex);		
		
		PolygonGXTController pgc = new PolygonGXTController(mapWidget, polygon);
		pgc.setOverlayTip(polygonOverlayTip);
		pgc.setStandardMenu(polygonStandardMenu);
		pgc.setVertexMenu(polygonMenuVertex);
	}
}
