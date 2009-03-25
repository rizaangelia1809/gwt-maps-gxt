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

import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapDragStartHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MapMouseOutHandler;
import com.google.gwt.maps.client.event.MapZoomEndHandler;
import com.google.gwt.maps.client.geom.LatLng;

public abstract class OverlayMenuTipController {

	protected boolean mouseOver;
	protected MapMenuController mapMenuController;
	protected MapEventHandler mapEventHandler;

	public OverlayMenuTipController(MapMenuController mapWidget) {
		this.mapMenuController = mapWidget;
		
		mouseOver = false;

		mapEventHandler = new MapEventHandler();
		mapMenuController.getMapWidget().addMapDragStartHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapDragEndHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapMouseOutHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapClickHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapZoomEndHandler(mapEventHandler);
	}

	
	protected abstract void showTip();

	protected abstract void updateTip();

	protected abstract void hideTip();

	protected abstract void showMenu();

	protected abstract boolean isMenuVisible();
	
	protected abstract void hideMenu();

	protected void detach() {
		mapMenuController.getMapWidget().removeMapDragStartHandler(mapEventHandler);
		mapMenuController.getMapWidget().removeMapDragEndHandler(mapEventHandler);
		mapMenuController.getMapWidget().removeMapMouseOutHandler(mapEventHandler);
		mapMenuController.getMapWidget().removeMapClickHandler(mapEventHandler);
		mapMenuController.getMapWidget().removeMapZoomEndHandler(mapEventHandler);
	}
	
	protected void attachMouseMoveHandler() {
		mapMenuController.getMapWidget().addMapMouseMoveHandler(mapEventHandler);
	}
	
	protected void detachMouseMoveHandler() {
		mapMenuController.getMapWidget().removeMapMouseMoveHandler(mapEventHandler);
	}

	
	protected void overlayMouseOver() {
		mouseOver = true;
		
		if (mouseOver && !mapMenuController.isMenuVisible()) {
			showTip();
		}
		
		attachMouseMoveHandler();
	}
	
	protected void overlayMouseOut() {
		mouseOver = false;
		
		detachMouseMoveHandler();
		
		hideTip();
	}

	protected void overlayClick() {
		showMenu();
	}
	
	protected void overlayStartUpdate() {
		hideTip();
	}
	
	protected void overlayEndUpdate() {

	}

	protected void overlayRemove() {
		hideTip();
		detach();
	}

	protected void mapMouseMove(LatLng latlng) {
		if (mouseOver && !mapMenuController.isMenuVisible()) {
			updateTip();
		}
	}
	
	protected void mapMouseOut() {
		hideTip();
	}
	
	protected void mapClick() {
		hideMenu();
	}
	
	protected void mapZoomEnd() {
		hideMenu();
	}
	
	protected void mapDragStart() {
		hideTip();
		hideMenu();
	}
	
	protected void mapDragEnd() {
		if (mouseOver && !mapMenuController.isMenuVisible()) {
			showTip();
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