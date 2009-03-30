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

import com.google.gwt.maps.client.event.PolylineMouseOutHandler;
import com.google.gwt.maps.client.event.PolylineMouseOverHandler;
import com.google.gwt.maps.client.event.PolylineRemoveHandler;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polyline;

public abstract class PolylineMenuTipController extends PolyOverlayMenuTipController {

	protected Polyline polyline;
	private PolylineEventHandler polylineEventHandler;
	public PolylineMenuTipController(MapMenuController mapMenuController, Polyline polyline) {
		super(mapMenuController);
		
		this.polyline = polyline;
		
		polylineEventHandler = new PolylineEventHandler();			
		polyline.addPolylineMouseOverHandler(polylineEventHandler);
		polyline.addPolylineMouseOutHandler(polylineEventHandler);
		polyline.addPolylineRemoveHandler(polylineEventHandler);
	}
	
	@Override
	protected int getCurrentVertex() {
		int temp = -1;
		for (int i = 0; i < polyline.getVertexCount(); i++) {
			Point vp = mapMenuController.getMapWidget().convertLatLngToContainerPixel(polyline.getVertex(i));
			if ((Math.abs(vp.getX()-mapMenuController.getCurrentMouseDivPosition().getX())<7)
						&& (Math.abs(vp.getY()-mapMenuController.getCurrentMouseDivPosition().getY())<7)) {
				temp = i;
				break;
			}
		}
		return temp;
	}

	@Override
	protected void detach() {
		super.detach();
		
		polyline.removePolylineMouseOverHandler(polylineEventHandler);
		polyline.removePolylineMouseOutHandler(polylineEventHandler);
		polyline.removePolylineRemoveHandler(polylineEventHandler);
	}
	
	private class PolylineEventHandler implements PolylineMouseOverHandler,
				PolylineMouseOutHandler, PolylineRemoveHandler {
		
		public void onMouseOver(PolylineMouseOverEvent event) {
			overlayMouseOver();
		}
		
		public void onMouseOut(PolylineMouseOutEvent event) {
			overlayMouseOut();
		}
		
		public void onRemove(PolylineRemoveEvent event) {
			overlayRemove();
		}
	}
}