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

import com.claudiushauptmann.gwt.maps.gxt.client.core.PolylineMenuTipController;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.Window;

/**
 * Extends the PolylineMenuTipController about gxt related logic that shows the
 * menu. Attach an instance of this class to your Polyline to use gxt menus and
 * tips.
 */
public class PolylineGXTController extends PolylineMenuTipController {

	/**
	 * The controller of the map the polyline is attached to.
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
	 * The MenuProvider for the start vertex.
	 */
	private MenuProvider startMenuProvider;

	/**
	 * The MenuProvider for the end vertex.
	 */
	private MenuProvider endMenuProvider;

	/**
	 * The currently shown menu.
	 */
	private Menu currentMenu;

	/**
	 * Creates a PolylineGXTController.
	 * 
	 * @param mapGXTController
	 *            The MapGXTController of the map the Polyline is attached to.
	 * @param polyline
	 *            The Polyline to be observed.
	 */
	public PolylineGXTController(MapGXTController mapGXTController,
			Polyline polyline) {
		super(mapGXTController, polyline);

		this.mapGXTController = mapGXTController;
	}

	/**
	 * Returns the current menu. 
	 * 
	 * @return The current menu.
	 */
	public Menu getCurrentMenu() {
		return currentMenu;
	}

	/**
	 * Sets the current menu.
	 * 
	 * @param currentMenu
	 *            The current menu.
	 */
	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
		mapGXTController.setCurrentMenu(currentMenu);
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
	 * @return The standard menu provider.
	 */
	public MenuProvider getStandardMenuProvider() {
		return standardMenuProvider;
	}

	/**
	 * Sets the standard menu provider.
	 * 
	 * @param menuProvider
	 *            The standard menu provider.
	 */
	public void setStandardMenuProvider(MenuProvider standardMenuProvider) {
		this.standardMenuProvider = standardMenuProvider;
	}

	/**
	 * Returns the vertex menu provider.
	 * 
	 * @return The vertex menu provider.
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
	 * Returns the start menu provider.
	 * 
	 * @return The start menu provider.
	 */
	public MenuProvider getStartMenuProvider() {
		return startMenuProvider;
	}

	/**
	 * Sets the start menu provider.
	 * 
	 * @param menuProvider
	 *            The start menu provider.
	 */
	public void setStartMenuProvider(MenuProvider startMenuProvider) {
		this.startMenuProvider = startMenuProvider;
	}

	/**
	 * Returns the end menu provider.
	 * 
	 * @return The end menu provider.
	 */
	public MenuProvider getEndMenuProvider() {
		return endMenuProvider;
	}

	/**
	 * Sets the end menu provider.
	 * 
	 * @param menuProvider
	 *            The end menu provider.
	 */
	public void setEndMenuProvider(MenuProvider endMenuProvider) {
		this.endMenuProvider = endMenuProvider;
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
			setCurrentMenu(currentMenu);
			mapGXTController.showMenu(currentMenu, position);
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
			setCurrentMenu(currentMenu);
			mapGXTController.showMenu(currentMenu, position);
		} else {
			showStandardMenu(position);
		}
	}

	/**
	 * Shows the start menu.
	 * 
	 * @param position
	 *            The position the menu has to be shown at.
	 */
	protected void showStartMenu(Point position) {
		if (startMenuProvider != null) {
			setCurrentMenu(getStartMenuProvider().getMenu());
			hideTip();
			setCurrentMenu(currentMenu);
			mapGXTController.showMenu(currentMenu, position);
		} else {
			showVertexMenu(position);
		}
	}

	/**
	 * Shows the end menu.
	 * 
	 * @param position
	 *            The position the menu has to be shown at.
	 */
	protected void showEndMenu(Point position) {
		if (endMenuProvider != null) {
			setCurrentMenu(getEndMenuProvider().getMenu());
			hideTip();
			setCurrentMenu(currentMenu);
			mapGXTController.showMenu(currentMenu, position);
		} else {
			showVertexMenu(position);
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
	 * Shows the menu. This method is called by its base class when the menu has
	 * to be shown. It decides which menu has to be shown.
	 */
	@Override
	protected void showMenu() {
		int currentVertex = getCurrentVertex();

		if (currentVertex == -1) {
			showStandardMenu(mapMenuController.getCurrentMousePosition());
		}
		if ((currentVertex > 0)
				&& (currentVertex < polyline.getVertexCount() - 1)) {
			showVertexMenu(mapMenuController.getCurrentMousePosition());
		}
		if (currentVertex == 0) {
			showStartMenu(mapMenuController.getCurrentMousePosition());
		}
		if (currentVertex == polyline.getVertexCount() - 1) {
			showEndMenu(mapMenuController.getCurrentMousePosition());
		}
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