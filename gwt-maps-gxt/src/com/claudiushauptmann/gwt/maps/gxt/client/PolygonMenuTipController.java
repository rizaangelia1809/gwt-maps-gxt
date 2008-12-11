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
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.event.PolygonLineUpdatedHandler;
import com.google.gwt.maps.client.event.PolygonMouseOutHandler;
import com.google.gwt.maps.client.event.PolygonMouseOverHandler;
import com.google.gwt.maps.client.event.PolygonRemoveHandler;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polygon;

public abstract class PolygonMenuTipController extends PolyOverlayMenuTipController {

	protected Polygon polygon;
	private PolygonEventHandler polygonEventHandler;
	
	public PolygonMenuTipController(MapWidget mapWidget, Polygon polygon) {
		super(mapWidget);
		
		this.polygon = polygon;
		
		polygonEventHandler = new PolygonEventHandler();			
		polygon.addPolygonClickHandler(polygonEventHandler);
		polygon.addPolygonMouseOverHandler(polygonEventHandler);
		polygon.addPolygonMouseOutHandler(polygonEventHandler);
		polygon.addPolygonRemoveHandler(polygonEventHandler);
		polygon.addPolygonLineUpdatedHandler(polygonEventHandler);
	}
	
	@Override
	protected int getCurrentVertex() {
		int temp = -1;
		for (int i = 0; i < polygon.getVertexCount(); i++) {
			Point vp = mapWidget.convertLatLngToContainerPixel(polygon.getVertex(i));
			if ((Math.abs(vp.getX()-currentMousePosition.getX())<7)
						&& (Math.abs(vp.getY()-currentMousePosition.getY())<7)) {
				temp = i;
				break;
			}
		}
		return temp;
	}
	
	@Override
	protected void detach() {
		super.detach();
		
		polygon.removePolygonClickHandler(polygonEventHandler);
		polygon.removePolygonMouseOverHandler(polygonEventHandler);
		polygon.removePolygonMouseOutHandler(polygonEventHandler);
		polygon.removePolygonRemoveHandler(polygonEventHandler);
		polygon.removePolygonLineUpdatedHandler(polygonEventHandler);
	}
	
	private class PolygonEventHandler implements PolygonClickHandler,	PolygonMouseOverHandler,
				PolygonMouseOutHandler, PolygonRemoveHandler, PolygonLineUpdatedHandler {
		
		public void onMouseOver(PolygonMouseOverEvent event) {
			mouseOver();
		}
		
		public void onMouseOut(PolygonMouseOutEvent event) {
			mouseOut();
		}
		
		public void onClick(PolygonClickEvent event) {
			click();
		}
		
		public void onRemove(PolygonRemoveEvent event) {
			remove();
		}

		public void onUpdate(PolygonLineUpdatedEvent event) {
			endUpdate();
		}
	}
}