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

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapDragStartHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MapZoomEndHandler;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;

public abstract class MapMenuController {
	protected boolean mouseOver;
	protected MapWidget mapWidget;
	protected LatLng currentLatLng;
	protected Point currentMousePosition;
	protected Point currentMouseDivPosition;
	protected MapEventHandler mapEventHandler;
	private boolean lastClickWasAnOverlay;
	private boolean lastClickShowedMapMenu;

	
	public MapMenuController(MapWidget mapWidget) {
		this.mapWidget = mapWidget;
		
		mouseOver = false;

		mapEventHandler = new MapEventHandler();
		mapWidget.addMapMouseMoveHandler(mapEventHandler);
		mapWidget.addMapDragStartHandler(mapEventHandler);
		mapWidget.addMapClickHandler(mapEventHandler);
		mapWidget.addMapZoomEndHandler(mapEventHandler);
	}
	

	protected void detach() {
		mapWidget.removeMapMouseMoveHandler(mapEventHandler);
		mapWidget.removeMapDragStartHandler(mapEventHandler);
		mapWidget.removeMapClickHandler(mapEventHandler);
		mapWidget.removeMapZoomEndHandler(mapEventHandler);
	}


	protected abstract void showMenu();

	protected abstract boolean isMenuVisible();
	
	protected abstract void hideMenu();
	
	protected void mapMouseMove(LatLng latlng) {
		currentLatLng = latlng;
		
		int mapAbsoluteLeft = mapWidget.getAbsoluteLeft();
		int mapAbsoluteTop = mapWidget.getAbsoluteTop();
		currentMouseDivPosition = mapWidget.convertLatLngToContainerPixel(currentLatLng);
		currentMousePosition =  Point.newInstance(currentMouseDivPosition.getX() + mapAbsoluteLeft,
				currentMouseDivPosition.getY() + mapAbsoluteTop);
	}
	
	protected void mapClick(MapClickEvent event) {
		//Can this code optimized??
		//Problem: Menu cannot be asked whether it is visible at this point of time,
		//since the click hides it before thid method is called.
		if (event.getOverlay() == null) {
			if ((!GwtMapsGxt.get().isMenuVisible()) && (!lastClickWasAnOverlay) && (!lastClickShowedMapMenu)) {
				showMenu();
				lastClickShowedMapMenu = true;
			} else {
				lastClickShowedMapMenu = false;
			}
			lastClickWasAnOverlay = false;
		} else {
			lastClickWasAnOverlay = true;
			lastClickShowedMapMenu = false;
		}
	}
	
	protected void mapZoomEnd() {
		hideMenu();
		lastClickShowedMapMenu = false;		
		lastClickWasAnOverlay = false;
	}
	
	protected void mapDragStart() {
		hideMenu();
		lastClickShowedMapMenu = false;		
		lastClickWasAnOverlay = false;
	}

	private class MapEventHandler implements MapMouseMoveHandler,
				MapDragStartHandler, MapClickHandler, MapZoomEndHandler {

		public void onMouseMove(MapMouseMoveEvent event) {
			mapMouseMove(event.getLatLng());
		}

		public void onDragStart(MapDragStartEvent event) {
			mapDragStart();
		}
		
		public void onClick(MapClickEvent event) {
			mapClick(event);
		}

		public void onZoomEnd(MapZoomEndEvent event) {
			mapZoomEnd();
		}
	}	

}
