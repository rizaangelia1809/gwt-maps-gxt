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

/**
 * Extends the PolygonMenuTipController about gxt related logic that shows the
 * menu. Attach an instance of this class to your Polyline to use gxt menus and
 * tips.
 */
public class PolygonGXTController extends PolygonMenuTipController {

	/**
	 * The controller of the map the poylgon is attached to.
	 */
	private MapGXTController mapGXTController;

	/**
	 * The TipProvider
	 */
	private TipProvider tipProvider;

	/**
	 * The currently shown tip.
	 */
	private Tip currentTip;

	/**
	 * The standard MenuProvider.
	 */
	private MenuProvider standardMenuProvider;

	/**
	 * The MenuProvider for vertexes.
	 */
	private MenuProvider vertexMenuProvider;

	/**
	 * The currently shown menu.
	 */
	private Menu currentMenu;

	/**
	 * Creates a PolygonGXTController.
	 * 
	 * @param mapGXTController
	 *            The MapGXTController of the map the Polygon is attached to.
	 * @param marker
	 *            The Polygon to be observed.
	 */
	public PolygonGXTController(MapGXTController mapGXTController,
			Polygon polygon) {
		super(mapGXTController, polygon);

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
		return currentTip;
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
	 * Returns the standard menu provider.
	 * 
	 * @return The menu provider.
	 */
	public MenuProvider getStandardMenuProvider() {
		return standardMenuProvider;
	}

	/**
	 * Sets the standard menu provider.
	 * 
	 * @param standardMenuProvider
	 */
	public void setStandardMenuProvider(MenuProvider standardMenuProvider) {
		this.standardMenuProvider = standardMenuProvider;
	}

	/**
	 * Returns the menu provider for vertexes.
	 * 
	 * @return The menu provider for vertexes.
	 */
	public MenuProvider getVertexMenuProvider() {
		return vertexMenuProvider;
	}

	/**
	 * Sets the vertex menu provider.
	 * 
	 * @param menuProvider
	 *            The vertex menu provider.
	 */
	public void setVertexMenuProvider(MenuProvider vertexMenuProvider) {
		this.vertexMenuProvider = vertexMenuProvider;
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
		mapGXTController.setCurrentMenu(currentMenu);
	}

	/**
	 * Shows the current tip. This method does the real work without having to
	 * decide when to do it.
	 */
	@Override
	protected void showTip() {
		if (tipProvider != null) {
			setCurrentTip(getTipProvider().getTip());
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
	 * Shows the standard menu.
	 * 
	 * @param position
	 *            The position the menu has to be shown at.
	 */
	protected void showStandardMenu(Point position) {
		if (standardMenuProvider != null) {
			setCurrentMenu(getStandardMenuProvider().getMenu());
			hideTip();
			mapGXTController.showMenu(currentMenu, position);
			setCurrentMenu(currentMenu);
		}
	}

	/**
	 * Shows the vertex menu.
	 * 
	 * @param position
	 *            The position the menu has to be shown at.
	 */
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

	/**
	 * Shows the menu. This method is called by its base class when the menu has
	 * to be shown. It decides which menu has to be shown.
	 */
	@Override
	protected void showMenu() {
		int currentVertex = getCurrentVertex();

		if (currentVertex == -1) {
			showStandardMenu(mapMenuController.getCurrentMousePosition());
		} else {
			showVertexMenu(mapMenuController.getCurrentMousePosition());
		}
	}

	/**
	 * Returns the visibility of the menu. This method is called by its base
	 * class, when it needs to know whether the menu is currently visible.
	 */
	@Override
	protected boolean isMenuVisible() {
		return (currentMenu != null) && (currentMenu.isVisible());
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