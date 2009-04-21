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

import com.claudiushauptmann.gwt.maps.gxt.client.core.MarkerMenuTipController;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Window;

public class MarkerGXTController extends MarkerMenuTipController {
	private MapGXTController mapGXTController;
	private TipProvider tipProvider;
	private Tip currentTip;
	private MenuProvider menuProvider;
	private Menu currentMenu;
	
	
	public MarkerGXTController(MapGXTController mapGXTController, Marker marker) {
		super(mapGXTController, marker);
		
		this.mapGXTController = mapGXTController;
	}

	
	public TipProvider getTipProvider() {
		return tipProvider;
	}

	
	public void setTipProvider(TipProvider tipProvider) {
		this.tipProvider = tipProvider;
	}
	
	
	protected Tip getCurrentTip() {
		return this.currentTip;
	}
	
	
	protected void setCurrentTip(Tip currentTip) {
		this.currentTip = currentTip;
	}
	
	
	public MenuProvider getMenuProvider() {
		return menuProvider;
	}

	
	public void setMenuProvider(MenuProvider menuProvider) {
		this.menuProvider = menuProvider;
	}

	
	protected Menu getCurrentMenu() {
		return currentMenu;
	}

	
	protected void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
	}

	
	@Override
	protected void showTip() {
		setCurrentTip(getTipProvider().getTip());
		
		if (currentTip != null) {
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

	
	@Override
	protected void showMenu() {
		setCurrentMenu(getMenuProvider().getMenu());
		
		if (currentMenu != null) {
			hideTip();
			MapGXTController.MenuTimer.showMenu(currentMenu, mapMenuController.getCurrentMousePosition());
			mapGXTController.setCurrentMenu(currentMenu);
		}
	}

	
	@Override
	protected boolean isMenuVisible() {
		boolean result;
		
		if (currentMenu != null) {
			result = currentMenu.isVisible();
		} else {
			result = false;
		}
		
		return result;
	}

	
	@Override
	protected void hideMenu() {
		if ((currentMenu != null) && (currentMenu.isVisible())) {
			currentMenu.hide();
		}
	}	
}
