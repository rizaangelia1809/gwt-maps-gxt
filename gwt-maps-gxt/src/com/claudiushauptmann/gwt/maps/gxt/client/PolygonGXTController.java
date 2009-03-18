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
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.Window;

public class PolygonGXTController extends PolygonMenuTipController {
	private Tip tip;
	private Menu standardMenu;
	private Menu vertexMenu;
	private Menu currentMenu;
	private MapGXTController mapGXTController;
	
	public PolygonGXTController(MapGXTController mapGXTController, Polygon polygon) {
		super(mapGXTController, polygon);
		
		this.mapGXTController = mapGXTController;
	}
	
	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
		mapGXTController.setCurrentMenu(currentMenu);
	}

	public Tip getTip() {
		return tip;
	}

	public void setTip(Tip tip) {
		this.tip = tip;
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
	protected void showTip() {
		if (tip != null) {
			tip.showAt(mapMenuController.getCurrentMousePosition().getX() + 20,
					mapMenuController.getCurrentMousePosition().getY()+20);
			updateTip();
		}
	}
	
	@Override
	protected void updateTip() {
		if (tip != null) {
			int x = mapMenuController.getCurrentMousePosition().getX() + 20;
			int y = mapMenuController.getCurrentMousePosition().getY() + 20;
			
			int width = tip.getWidth();
			int height = tip.getHeight();
	
			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
				x = mapMenuController.getCurrentMousePosition().getX() - 20 - width;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
				y = mapMenuController.getCurrentMousePosition().getY() - 20 - height;
			}
	
			tip.setPosition(x, y);
		}
	}
	
	@Override
	protected void hideTip() {
		if (tip != null) {
			tip.hide();
		}
	}
	
	protected void showStandardMenu(Point position) {
		if (standardMenu != null) {
			hideTip();
			MapGXTController.MenuTimer.showMenu(standardMenu, position);
			setCurrentMenu(standardMenu);
		}
	}
	
	protected void showVertexMenu(Point position) {
		if (vertexMenu != null) {
			hideTip();
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