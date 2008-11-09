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
package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerRemoveHandler;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Timer;

/**
 * Attaches a Menu to a Marker.
 */
public class MarkerMenuController {
	
	private Marker marker;
	private MapWidget mapWidget;
	private Menu menu;
	private Point offset;

	private MarkerEventHandler markerEventHandler;

	public MarkerMenuController(MapWidget mapWidget, Marker marker,
			Menu menu, Point offset) {

		this.mapWidget = mapWidget;
		this.marker = marker;
		this.menu = menu;
		this.offset = offset;

		markerEventHandler = new MarkerEventHandler();

		attach();		
	}
	
	/**
	 * Returns the MapWidget
	 * 
	 * @return the MapWidget.
	 */
	public MapWidget getMapWidget() {
		return mapWidget;
	}

	/**
	 * Returns the Marker.
	 * 
	 * @return the Marker
	 */
	public Marker getMarker() {
		return marker;
	}

	/**
	 * @return returns the current {@link OverlayTip}.
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Sets the current OverlayTip.
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * Gets the offset from the marker where the Overlay will be displayed.
	 * 
	 * @return the offset.
	 */
	public Point getOffset() {
		return offset;
	}

	/**
	 * Sets the offset from the marker where the Overlay will be displayed.
	 */
	public void setOffset(Point offset) {
		this.offset = offset;
	}
	
	
	protected void attach() {
		marker.addMarkerClickHandler(markerEventHandler);
		marker.addMarkerRemoveHandler(markerEventHandler);
	}
	
	protected void detach() {
		marker.removeMarkerClickHandler(markerEventHandler);
		marker.removeMarkerRemoveHandler(markerEventHandler);
	}
	
	protected void showMenu() {
		Point point = Utility.LatLng2Point(mapWidget, marker.getLatLng());
		point = Point.newInstance(point.getX()+offset.getX(), point.getY()+offset.getY());
		MenuTimer.showMenu(menu, point);
	}
	
	
	private class MarkerEventHandler implements MarkerClickHandler, MarkerRemoveHandler {
		public void onRemove(MarkerRemoveEvent event) {
			detach();
		}

		public void onClick(MarkerClickEvent event) {
			showMenu();
		}
	}
	
	private static class MenuTimer extends Timer{
		private Menu menu;
		private Point point;
		
		MenuTimer(Menu menu, Point point) {
			this.menu = menu;
			this.point = point;
		}

		@Override
		public void run() {
			menu.showAt(point.getX(), point.getY());
		}
		
		public static void showMenu(Menu menu, Point point) {
			new MenuTimer(menu, point).schedule(50);
		}
	}
}
