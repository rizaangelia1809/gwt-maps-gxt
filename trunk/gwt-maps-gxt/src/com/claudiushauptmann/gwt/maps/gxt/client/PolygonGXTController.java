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
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.Window;

public class PolygonGXTController extends PolygonMenuTipController {
	private OverlayTip overlayTip;
	private Menu standardMenu;
	private Menu vertexMenu;
	private Menu currentMenu;
	
	public PolygonGXTController(MapMenuController mapMenuController, Polygon polygon) {
		super(mapMenuController, polygon);
	}
	
	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
		mapMenuController.setCurrentMenu(currentMenu);
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
		overlayTip.showAt(mapMenuController.getCurrentMousePosition().getX() + 20,
				mapMenuController.getCurrentMousePosition().getY()+20);
		updateOverlayTip();
	}
	
	@Override
	protected void updateOverlayTip() {
		int x = mapMenuController.getCurrentMousePosition().getX() + 20;
		int y = mapMenuController.getCurrentMousePosition().getY() + 20;
		
		int width = overlayTip.getWidth();
		int height = overlayTip.getHeight();

		if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
			x = mapMenuController.getCurrentMousePosition().getX() - 20 - width;
		}
		if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
			y = mapMenuController.getCurrentMousePosition().getY() - 20 - height;
		}

		overlayTip.setPosition(x, y);
	}
	
	@Override
	protected void hideOverlayTip() {
		overlayTip.hide();
	}
	
	protected void showStandardMenu(Point position) {
		if (standardMenu != null) {
			MapGXTController.MenuTimer.showMenu(standardMenu, position);
			setCurrentMenu(standardMenu);
		}
	}
	
	protected void showVertexMenu(Point position) {
		if (vertexMenu != null) {
			MapGXTController.MenuTimer.showMenu(vertexMenu, position);
			setCurrentMenu(vertexMenu);
		} else {
			showStandardMenu(position);
		}
	}

	@Override
	protected void showMenu() {
		int currentVertex = getCurrentVertex();

		if (currentVertex == -1) {
			showStandardMenu(mapMenuController.getCurrentMousePosition());
		} else {
			showVertexMenu(mapMenuController.getCurrentMousePosition());
		}
	}
	
	@Override
	protected boolean isMenuVisible() {
		return (currentMenu != null) && (currentMenu.isVisible());
	}

	@Override
	protected void hideMenu() {
		if ((currentMenu != null) && (currentMenu.isVisible())) {
			currentMenu.hide();
		}
	}	
}