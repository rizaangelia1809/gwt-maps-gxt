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

import com.claudiushauptmann.gwt.maps.gxt.client.core.MapMenuController;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Extends the MapMenuController about gxt related logic that is doing the real
 * work without deciding when to do. Attach an instance of this class to your
 * map to use gxt menus and tips with your map or your overlays.
 */
public class MapGXTController extends MapMenuController {
	private Menu currentMenu;
	private MenuProvider menuProvider;

	/**
	 * Creates a MapGXTController.
	 * 
	 * @param mapWidget
	 *            The map to be observed.
	 */
	public MapGXTController(MapWidget mapWidget) {
		super(mapWidget);
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
	public Menu getCurrentMenu() {
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
	 * This public method shows a menu. It is called by the GXT controllers
	 * (map, marker, polygon and polyline) and shows a menu some time delayed,
	 * when all disturbing maps and gxt dom operations are finished.
	 * 
	 * @param menu
	 *            The menu that has to be shown.
	 * @param point
	 *            The position where the menu has to be shown.
	 */
	public void showMenu(Menu menu, Point point) {
		MenuTimer.showMenu(menu, point);
	}

	/**
	 * Shows the menu. This method is called by the base class when the menu has
	 * to be shown. Provides some logic whether the menu can actually be shown.
	 */
	@Override
	protected void showMenu() {
		if (getMenuProvider() != null) {
			Menu tempMenu = getMenuProvider().getMenu();

			if (tempMenu != null) {
				showMenu(tempMenu, getCurrentMousePosition());
				setCurrentMenu(tempMenu);
			}
		}
	}

	/**
	 * Returns the visibility of the menu. This method is called by its base
	 * class, when it needs to know whether the menu is currently visible.
	 */
	@Override
	public boolean isMenuVisible() {
		return (currentMenu != null) && (currentMenu.isVisible());
	}

	/**
	 * Hides the menu. This method is called by its base class when the menu has
	 * to be hidden.
	 */
	@Override
	public void hideMenu() {
		if (isMenuVisible()) {
			getCurrentMenu().hide();
		}
	}

	/**
	 * Helper class that shows the menu delayed.
	 */
	private static class MenuTimer extends Timer {
		private static final int delayTime = 50;
		private Menu menu;
		private Point point;

		/**
		 * Creates a MenuTimer.
		 * 
		 * @param menu
		 *            The menu to be shown delayed.
		 * @param point
		 *            The position where the menu has to be shown.
		 */
		MenuTimer(Menu menu, Point point) {
			this.menu = menu;
			this.point = point;
		}

		/**
		 * This method does all the work to show the menu at the right position.
		 */
		@Override
		public void run() {
			int x = point.getX();
			int y = point.getY();

			menu.showAt(x, y);

			int width = menu.getWidth();
			int height = menu.getHeight();

			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft()
					- 10) {
				x = Window.getClientWidth() + Window.getScrollLeft() - width
						- 10;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop()
					- 10) {
				y = Window.getClientHeight() + Window.getScrollTop() - height
						- 10;
			}

			menu.setPosition(x, y);
		}

		/**
		 * Creates a MenuTimer and shows the menu delayed.
		 * 
		 * @param menu
		 *            The menu to be shown delayed.
		 * @param point
		 *            The position where the menu has to be shown.
		 */
		static void showMenu(Menu menu, Point point) {
			new MenuTimer(menu, point).schedule(delayTime);
		}
	}

}
