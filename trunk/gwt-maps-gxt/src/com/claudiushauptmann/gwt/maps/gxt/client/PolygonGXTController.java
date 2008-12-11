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
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polygon;

public class PolygonGXTController extends PolygonMenuTipController {
	private OverlayTip overlayTip;
	private Menu standardMenu;
	private Menu vertexMenu;
	private Menu currentMenu;
	
	public PolygonGXTController(MapWidget mapWidget, Polygon polygon) {
		super(mapWidget, polygon);
	}
	
	public OverlayTip getOverlayTip() {
		return overlayTip;
	}

	public void setOverlayTip(OverlayTip overlayTip) {
		this.overlayTip = overlayTip;
	}
	
	public Menu getStandardMenu() {
		return standardMenu;
	}

	public void setStandardMenu(Menu menu) {
		this.standardMenu = menu;
	}

	public Menu getVertexMenu() {
		return vertexMenu;
	}

	public void setVertexMenu(Menu vertexMenu) {
		this.vertexMenu = vertexMenu;
	}

	@Override
	protected void showOverlayTip() {
		overlayTip.showAt(currentMousePosition.getX() + 20, currentMousePosition.getY()+20);
	}
	
	@Override
	protected void updateOverlayTip() {
		overlayTip.setPosition(currentMousePosition.getX()+20, currentMousePosition.getY()+20);
	}
	
	@Override
	protected void hideOverlayTip() {
		overlayTip.hide();
	}
	
	protected void showStandardMenu(Point position) {
		if (standardMenu != null) {
			standardMenu.showAt(position.getX(), position.getY());
			currentMenu = standardMenu;
		}
	}
	
	protected void showVertexMenu(Point position) {
		if (vertexMenu != null) {
			vertexMenu.showAt(position.getX(), position.getY());
			currentMenu = vertexMenu;
		} else {
			showStandardMenu(position);
		}
	}
	
	@Override
	protected boolean isMenuVisible() {
		return (currentMenu != null) && (currentMenu.isVisible());
	}

	@Override
	protected void showMenu() {
		int currentVertex = getCurrentVertex();

		if (currentVertex == -1) {
			showStandardMenu(currentMousePosition);
		} else {
			showVertexMenu(currentMousePosition);
		}
	}
}