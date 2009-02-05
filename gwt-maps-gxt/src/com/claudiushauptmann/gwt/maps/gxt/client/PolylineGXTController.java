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
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.Window;

public class PolylineGXTController extends PolylineMenuTipController {
	private OverlayTip overlayTip;
	private Menu standardMenu;
	private Menu vertexMenu;
	private Menu startMenu;
	private Menu endMenu;
	private Menu currentMenu;

	public PolylineGXTController(MapWidget mapWidget, Polyline polyline) {
		super(mapWidget, polyline);
	}
	
	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
		GwtMapsGxt.get().setCurrentMenu(currentMenu);
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

	@Override
	protected void showOverlayTip() {
		overlayTip.showAt(currentMousePosition.getX() + 20, currentMousePosition.getY()+20);
		updateOverlayTip();
	}
	
	@Override
	protected void updateOverlayTip() {
		int x = currentMousePosition.getX() + 20;
		int y = currentMousePosition.getY() + 20;
		
		int width = overlayTip.getWidth();
		int height = overlayTip.getHeight();

		if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
			x = currentMousePosition.getX() - 20 - width;
		}
		if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
			y = currentMousePosition.getY() - 20 - height;
		}

		overlayTip.setPosition(x, y);
	}
	
	@Override
	protected void hideOverlayTip() {
		overlayTip.hide();
	}
	
	protected void showStandardMenu(Point position) {
		if (standardMenu != null) {
			MarkerGXTController.MenuTimer.showMenu(standardMenu, position);
			setCurrentMenu(standardMenu);
		}
	}
	
	protected void showVertexMenu(Point position) {
		if (vertexMenu != null) {
			MarkerGXTController.MenuTimer.showMenu(vertexMenu, position);
			setCurrentMenu(vertexMenu);
		} else {
			showStandardMenu(position);
		}
	}
	
	protected void showStartMenu(Point position) {
		if (startMenu != null) {
			MarkerGXTController.MenuTimer.showMenu(startMenu, position);
			setCurrentMenu(startMenu);
		} else {
			showVertexMenu(position);
		}
	}
	
	protected void showEndMenu(Point position) {
		if (endMenu != null) {
			MarkerGXTController.MenuTimer.showMenu(endMenu, position);
			setCurrentMenu(endMenu);
		} else {
			showVertexMenu(position);
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

	@Override
	protected void hideMenu() {
		if ((currentMenu != null) && (currentMenu.isVisible())) {
			currentMenu.hide();
		}
	}	
}