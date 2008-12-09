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
import com.google.gwt.maps.client.event.MapMouseMoveHandler.MapMouseMoveEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polyline;

public class PolylineGXTController {
	private Polyline polyline;
	private MapWidget mapWidget;
	
	private OverlayTip overlayTip;
	
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
		
		subscribe();
	}
	
	/* GXT specific */
	
	public OverlayTip getOverlayTip() {
		return overlayTip;
	}

	public void setOverlayTip(OverlayTip overlayTip) {
		this.overlayTip = overlayTip;
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
	
	protected void showOverlayTip() {
		overlayTip.showAt(currentMousePosition.getX()-overlayTip.getWidth()-20, currentMousePosition.getY());
	}
	
	protected void updateOverlayTip() {
		overlayTip.setPosition(currentMousePosition.getX()-overlayTip.getWidth()-20, currentMousePosition.getY());
	}
	
	protected void hideOverlayTip() {
		overlayTip.hide();
	}
	
	protected void showPolylineMenu(Point position) {
		if (menu != null) {
			menu.showAt(position.getX(), position.getY());
			currentMenu = menu;
		}
	}
	
	protected void showVertexMenu(Point position) {
		if (vertexMenu != null) {
			vertexMenu.showAt(position.getX(), position.getY());
			currentMenu = vertexMenu;
		} else {
			showPolylineMenu(position);
		}
	}
	
	protected void showStartMenu(Point position) {
		if (startMenu != null) {
			startMenu.showAt(position.getX(), position.getY());
			currentMenu = startMenu;
		} else {
			showVertexMenu(position);
		}
	}
	
	protected void showEndMenu(Point position) {
		if (endMenu != null) {
			endMenu.showAt(position.getX(), position.getY());
			currentMenu = endMenu;
		} else {
			showVertexMenu(position);
		}
	}
	
	protected boolean isMenuVisible() {
		return (currentMenu == null) || (!currentMenu.isVisible());
	}

	/* non GXT specific */
	
	protected void subscribe() {
		polyline.addPolylineClickHandler(eventHandler);
		polyline.addPolylineMouseOverHandler(eventHandler);
		polyline.addPolylineMouseOutHandler(eventHandler);
		polyline.addPolylineRemoveHandler(eventHandler);
		mapWidget.addMapMouseMoveHandler(eventHandler);
	}
	
	protected void unsubscribe() {
		polyline.removePolylineClickHandler(eventHandler);
		polyline.removePolylineMouseOverHandler(eventHandler);
		polyline.removePolylineMouseOutHandler(eventHandler);
		polyline.removePolylineRemoveHandler(eventHandler);
		mapWidget.removeMapMouseMoveHandler(eventHandler);
	}

	protected void showMenu() {
	}
	
	private class EventHandler implements PolylineClickHandler,	PolylineMouseOverHandler,
			PolylineMouseOutHandler, PolylineRemoveHandler, MapMouseMoveHandler {

		public void onMouseOver(PolylineMouseOverEvent event) {
			mouseOver = true;
			
			if (mouseOver && isMenuVisible()) {
				showOverlayTip();
			}
		}

		public void onMouseMove(MapMouseMoveEvent event) {
			currentLatLng = event.getLatLng();
			currentMousePosition = Utility.LatLng2Point(mapWidget, currentLatLng);
		
			currentVertex = -1;
//			if (mouseOver) {
				for (int i = 0; i < polyline.getVertexCount(); i++) {
					Point vp = Utility.LatLng2Point(mapWidget, polyline.getVertex(i));
					if ((Math.abs(vp.getX()-currentMousePosition.getX())<7)
								&& (Math.abs(vp.getY()-currentMousePosition.getY())<7)) {
						currentVertex = i;
						break;
					}
				}
//			}
		
			if (mouseOver && isMenuVisible()) {
				updateOverlayTip();
			}
		}

		public void onMouseOut(PolylineMouseOutEvent event) {
			mouseOver = false;
			hideOverlayTip() ;
		}

		public void onClick(PolylineClickEvent event) {
			hideOverlayTip();

			if (currentVertex == -1) {
				showPolylineMenu(currentMousePosition);
			}
			if ((currentVertex > 0) && (currentVertex < polyline.getVertexCount()-1)) {
				showVertexMenu(currentMousePosition);
			}
			if (currentVertex == 0) {
				showStartMenu(currentMousePosition);
			}
			if (currentVertex == polyline.getVertexCount()-1) {
				showEndMenu(currentMousePosition);
			}
		}

		public void onRemove(PolylineRemoveEvent event) {
			hideOverlayTip();
			unsubscribe();
		}
	}
}