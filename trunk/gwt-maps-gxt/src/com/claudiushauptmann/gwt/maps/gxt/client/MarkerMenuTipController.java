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
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
import com.google.gwt.maps.client.event.MarkerMouseOutHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.event.MarkerRemoveHandler;
import com.google.gwt.maps.client.overlay.Marker;

public abstract class MarkerMenuTipController extends OverlayMenuTipController {
	
	protected Marker marker;
	private MarkerEventHandler markerEventHandler;
	
	public MarkerMenuTipController(MapWidget mapWidget, Marker marker) {
		super(mapWidget);
		
		this.marker = marker;
		
		markerEventHandler = new MarkerEventHandler();
		marker.addMarkerMouseOverHandler(markerEventHandler);
		marker.addMarkerMouseOutHandler(markerEventHandler);
		marker.addMarkerClickHandler(markerEventHandler);
		marker.addMarkerRemoveHandler(markerEventHandler);
		marker.addMarkerDragStartHandler(markerEventHandler);
		marker.addMarkerDragEndHandler(markerEventHandler);
	}

	@Override
	protected void detach() {
		super.detach();
		
		marker.removeMarkerClickHandler(markerEventHandler);
		marker.removeMarkerMouseOverHandler(markerEventHandler);
		marker.removeMarkerMouseOutHandler(markerEventHandler);
		marker.removeMarkerRemoveHandler(markerEventHandler);
		marker.removeMarkerDragStartHandler(markerEventHandler);
		marker.removeMarkerDragEndHandler(markerEventHandler);
	}

	private class MarkerEventHandler implements MarkerMouseOverHandler, MarkerMouseOutHandler,
				MarkerClickHandler, MarkerRemoveHandler, MarkerDragStartHandler,
				MarkerDragEndHandler {

		public void onMouseOver(MarkerMouseOverEvent event) {
			overlayMouseOver();
		}
		
		public void onMouseOut(MarkerMouseOutEvent event) {
			overlayMouseOut();
		}

		public void onClick(MarkerClickEvent event) {
			overlayClick();
		}

		public void onRemove(MarkerRemoveEvent event) {
			overlayRemove();
		}

		public void onDragStart(MarkerDragStartEvent event) {
			overlayStartUpdate();
		}
		
		public void onDragEnd(MarkerDragEndEvent event) {
			overlayEndUpdate();
		}
	}
}
