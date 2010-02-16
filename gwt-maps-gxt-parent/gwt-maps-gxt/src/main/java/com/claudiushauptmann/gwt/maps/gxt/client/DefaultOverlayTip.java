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

import com.extjs.gxt.ui.client.widget.tips.Tip;

/**
 * Inherits {@link Tip} and stores title and description to display.
 */
public class DefaultOverlayTip extends Tip  {
	private String title;
	private String description;
	
	/**
	 * @return returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the current description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	protected void updateContent() {
	      getHeader().setText(title == null ? "" : title);
	      if (description != null) {
	        getBody().update(description);
	      }
	}	
}
