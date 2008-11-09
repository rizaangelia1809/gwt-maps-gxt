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
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtMapsGxt_Sample implements EntryPoint {
	private MapWidget mapWidget;
	private Marker  marker;
	private OverlayTip overlayTip;
	private MarkerTipController markerTipController;
	private MarkerMenuController markerMenuController;

	public void onModuleLoad() {
		mapWidget = new MapWidget();
		mapWidget.setCenter(LatLng.newInstance(48.136559, 11.576318), 13);
		mapWidget.setWidth("100%");
		mapWidget.setHeight("100%");
		mapWidget.addControl(new LargeMapControl());
		mapWidget.setContinuousZoom(true);
		mapWidget.setScrollWheelZoomEnabled(true);
		RootPanel.get().add(mapWidget);
		
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
		
		Point p = Point.newInstance(20, -20);
		
		markerTipController = new MarkerTipController(mapWidget, marker, overlayTip, p);
		
		Menu popupMenu = new Menu();		
		MenuItem item1 = new MenuItem();
		item1.setText("Item 1");
		popupMenu.add(item1);		
		Point menuOffset = Point.newInstance(-20, -40);
		markerMenuController = new MarkerMenuController(mapWidget,
				marker, popupMenu, menuOffset);
	}
}
