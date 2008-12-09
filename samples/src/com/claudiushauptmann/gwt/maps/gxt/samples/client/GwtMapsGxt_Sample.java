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

import com.claudiushauptmann.gwt.maps.gxt.client.MarkerMenuController;
import com.claudiushauptmann.gwt.maps.gxt.client.MarkerTipController;
import com.claudiushauptmann.gwt.maps.gxt.client.OverlayTip;
import com.claudiushauptmann.gwt.maps.gxt.client.PolylineGXTController;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.maps.client.overlay.PolylineOptions;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtMapsGxt_Sample implements EntryPoint {
	private MapWidget mapWidget;
	private Marker  marker;
	private OverlayTip overlayTip;
	private MarkerTipController markerTipController;
	private MarkerMenuController markerMenuController;

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
		
		
		//Marker
		MarkerOptions mo = MarkerOptions.newInstance();
		mo.setClickable(true);
		mo.setDraggable(true);
		marker = new Marker(mapWidget.getCenter(), mo);
		mapWidget.addOverlay(marker);
		
		overlayTip = new OverlayTip();
		overlayTip.setTitle("Marienplatz");
		overlayTip.setDescription("Marienplatz is a central square in the"
				+ " city center of Munich, Germany since 1158.<br/>"
				+ " In the Middle Ages markets and tournaments were held in this"
				+ " city square. The Glockenspiel in the new city hall was inspired"
				+ " by these tournaments, and draws millions of tourists a year.");
		
		markerTipController = new MarkerTipController(mapWidget,
				marker, overlayTip);
		
		Menu popupMenu = new Menu();		
		MenuItem item1 = new MenuItem();
		item1.setText("Item 1");
		popupMenu.add(item1);		
		markerMenuController = new MarkerMenuController(mapWidget,
				marker, popupMenu);
		
		//Polyline
		LatLng[] ll = new LatLng[2];
		ll[0] = mapWidget.getBounds().getNorthEast();
		ll[1] = mapWidget.getBounds().getSouthWest();
		PolylineOptions plo = PolylineOptions.newInstance(true, false);
		Polyline line = new Polyline(ll, "#FF0000", 2, 1.0, plo);
		line.setEditingEnabled(true);
		mapWidget.addOverlay(line);
		
		OverlayTip polylineOverlayTip = new OverlayTip();
		polylineOverlayTip.setTitle("Polyline");
		polylineOverlayTip.setDescription("This is the description");

		OverlayTip polylineVertexOverlayTip = new OverlayTip();
		polylineVertexOverlayTip.setTitle("Vertex");
		polylineVertexOverlayTip.setDescription("This is the description");

		OverlayTip polylineStartOverlayTip = new OverlayTip();
		polylineStartOverlayTip.setTitle("Start");
		polylineStartOverlayTip.setDescription("This is the description");

		OverlayTip polylineEndOverlayTip = new OverlayTip();
		polylineEndOverlayTip.setTitle("End");
		polylineEndOverlayTip.setDescription("This is the description");

		Menu lineMenu = new Menu();		
		MenuItem lineMenuItem = new MenuItem();
		lineMenuItem.setText("Polyline");
		lineMenu.add(lineMenuItem);		

		Menu lineMenuVertex = new Menu();		
		MenuItem lineMenuItemVertex = new MenuItem();
		lineMenuItemVertex.setText("Vertex");
		lineMenuVertex.add(lineMenuItemVertex);		

		Menu lineMenuStart = new Menu();		
		MenuItem lineMenuItemStart = new MenuItem();
		lineMenuItemStart.setText("Start");
		lineMenuStart.add(lineMenuItemStart);		

		Menu lineMenuEnd = new Menu();		
		MenuItem lineMenuItemEnd = new MenuItem();
		lineMenuItemEnd.setText("End");
		lineMenuEnd.add(lineMenuItemEnd);		
		
		PolylineGXTController ptc = new PolylineGXTController(mapWidget,
				line, polylineOverlayTip);

		ptc.setMenu(lineMenu);
		ptc.setVertexMenu(lineMenuVertex);
		ptc.setStartMenu(lineMenuStart);
		ptc.setEndMenu(lineMenuEnd);
		
		mapWidget.addMapClickHandler(new MapClickHandler() {
			public void onClick(MapClickEvent event) {
				System.out.println("click");
			}
		});
	}
}
