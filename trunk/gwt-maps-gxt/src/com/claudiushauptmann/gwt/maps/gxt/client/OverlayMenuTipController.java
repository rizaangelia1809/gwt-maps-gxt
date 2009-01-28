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
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapDragStartHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MapMouseOutHandler;
import com.google.gwt.maps.client.event.MapZoomEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;

public abstract class OverlayMenuTipController {

	protected boolean mouseOver;
	protected MapWidget mapWidget;
	protected LatLng currentLatLng;
	protected Point currentMousePosition;
	protected Point currentMouseDivPosition;
	protected MapEventHandler mapEventHandler;

	public OverlayMenuTipController(MapWidget mapWidget) {
		this.mapWidget = mapWidget;
		
		mouseOver = false;

		mapEventHandler = new MapEventHandler();
		mapWidget.addMapMouseMoveHandler(mapEventHandler);
		mapWidget.addMapDragStartHandler(mapEventHandler);
		mapWidget.addMapDragEndHandler(mapEventHandler);
		mapWidget.addMapMouseOutHandler(mapEventHandler);
		mapWidget.addMapClickHandler(mapEventHandler);
		mapWidget.addMapZoomEndHandler(mapEventHandler);
	}

	
	protected abstract void showOverlayTip();

	protected abstract void updateOverlayTip();

	protected abstract void hideOverlayTip();

	protected abstract void showMenu();

	protected abstract boolean isMenuVisible();
	
	protected abstract void hideMenu();

	protected void detach() {
		mapWidget.removeMapMouseMoveHandler(mapEventHandler);
		mapWidget.removeMapDragStartHandler(mapEventHandler);
		mapWidget.removeMapDragEndHandler(mapEventHandler);
		mapWidget.removeMapMouseOutHandler(mapEventHandler);
		mapWidget.removeMapClickHandler(mapEventHandler);
		mapWidget.removeMapZoomEndHandler(mapEventHandler);
	}

	
	protected void overlayMouseOver() {
		mouseOver = true;
		
		if (mouseOver && !isMenuVisible()) {
			showOverlayTip();
		}
	}
	
	protected void overlayMouseOut() {
		mouseOver = false;
		
		hideOverlayTip() ;
	}

	protected void overlayClick() {
		hideOverlayTip();
		showMenu();
	}
	
	protected void overlayStartUpdate() {
		hideOverlayTip();
	}
	
	protected void overlayEndUpdate() {
		showOverlayTip();
	}

	protected void overlayRemove() {
		hideOverlayTip();
		detach();
	}

	protected void mapMouseMove(LatLng latlng) {
		currentLatLng = latlng;
		
		int mapAbsoluteLeft = mapWidget.getAbsoluteLeft();
		int mapAbsoluteTop = mapWidget.getAbsoluteTop();
		currentMouseDivPosition = mapWidget.convertLatLngToContainerPixel(currentLatLng);
		currentMousePosition =  Point.newInstance(currentMouseDivPosition.getX() + mapAbsoluteLeft,
				currentMouseDivPosition.getY() + mapAbsoluteTop);
			
		if (mouseOver && !isMenuVisible()) {
			updateOverlayTip();
		}
	}
	
	protected void mapMouseOut() {
		hideOverlayTip();
	}
	
	protected void mapClick() {
		hideMenu();
	}
	
	protected void mapZoomEnd() {
		hideMenu();
	}
	
	protected void mapDragStart() {
		hideOverlayTip();
		hideMenu();
	}
	
	protected void mapDragEnd() {
		if (mouseOver && !isMenuVisible()) {
			showOverlayTip();
		}
	}

	private class MapEventHandler implements MapMouseMoveHandler,
				MapDragStartHandler, MapDragEndHandler,
				MapMouseOutHandler, MapClickHandler, MapZoomEndHandler {

		public void onMouseMove(MapMouseMoveEvent event) {
			mapMouseMove(event.getLatLng());
		}

		public void onDragStart(MapDragStartEvent event) {
			mapDragStart();
		}

		public void onDragEnd(MapDragEndEvent event) {
			mapDragEnd();
		}

		public void onMouseOut(MapMouseOutEvent event) {
			mapMouseOut();
		}
		
		public void onClick(MapClickEvent event) {
			mapClick();
		}

		public void onZoomEnd(MapZoomEndEvent event) {
			mapZoomEnd();
		}
	}
}