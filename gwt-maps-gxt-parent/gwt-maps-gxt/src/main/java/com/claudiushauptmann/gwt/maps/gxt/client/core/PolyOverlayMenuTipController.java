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
package com.claudiushauptmann.gwt.maps.gxt.client.core;

/**
 * Base class for 2D overlay controller. Calculates and stores the last clicked
 * Vertex.
 */
public abstract class PolyOverlayMenuTipController extends
		OverlayMenuTipController {

	/**
	 * The index of the vertex that was clicked.
	 */
	private int lastClickedVertex;

	/**
	 * Creates a PolyOverlayMenuTipController
	 * 
	 * @param mapMenuController
	 *            The MapMenuController of the map the overlay is attached to.
	 */
	public PolyOverlayMenuTipController(MapMenuController mapMenuController) {
		super(mapMenuController);

		lastClickedVertex = -1;
	}

	/**
	 * Returns the index of the vertex that was clicked.
	 * 
	 * @return The index of the vertex that was clicked.
	 */
	public int getLastClickedVertex() {
		return lastClickedVertex;
	}

	/**
	 * Sets the index of the vertex that was clicked.
	 * 
	 * @param lastClickedVertex
	 *            The index of the vertex that was clicked.
	 */
	protected void setLastClickedVertex(int lastClickedVertex) {
		this.lastClickedVertex = lastClickedVertex;
	}

	/**
	 * Has to return the current vertex.
	 * 
	 * @return The current vertex.
	 */
	protected abstract int getCurrentVertex();

	/**
	 * Is called when the overlay is right clicked and stores the current
	 * vertex.
	 */
	@Override
	protected void overlayRightClick() {
		super.overlayRightClick();

		setLastClickedVertex(getCurrentVertex());
	}

}