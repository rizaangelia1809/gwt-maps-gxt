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
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
import com.google.gwt.maps.client.event.MarkerMouseOutHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.event.MarkerRemoveHandler;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Marker;

/**
 * Attaches an OverlayTip to a Marker.
 */
public class MarkerTipController{

	private Marker marker;
	private MapWidget mapWidget;
	private OverlayTip overlayTip;
	private Point offset;

	private MarkerEventHandler markerEventHandler;
	
	/**
	 * Creates a {@link MarkerTipController}
	 * 
	 * @param mapWidget
	 * @param marker
	 * @param overlayTip
	 * @param offset
	 */
	public MarkerTipController(MapWidget mapWidget, Marker marker,
			OverlayTip overlayTip, Point offset) {
		this.mapWidget = mapWidget;
		this.marker = marker;
		this.overlayTip = overlayTip;
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
	public OverlayTip getOverlayTip() {
		return overlayTip;
	}

	/**
	 * Sets the current OverlayTip.
	 */
	public void setOverlayTip(OverlayTip overlayTip) {
		this.overlayTip = overlayTip;
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
	
	/**
	 * attaches the event handlers to the marker
	 */
	protected void attach() {
		marker.addMarkerMouseOverHandler(markerEventHandler);
		marker.addMarkerMouseOutHandler(markerEventHandler);
		marker.addMarkerClickHandler(markerEventHandler);
		marker.addMarkerDragStartHandler(markerEventHandler);
		marker.addMarkerRemoveHandler(markerEventHandler);
	}
	
	/**
	 * Removes the event handlers from the marker
	 */
	protected void detach() {
		marker.removeMarkerMouseOverHandler(markerEventHandler);
		marker.removeMarkerMouseOutHandler(markerEventHandler);
		marker.removeMarkerClickHandler(markerEventHandler);
		marker.removeMarkerDragStartHandler(markerEventHandler);
		marker.removeMarkerRemoveHandler(markerEventHandler);
	}
	
	/**
	 * Shows the OverlayTip.
	 */
	protected void showTip() {
		Point p = Utility.LatLng2Point(mapWidget, marker.getLatLng());
		overlayTip.showAt(p.getX()+offset.getX(), p.getY()+offset.getY());
	}
	
	/**
	 * Hides the OverlayTip.
	 */
	protected void hideTip() {
		overlayTip.hide();
	}

	
	private class MarkerEventHandler implements	MarkerMouseOverHandler,
			MarkerMouseOutHandler, MarkerClickHandler, MarkerDragStartHandler,
			MarkerRemoveHandler {
		
		public void onMouseOut(MarkerMouseOutEvent event) {
			hideTip();
		}
	
		public void onMouseOver(MarkerMouseOverEvent event) {
			showTip();
		}
	
		public void onClick(MarkerClickEvent event) {
			hideTip();
		}
	
		public void onDragStart(MarkerDragStartEvent event) {
			hideTip();
		}
	
		public void onRemove(MarkerRemoveEvent event) {
			hideTip();
			detach();
		}
	}
}
