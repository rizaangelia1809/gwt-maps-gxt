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

import com.google.gwt.maps.client.event.MarkerMouseOutHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.event.MarkerRemoveHandler;
import com.google.gwt.maps.client.overlay.Marker;

/**
 * Base controller for Markers that derives from OverlayMenuTipController.
 * Handles the marker events and calls the handlers of OverlayMenuTipController.
 */
public abstract class MarkerMenuTipController extends OverlayMenuTipController {

	/**
	 * The marker to be observed.
	 */
	protected Marker marker;

	/**
	 * The observer attached the marker.
	 */
	private MarkerEventHandler markerEventHandler;

	/**
	 * Creates a MarkerMenuTipController.
	 * 
	 * @param mapMenuController
	 *            The MapMenuController attached to the map.
	 * @param marker
	 *            The marker that has to be observed.
	 */
	public MarkerMenuTipController(MapMenuController mapMenuController,
			Marker marker) {
		super(mapMenuController);

		this.marker = marker;

		markerEventHandler = new MarkerEventHandler();
		marker.addMarkerMouseOverHandler(markerEventHandler);
		marker.addMarkerMouseOutHandler(markerEventHandler);
		marker.addMarkerRemoveHandler(markerEventHandler);
	}

	/**
	 * Overrides the base method to detach it's observer from the marker.
	 */
	@Override
	protected void detachHandlers() {
		super.detachHandlers();

		marker.removeMarkerMouseOverHandler(markerEventHandler);
		marker.removeMarkerMouseOutHandler(markerEventHandler);
		marker.removeMarkerRemoveHandler(markerEventHandler);
	}

	/**
	 * Observer class for marker events.
	 */
	private class MarkerEventHandler implements MarkerMouseOverHandler,
			MarkerMouseOutHandler, MarkerRemoveHandler {

		public void onMouseOver(MarkerMouseOverEvent event) {
			overlayMouseOver();
		}

		public void onMouseOut(MarkerMouseOutEvent event) {
			overlayMouseOut();
		}

		public void onRemove(MarkerRemoveEvent event) {
			overlayRemove();
		}
	}
}
