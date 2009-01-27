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
import com.google.gwt.maps.client.event.PolylineClickHandler;
import com.google.gwt.maps.client.event.PolylineLineUpdatedHandler;
import com.google.gwt.maps.client.event.PolylineMouseOutHandler;
import com.google.gwt.maps.client.event.PolylineMouseOverHandler;
import com.google.gwt.maps.client.event.PolylineRemoveHandler;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polyline;

public abstract class PolylineMenuTipController extends PolyOverlayMenuTipController {

	protected Polyline polyline;
	private PolylineEventHandler polylineEventHandler;
	public PolylineMenuTipController(MapWidget mapWidget, Polyline polyline) {
		super(mapWidget);
		
		this.polyline = polyline;
		
		polylineEventHandler = new PolylineEventHandler();			
		polyline.addPolylineClickHandler(polylineEventHandler);
		polyline.addPolylineMouseOverHandler(polylineEventHandler);
		polyline.addPolylineMouseOutHandler(polylineEventHandler);
		polyline.addPolylineRemoveHandler(polylineEventHandler);
		polyline.addPolylineLineUpdatedHandler(polylineEventHandler);
	}
	
	@Override
	protected int getCurrentVertex() {
		int temp = -1;
		for (int i = 0; i < polyline.getVertexCount(); i++) {
			Point vp = mapWidget.convertLatLngToContainerPixel(polyline.getVertex(i));
			if ((Math.abs(vp.getX()-currentMouseDivPosition.getX())<7)
						&& (Math.abs(vp.getY()-currentMouseDivPosition.getY())<7)) {
				temp = i;
				break;
			}
		}
		return temp;
	}

	@Override
	protected void detach() {
		super.detach();
		
		polyline.removePolylineClickHandler(polylineEventHandler);
		polyline.removePolylineMouseOverHandler(polylineEventHandler);
		polyline.removePolylineMouseOutHandler(polylineEventHandler);
		polyline.removePolylineRemoveHandler(polylineEventHandler);
		polyline.removePolylineLineUpdatedHandler(polylineEventHandler);
	}
	
	private class PolylineEventHandler implements PolylineClickHandler,	PolylineMouseOverHandler,
				PolylineMouseOutHandler, PolylineRemoveHandler, PolylineLineUpdatedHandler {
		
		public void onMouseOver(PolylineMouseOverEvent event) {
			overlayMouseOver();
		}
		
		public void onMouseOut(PolylineMouseOutEvent event) {
			overlayMouseOut();
		}
		
		public void onClick(PolylineClickEvent event) {
			overlayClick();
		}
		
		public void onRemove(PolylineRemoveEvent event) {
			overlayRemove();
		}

		public void onUpdate(PolylineLineUpdatedEvent event) {
			overlayEndUpdate();
		}
	}
}