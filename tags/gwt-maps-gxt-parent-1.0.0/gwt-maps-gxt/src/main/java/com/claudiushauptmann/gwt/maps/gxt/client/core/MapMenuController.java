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

/**
 * MapMenuController provides base functionality for controllers of maps. It
 * registers for map events and provides abstract logic around when to show,
 * update und hide the menu. Derived controllers only have to worry about how to
 * show, update and hide the menu but not about when to do that.
 */
public abstract class MapMenuController {

	/**
	 * The map to be observed.
	 */
	protected MapWidget mapWidget;

	/**
	 * The observer for the map.
	 */
	protected MapEventHandler mapEventHandler;

	/**
	 * The last clicked position of the mouse in latlng.
	 */
	private LatLng currentLatLng;

	/**
	 * The last clicked position of the mouse in Point.
	 */
	private Point currentMousePosition;

	/**
	 * The last clicked position of the mouse relative to it's containing dom
	 * element.
	 */
	private Point currentMouseDivPosition;

	/**
	 * The last right clicked mouse position in latlng.
	 */
	private LatLng rightClickedLatLng;

	/**
	 * The last right clicked position of the mouse in Point.
	 */
	private Point rightClickedMousePosition;

	/**
	 * The last right clicked position of the mouse relative to it's containing
	 * dom element.
	 */
	private Point rightClickedDivPosition;

	/**
	 * Creates a MapMenuController.
	 * 
	 * @param mapWidget
	 *            The map to be observed.
	 */
	public MapMenuController(MapWidget mapWidget) {
		this.mapWidget = mapWidget;

		mapEventHandler = new MapEventHandler();
		mapWidget.addMapMouseMoveHandler(mapEventHandler);
		mapWidget.addMapRightClickHandler(mapEventHandler);
	}

	/**
	 * Returns the map to be observed.
	 * 
	 * @return The map to be observed.
	 */
	public MapWidget getMapWidget() {
		return mapWidget;
	}

	/**
	 * Returns the last clicked position of the mouse in latlng.
	 */
	public LatLng getCurrentLatLng() {
		return currentLatLng;
	}

	/**
	 * Returns the last clicked position of the mouse in Point.
	 */
	public Point getCurrentMousePosition() {
		return currentMousePosition;
	}

	/**
	 * Returns the last clicked position of the mouse relative to it's
	 * containing dom element.
	 */
	public Point getCurrentMouseDivPosition() {
		return currentMouseDivPosition;
	}

	/**
	 * Returns the last right clicked mouse position in latlng.
	 */
	public LatLng getRightClickedLatLng() {
		return rightClickedLatLng;
	}

	/**
	 * Returns the last right clicked position of the mouse in Point.
	 */
	public Point getRightClickedMousePosition() {
		return rightClickedMousePosition;
	}

	/**
	 * Returns the last right clicked position of the mouse relative to it's
	 * containing dom element.
	 */
	public Point getRightClickedDivPosition() {
		return rightClickedDivPosition;
	}

	/**
	 * Is called when the menu has to be shown. Override this method.
	 */
	protected abstract void showMenu();

	/**
	 * Returns the menu visibility.
	 * 
	 * @return The menu visibility.
	 */
	public abstract boolean isMenuVisible();

	/**
	 * Is called when the menu has to be hidden. Override this menu.
	 */
	public abstract void hideMenu();

	/**
	 * Detaches the observers.
	 */
	protected void detach() {
		mapWidget.removeMapMouseMoveHandler(mapEventHandler);
		mapWidget.removeMapRightClickHandler(mapEventHandler);
	}

	/**
	 * Events Handler for mouse move events.
	 * 
	 * @param latlng
	 *            The current position of the mouse in latlng.
	 */
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

	/**
	 * Event handler for right click events.
	 */
	protected void mapRightClick() {
		rightClickedLatLng = currentLatLng;
		rightClickedMousePosition = currentMousePosition;
		rightClickedDivPosition = currentMouseDivPosition;

		hideMenu();
		showMenu();
	}

	/**
	 * Observer for map events.
	 */
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
