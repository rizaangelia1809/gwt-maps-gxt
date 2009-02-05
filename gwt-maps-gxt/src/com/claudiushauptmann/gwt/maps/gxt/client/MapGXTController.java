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


public class MapGXTController extends MapMenuController {
	private Menu menu;

	public MapGXTController(MapWidget mapWidget) {
		super(mapWidget);
	}
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	protected void hideMenu() {
		menu.hide();
	}

	@Override
	protected boolean isMenuVisible() {
		return menu.isVisible();
	}

	@Override
	protected void showMenu() {
		GwtMapsGxt.MenuTimer.showMenu(menu, currentMousePosition);
		GwtMapsGxt.get().setCurrentMenu(menu);
	}
}
