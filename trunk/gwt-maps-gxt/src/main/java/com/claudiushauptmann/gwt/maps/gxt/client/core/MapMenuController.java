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
package com.claudiushauptmann.gwt.maps.gxt.client.core;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MapRightClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;

public abstract class MapMenuController {
	protected MapWidget mapWidget;
	protected MapEventHandler mapEventHandler;
	
	private LatLng currentLatLng;
	private Point currentMousePosition;
	private Point currentMouseDivPosition;
	private LatLng rightClickedLatLng;
	private Point rightClickedMousePosition;
	private Point rightClickedDivPosition;

	public MapMenuController(MapWidget mapWidget) {
		this.mapWidget = mapWidget;

		mapEventHandler = new MapEventHandler();
		mapWidget.addMapMouseMoveHandler(mapEventHandler);
		mapWidget.addMapRightClickHandler(mapEventHandler);
	}

	public MapWidget getMapWidget() {
		return mapWidget;
	}

	public LatLng getCurrentLatLng() {
		return currentLatLng;
	}

	public Point getCurrentMousePosition() {
		return currentMousePosition;
	}

	public Point getCurrentMouseDivPosition() {
		return currentMouseDivPosition;
	}

	
	public LatLng getRightClickedLatLng() {
		return rightClickedLatLng;
	}

	public Point getRightClickedMousePosition() {
		return rightClickedMousePosition;
	}

	public Point getRightClickedDivPosition() {
		return rightClickedDivPosition;
	}

	protected abstract void showMenu();

	public abstract boolean isMenuVisible();

	public abstract void hideMenu();

	protected void detach() {
		mapWidget.removeMapMouseMoveHandler(mapEventHandler);
		mapWidget.removeMapRightClickHandler(mapEventHandler);
	}

	protected void mapMouseMove(LatLng latlng) {
		currentLatLng = latlng;

		int mapAbsoluteLeft = mapWidget.getAbsoluteLeft();
		int mapAbsoluteTop = mapWidget.getAbsoluteTop();
		currentMouseDivPosition = mapWidget
				.convertLatLngToContainerPixel(currentLatLng);
		currentMousePosition = Point.newInstance(currentMouseDivPosition.getX()
				+ mapAbsoluteLeft, currentMouseDivPosition.getY()
				+ mapAbsoluteTop);
	}

	protected void mapRightClick() {
		rightClickedLatLng = currentLatLng;
		rightClickedMousePosition = currentMousePosition;
		rightClickedDivPosition = currentMouseDivPosition;
		
		hideMenu();
		showMenu();
	}

	private class MapEventHandler implements MapMouseMoveHandler,
			MapRightClickHandler {

		public void onMouseMove(MapMouseMoveEvent event) {
			mapMouseMove(event.getLatLng());
		}

		public void onRightClick(MapRightClickEvent event) {
			if (event.getOverlay() == null) {
				mapRightClick();
			}
		}
	}

}
