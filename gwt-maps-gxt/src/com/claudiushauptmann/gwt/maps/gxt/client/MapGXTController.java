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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class MapGXTController extends MapMenuController {
	private Menu currentMenu;
	
	
	public MapGXTController(MapWidget mapWidget) {
		super(mapWidget);
	}
	
	
	public Menu getCurrentMenu() {
		return currentMenu;
	}


	@Override
	public boolean isMenuVisible() {
		return (currentMenu != null) && (currentMenu.isVisible());
	}


	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
	}

	
	public static class MenuTimer extends Timer{
		private Menu menu;
		private Point point;
		
		MenuTimer(Menu menu, Point point) {
			this.menu = menu;
			this.point = point;
		}
		
		@Override
		public void run() {
			int x = point.getX();
			int y = point.getY();
	
			menu.showAt(x, y);
	
			int width = menu.getWidth();
			int height = menu.getHeight();
	
			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
				x = Window.getClientWidth() + Window.getScrollLeft() - width -10;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
				y = Window.getClientHeight() + Window.getScrollTop() - height - 10;
			}
			
			menu.setPosition(x, y);
		}
		
		public static void showMenu(Menu menu, Point point) {
			new MenuTimer(menu, point).schedule(50);
		}
	}

}
