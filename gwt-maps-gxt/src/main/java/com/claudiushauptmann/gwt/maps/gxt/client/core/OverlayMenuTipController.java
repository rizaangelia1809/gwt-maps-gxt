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

import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapDragStartHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MapMouseOutHandler;
import com.google.gwt.maps.client.event.MapRightClickHandler;
import com.google.gwt.maps.client.event.MapZoomEndHandler;
import com.google.gwt.maps.client.geom.LatLng;

/**
 * OverlayMenuTipController provides base functionality for controllers of
 * concrete overlays. It registers for map events and provides abstract logic
 * around when to show, update und hide tips and menus. Derived controllers only
 * have to worry about how to show, update and hide tips and menus but not about
 * when to do that.
 */
public abstract class OverlayMenuTipController {

	/**
	 * Stores, whether the mouse is over the overlay.
	 */
	protected boolean mouseOver;

	/**
	 * Stores, the MapMenuController of the map containing the overlay.
	 */
	protected MapMenuController mapMenuController;

	/**
	 * Stores the observer attached to overlay and map.
	 */
	protected MapEventHandler mapEventHandler;

	/**
	 * Creates an OverlayMenuTipController
	 * 
	 * @param mapMenuController
	 *            The MapMenuController that is attached to the map.
	 */
	public OverlayMenuTipController(MapMenuController mapMenuController) {
		this.mapMenuController = mapMenuController;

		mouseOver = false;

		mapEventHandler = new MapEventHandler();
		attachHandlers();
	}

	/**
	 * Hides and shows the Tip if it is visible to refresh it.
	 */
	public void refreshTip() {
		if (isTipVisible()) {
			hideTip();
			showTip();
		}
	}

	/**
	 * This method has to show the Tip of the overlay.
	 */
	protected abstract void showTip();

	/**
	 * Has to return the visibility of the tip.
	 * 
	 * @return The current visibility of the tip.
	 */
	protected abstract boolean isTipVisible();

	/**
	 * Is called, when the position of the tip has to be updated.
	 */
	protected abstract void updateTipPosition();

	/**
	 * Is called when the tip has to be hidden.
	 */
	protected abstract void hideTip();

	/**
	 * Is called when the menu has to be shown.
	 */
	protected abstract void showMenu();

	/**
	 * Has to return the visibility of the menu.
	 * 
	 * @return The visibility of the menu.
	 */
	protected abstract boolean isMenuVisible();

	/**
	 * Is called, when the menu has to be hidden.
	 */
	protected abstract void hideMenu();

	/**
	 * Attaches the observer to the map.
	 */
	protected void attachHandlers() {
		mapMenuController.getMapWidget()
				.addMapDragStartHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapDragEndHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapMouseOutHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapClickHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapZoomEndHandler(mapEventHandler);
	}

	/**
	 * Detaches the observer from the map.
	 */
	protected void detachHandlers() {
		detachMouseOverHandlers();

		mapMenuController.getMapWidget().removeMapDragStartHandler(
				mapEventHandler);
		mapMenuController.getMapWidget().removeMapDragEndHandler(
				mapEventHandler);
		mapMenuController.getMapWidget().removeMapMouseOutHandler(
				mapEventHandler);
		mapMenuController.getMapWidget().removeMapClickHandler(mapEventHandler);
		mapMenuController.getMapWidget().removeMapZoomEndHandler(
				mapEventHandler);
	}

	/**
	 * Attaches mouse move and right click handler to the map. This is separated
	 * from other handlers, because these events are only useful, if the mouse
	 * is over an overlay.
	 */
	protected void attachMouseOverHandlers() {
		mapMenuController.getMapWidget()
				.addMapMouseMoveHandler(mapEventHandler);
		mapMenuController.getMapWidget().addMapRightClickHandler(
				mapEventHandler);
	}

	/**
	 * Detaches mouse move and right click handler from the map. This is
	 * separated from other handlers, because these events are only useful, if
	 * the mouse is over an overlay.
	 */
	protected void detachMouseOverHandlers() {
		mapMenuController.getMapWidget().removeMapMouseMoveHandler(
				mapEventHandler);
		mapMenuController.getMapWidget().removeMapRightClickHandler(
				mapEventHandler);
	}

	/**
	 * Is called when the mouse moves over an overlay. Shows the tip if no menu
	 * is visible and attaches mouse move and right click handlers.
	 */
	protected void overlayMouseOver() {
		mouseOver = true;

		if (mouseOver && !mapMenuController.isMenuVisible()) {
			showTip();
		}

		attachMouseOverHandlers();
	}

	/**
	 * Is called when the mouse leaves an overlay. Detaches mouseOverHandlers
	 * and hides the tip.
	 */
	protected void overlayMouseOut() {
		mouseOver = false;

		detachMouseOverHandlers();

		hideTip();
	}

	/**
	 * Is called when the user right clicks an overlay. Hides the current menu
	 * and shows the menu.
	 */
	protected void overlayRightClick() {
		mapMenuController.hideMenu();
		showMenu();
	}

	/**
	 * Is called when the user clicks an overlay. Hides the current tip.
	 */
	protected void overlayMouseDown() {
		hideTip();
	}

	/**
	 * Is called when an overlay is removed. Hides the current tip and detaches
	 * his handlers.
	 */
	protected void overlayRemove() {
		hideTip();
		detachHandlers();
	}

	/**
	 * Is called when the mouse moves. Updates the position of the tip if the
	 * mouse is over the overlay and no map is visible.
	 * 
	 * @param latlng
	 *            current position of the mouse in latlng.
	 */
	protected void mapMouseMove(LatLng latlng) {
		if (mouseOver && !mapMenuController.isMenuVisible()) {
			updateTipPosition();
		}
	}

	/**
	 * Is called, when the mouse leaves the map. Hides the tip.
	 */
	protected void mapMouseOut() {
		hideTip();
	}

	/**
	 * Is called when the user clicks the map. Hides the menu.
	 */
	protected void mapClick() {
		hideMenu();
	}

	/**
	 * Is called when the user finishes zooming. Hides the menu.
	 */
	protected void mapZoomEnd() {
		hideMenu();
	}

	/**
	 * Is called when the user starts dragging the overlay. Hides tip and menu.
	 */
	protected void mapDragStart() {
		hideTip();
		hideMenu();
	}

	/**
	 * Is called when the user finishes dragging the overlay. Shows the tip if
	 * mouse is over the overlay and no menu is visible.
	 */
	protected void mapDragEnd() {
		if (mouseOver && !mapMenuController.isMenuVisible()) {
			showTip();
		}
	}

	/**
	 * Observer for map events. It's methods call protected methods of the
	 * containing class.
	 */
	private class MapEventHandler implements MapMouseMoveHandler,
			MapDragStartHandler, MapDragEndHandler, MapMouseOutHandler,
			MapClickHandler, MapZoomEndHandler, MapRightClickHandler {

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

		public void onRightClick(MapRightClickEvent event) {
			overlayRightClick();
		}

	}
}