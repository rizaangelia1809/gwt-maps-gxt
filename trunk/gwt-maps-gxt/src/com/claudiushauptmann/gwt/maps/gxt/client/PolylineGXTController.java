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

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.PolylineClickHandler;
import com.google.gwt.maps.client.event.PolylineMouseOutHandler;
import com.google.gwt.maps.client.event.PolylineMouseOverHandler;
import com.google.gwt.maps.client.event.PolylineRemoveHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.Timer;

public class PolylineGXTController {
	private Polyline polyline;
	private MapWidget mapWidget;
	
	private OverlayTip overlayTip;
	private OverlayTip vertexOverlayTip;
	private OverlayTip startOverlayTip;
	private OverlayTip endOverlayTip;
	private OverlayTip currentOverlayTip;
	
	private Menu menu;
	private Menu vertexMenu;
	private Menu startMenu;
	private Menu endMenu;
	private Menu currentMenu;
	
	private LatLng currentLatLng;
	private Point currentMousePosition;
	private int currentVertex; 
	private boolean mouseOver;
	
	private EventHandler eventHandler;
	
	public PolylineGXTController(MapWidget mapWidget, Polyline polyline, OverlayTip overlayTip) {
		this.mapWidget = mapWidget;
		this.polyline = polyline;
		this.overlayTip = overlayTip;
		
		mouseOver = false;
		currentVertex = -1;
		
		eventHandler = new EventHandler();
		
		attach();
	}
	
	
	public OverlayTip getOverlayTip() {
		return overlayTip;
	}

	public void setOverlayTip(OverlayTip overlayTip) {
		this.overlayTip = overlayTip;
	}

	public OverlayTip getVertexOverlayTip() {
		return vertexOverlayTip;
	}

	public void setVertexOverlayTip(OverlayTip pointOverlayTip) {
		this.vertexOverlayTip = pointOverlayTip;
	}

	public OverlayTip getStartOverlayTip() {
		return startOverlayTip;
	}

	public void setStartOverlayTip(OverlayTip startOverlayTip) {
		this.startOverlayTip = startOverlayTip;
	}

	public OverlayTip getEndOverlayTip() {
		return endOverlayTip;
	}

	public void setEndOverlayTip(OverlayTip endOverlayTip) {
		this.endOverlayTip = endOverlayTip;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Menu getVertexMenu() {
		return vertexMenu;
	}

	public void setVertexMenu(Menu vertexMenu) {
		this.vertexMenu = vertexMenu;
	}

	public Menu getStartMenu() {
		return startMenu;
	}

	public void setStartMenu(Menu startMenu) {
		this.startMenu = startMenu;
	}

	public Menu getEndMenu() {
		return endMenu;
	}

	public void setEndMenu(Menu endMenu) {
		this.endMenu = endMenu;
	}


	protected void attach() {
		polyline.addPolylineClickHandler(eventHandler);
		polyline.addPolylineMouseOverHandler(eventHandler);
		polyline.addPolylineMouseOutHandler(eventHandler);
		polyline.addPolylineRemoveHandler(eventHandler);
		mapWidget.addMapMouseMoveHandler(eventHandler);
	}
	
	protected void detach() {
		polyline.removePolylineClickHandler(eventHandler);
		polyline.removePolylineMouseOverHandler(eventHandler);
		polyline.removePolylineMouseOutHandler(eventHandler);
		polyline.removePolylineRemoveHandler(eventHandler);
		mapWidget.removeMapMouseMoveHandler(eventHandler);
	}
	
	protected void updateOverlayTip() {
		//Show only if the menu is not visible
		if (mouseOver && ((currentMenu == null) || (!currentMenu.isVisible()))) {
			//Which is the right OverlayTip?
			OverlayTip newOverlayTip = overlayTip;
			if ((currentVertex != -1) && (vertexOverlayTip != null)) {
				newOverlayTip = vertexOverlayTip;
			}
			if ((currentVertex == 0) && (startOverlayTip != null)) {
				newOverlayTip = startOverlayTip;
			}
			if ((currentVertex == polyline.getVertexCount()-1) && (endOverlayTip != null)) {
				newOverlayTip = endOverlayTip;
			}
			
			//Update the OverlayTip
			if (newOverlayTip == currentOverlayTip) {
				currentOverlayTip.setPosition(currentMousePosition.getX()+15, currentMousePosition.getY());
			} else {
				if (currentOverlayTip != null) {
					currentOverlayTip.hide();
					currentOverlayTip = null;
				}
				if (newOverlayTip != null) {
					currentOverlayTip = newOverlayTip;
					currentOverlayTip.showAt(currentMousePosition.getX()+15, currentMousePosition.getY());
				}
			}
		} else {
			if (currentOverlayTip != null) {
				currentOverlayTip.hide();
				currentOverlayTip = null;
			}
		}
	}

	protected void showMenu() {
		currentMenu = menu;
		if ((currentVertex != -1) && (vertexMenu != null)) {
			currentMenu = vertexMenu;
		}
		if ((currentVertex == 0) && (startMenu != null)) {
			currentMenu = startMenu;
		}
		if ((currentVertex == polyline.getVertexCount()-1) && (endMenu!= null)) {
			currentMenu = endMenu;
		}
		
		if (currentMenu != null) {
			currentMenu.showAt(currentMousePosition.getX(), currentMousePosition.getY());
			updateOverlayTip();
		}
	}
	
	private class EventHandler implements PolylineClickHandler,	PolylineMouseOverHandler,
			PolylineMouseOutHandler, PolylineRemoveHandler, MapMouseMoveHandler {

		public void onMouseOver(PolylineMouseOverEvent event) {
			mouseOver = true;
			updateOverlayTip();
		}

		public void onMouseOut(PolylineMouseOutEvent event) {
			mouseOver = false;
			updateOverlayTip();
		}

		public void onMouseMove(MapMouseMoveEvent event) {
			currentLatLng = event.getLatLng();
			currentMousePosition = Utility.LatLng2Point(mapWidget, currentLatLng);

			currentVertex = -1;
			if (mouseOver) {
				for (int i = 0; i < polyline.getVertexCount(); i++) {
					Point vp = Utility.LatLng2Point(mapWidget, polyline.getVertex(i));
					if ((Math.abs(vp.getX()-currentMousePosition.getX())<6)
								&& (Math.abs(vp.getY()-currentMousePosition.getY())<6)) {
						currentVertex = i;
						break;
					}
				}
			}

			updateOverlayTip();
		}

		public void onClick(PolylineClickEvent event) {
			showMenu();
		}

		public void onRemove(PolylineRemoveEvent event) {
			updateOverlayTip();
			detach();
		}
	}
}