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

public class MarkerTipWrapper implements
			MarkerMouseOverHandler,
			MarkerMouseOutHandler,
			MarkerClickHandler,
			MarkerDragStartHandler,
			MarkerRemoveHandler {

	private Marker marker;
	private MapWidget mapWidget;
	private OverlayTip overlayTip;
	private Point offset;
	
	public MarkerTipWrapper(MapWidget mapWidget, Marker marker, OverlayTip overlayTip) {
		this.mapWidget = mapWidget;
		this.marker = marker;
		this.overlayTip = overlayTip;
		offset = Point.newInstance(0, 0);

		marker.addMarkerMouseOverHandler(this);
		marker.addMarkerMouseOutHandler(this);
		marker.addMarkerClickHandler(this);
		marker.addMarkerDragStartHandler(this);
		marker.addMarkerRemoveHandler(this);
	}
	
	public MapWidget getMapWidget() {
		return mapWidget;
	}

	public Marker getMarker() {
		return marker;
	}

	public OverlayTip getOverlayTip() {
		return overlayTip;
	}

	public void setOverlayTip(OverlayTip overlayTip) {
		this.overlayTip = overlayTip;
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}

	
	public void onMouseOut(MarkerMouseOutEvent event) {
		overlayTip.hide();
	}

	public void onMouseOver(MarkerMouseOverEvent event) {
		Point p = Utility.LatLng2Point(mapWidget, marker.getLatLng());
		overlayTip.showAt(p.getX()+offset.getX(), p.getY()+offset.getY());
	}

	public void onClick(MarkerClickEvent event) {
		overlayTip.hide();
	}

	public void onDragStart(MarkerDragStartEvent event) {
		overlayTip.hide();
	}

	public void onRemove(MarkerRemoveEvent event) {
		overlayTip.hide();
		
		marker.removeMarkerMouseOverHandler(this);
		marker.removeMarkerMouseOutHandler(this);
		marker.removeMarkerClickHandler(this);
		marker.removeMarkerDragStartHandler(this);
		marker.removeMarkerRemoveHandler(this);
	}
}
