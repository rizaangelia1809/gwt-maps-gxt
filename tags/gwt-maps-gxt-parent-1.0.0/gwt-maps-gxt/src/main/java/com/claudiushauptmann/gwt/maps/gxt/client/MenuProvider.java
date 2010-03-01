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

/**
 * MenuProviders are queried by Controllers for the current Menu. Thus, the menu
 * can be created in a lazy way and be replaced at further calls.
 */
public interface MenuProvider {

	/**
	 * This method will be called by the controller when the popup menu has to
	 * be shown.
	 * 
	 * @return The menu to be shown.
	 */
	public Menu getMenu();

}
