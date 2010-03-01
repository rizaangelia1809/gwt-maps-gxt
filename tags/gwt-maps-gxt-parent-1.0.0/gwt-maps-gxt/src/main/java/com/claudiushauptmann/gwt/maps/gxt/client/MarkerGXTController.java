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

/**
 * Extends the MarkerMenuTipController about gxt related logic that shows the
 * menu. Attach an instance of this class to your Polyline to use gxt menus and
 * tips.
 */
public class MarkerGXTController extends MarkerMenuTipController {

	/**
	 * The controller of the map the marker is attached to.
	 */
	private MapGXTController mapGXTController;

	/**
	 * The TipProvider.
	 */
	private TipProvider tipProvider;

	/**
	 * The currently shown tip.
	 */
	private Tip currentTip;

	/**
	 * The MenuProvider.
	 */
	private MenuProvider menuProvider;

	/**
	 * The currently shown menu.
	 */
	private Menu currentMenu;

	/**
	 * Creates a MarkerGXTController.
	 * 
	 * @param mapGXTController
	 *            The MapGXTController of the map the Marker is attached to.
	 * @param marker
	 *            The Marker to be observed.
	 */
	public MarkerGXTController(MapGXTController mapGXTController, Marker marker) {
		super(mapGXTController, marker);

		this.mapGXTController = mapGXTController;
	}

	/**
	 * Returns the tip provider.
	 * 
	 * @return The tip provider.
	 */
	public TipProvider getTipProvider() {
		return tipProvider;
	}

	/**
	 * Sets the tip provider.
	 * 
	 * @param tipProvider
	 *            The tip provider.
	 */
	public void setTipProvider(TipProvider tipProvider) {
		this.tipProvider = tipProvider;
	}

	/**
	 * Returns the current tip.
	 * 
	 * @return The current tip.
	 */
	protected Tip getCurrentTip() {
		return this.currentTip;
	}

	/**
	 * Sets the current tip.
	 * 
	 * @param currentTip
	 *            The current tip.
	 */
	protected void setCurrentTip(Tip currentTip) {
		this.currentTip = currentTip;
	}

	/**
	 * Returns the menu provider.
	 * 
	 * @return The menu provider.
	 */
	public MenuProvider getMenuProvider() {
		return menuProvider;
	}

	/**
	 * Sets the menu provider.
	 * 
	 * @param menuProvider
	 *            The menu provider.
	 */
	public void setMenuProvider(MenuProvider menuProvider) {
		this.menuProvider = menuProvider;
	}

	/**
	 * Returns the current menu.
	 * 
	 * @return The current menu.
	 */
	protected Menu getCurrentMenu() {
		return currentMenu;
	}

	/**
	 * Sets the current menu.
	 * 
	 * @param currentMenu
	 *            The current menu.
	 */
	protected void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
	}

	/**
	 * Shows the current tip. This method does the real work without having to
	 * decide when to do it.
	 */
	@Override
	protected void showTip() {
		setCurrentTip(getTipProvider().getTip());

		if (currentTip != null) {
			currentTip.showAt(mapMenuController.getCurrentMousePosition()
					.getX() + 20, mapMenuController.getCurrentMousePosition()
					.getY() + 20);
			updateTipPosition();
		}
	}

	/**
	 * Returns the current visibility of the tip. This method is called by its
	 * base class when it has to know whether a tip is visible.
	 * 
	 * @return The current visibility of the tip.
	 */
	@Override
	protected boolean isTipVisible() {
		return ((currentTip != null) && currentTip.isVisible());
	}

	/**
	 * Updates the tip position. This method is called by its base class when
	 * the mouse moves and the tip position has to be updated.
	 */
	@Override
	protected void updateTipPosition() {
		if (currentTip != null) {
			int x = mapMenuController.getCurrentMousePosition().getX() + 20;
			int y = mapMenuController.getCurrentMousePosition().getY() + 20;

			int width = currentTip.getWidth();
			int height = currentTip.getHeight();

			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft()
					- 10) {
				x = mapMenuController.getCurrentMousePosition().getX() - 20
						- width;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop()
					- 10) {
				y = mapMenuController.getCurrentMousePosition().getY() - 20
						- height;
			}

			currentTip.setPosition(x, y);
		}
	}

	/**
	 * Hides the tip. This method is called by its base class when the tip has
	 * to be hidden.
	 */
	@Override
	protected void hideTip() {
		if (currentTip != null) {
			currentTip.hide();
		}
	}

	/**
	 * Shows the menu. This method is called by its base class when the menu has
	 * to be shown.
	 */
	@Override
	protected void showMenu() {
		if (getMenuProvider() != null) {
			setCurrentMenu(getMenuProvider().getMenu());

			if (currentMenu != null) {
				hideTip();
				mapGXTController.showMenu(currentMenu, mapMenuController
						.getCurrentMousePosition());
				mapGXTController.setCurrentMenu(currentMenu);
			}
		}
	}

	/**
	 * Returns the visibility of the menu. This method is called by its base
	 * class, when it needs to know whether the menu is currently visible.
	 */
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

	/**
	 * Hides the menu. This method is called by its base class when the menu has
	 * to be hidden.
	 */
	@Override
	protected void hideMenu() {
		if ((currentMenu != null) && (currentMenu.isVisible())) {
			currentMenu.hide();
		}
	}
}
