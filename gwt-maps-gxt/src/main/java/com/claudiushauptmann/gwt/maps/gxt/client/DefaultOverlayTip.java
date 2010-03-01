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
public class DefaultOverlayTip extends Tip {

	/**
	 * The title.
	 */
	private String title;

	/**
	 * The description.
	 */
	private String description;

	/**
	 * Returns the title.
	 * 
	 * @return The title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            The title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the description.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param The
	 *            description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Updates the content of the tip.
	 */
	@Override
	protected void updateContent() {
		getHeader().setText(title == null ? "" : title);
		if (description != null) {
			getBody().update(description);
		}
	}
}
