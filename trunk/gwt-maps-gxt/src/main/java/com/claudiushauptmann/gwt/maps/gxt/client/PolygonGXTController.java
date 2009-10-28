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

import com.claudiushauptmann.gwt.maps.gxt.client.core.PolygonMenuTipController;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.Window;

public class PolygonGXTController extends PolygonMenuTipController {
	private MapGXTController mapGXTController;
	private TipProvider tipProvider;
	private Tip currentTip;
	private MenuProvider standardMenuProvider;
	private MenuProvider vertexMenuProvider;
	private Menu currentMenu;
	
	
	public PolygonGXTController(MapGXTController mapGXTController, Polygon polygon) {
		super(mapGXTController, polygon);
		
		this.mapGXTController = mapGXTController;
	}
	
	
	public TipProvider getTipProvider() {
		return tipProvider;
	}

	
	public void setTipProvider(TipProvider tipProvider) {
		this.tipProvider = tipProvider;
	}

	
	protected Tip getCurrentTip() {
		return currentTip;
	}

	
	protected void setCurrentTip(Tip currentTip) {
		this.currentTip = currentTip;
	}
	
	
	public MenuProvider getStandardMenuProvider() {
		return standardMenuProvider;
	}

	
	public void setStandardMenuProvider(MenuProvider standardMenuProvider) {
		this.standardMenuProvider = standardMenuProvider;
	}

	
	public MenuProvider getVertexMenuProvider() {
		return vertexMenuProvider;
	}

	
	public void setVertexMenuProvider(MenuProvider vertexMenuProvider) {
		this.vertexMenuProvider = vertexMenuProvider;
	}
	
	
	protected Menu getCurrentMenu() {
		return currentMenu;
	}

	
	protected void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
		mapGXTController.setCurrentMenu(currentMenu);
	}

	
	@Override
	protected void showTip() {
		if (tipProvider != null) {
			setCurrentTip(getTipProvider().getTip());
			currentTip.showAt(mapMenuController.getCurrentMousePosition().getX() + 20,
					mapMenuController.getCurrentMousePosition().getY()+20);
			updateTipPosition();
		}
	}
	
	@Override
	protected boolean isTipVisible() {
		return ((currentTip != null) && currentTip.isVisible());
	}

	
	@Override
	protected void updateTipPosition() {
		if (currentTip != null) {
			int x = mapMenuController.getCurrentMousePosition().getX() + 20;
			int y = mapMenuController.getCurrentMousePosition().getY() + 20;
			
			int width = currentTip.getWidth();
			int height = currentTip.getHeight();
	
			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
				x = mapMenuController.getCurrentMousePosition().getX() - 20 - width;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
				y = mapMenuController.getCurrentMousePosition().getY() - 20 - height;
			}
	
			currentTip.setPosition(x, y);
		}
	}
	
	@Override
	protected void hideTip() {
		if (currentTip != null) {
			currentTip.hide();
		}
	}
	
	protected void showStandardMenu(Point position) {		
		if (standardMenuProvider != null) {
			setCurrentMenu(getStandardMenuProvider().getMenu());
			hideTip();
			mapGXTController.showMenu(currentMenu, position);
			setCurrentMenu(currentMenu);
		}
	}
	
	protected void showVertexMenu(Point position) {
		if (vertexMenuProvider != null) {
			setCurrentMenu(getVertexMenuProvider().getMenu());
			hideTip();
			mapGXTController.showMenu(currentMenu, position);
			setCurrentMenu(currentMenu);
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