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


public abstract class PolyOverlayMenuTipController extends
		OverlayMenuTipController {

	private int lastClickedVertex;

	public PolyOverlayMenuTipController(MapMenuController mapMenuController) {
		super(mapMenuController);
		
		lastClickedVertex = -1;
	}

	public int getLastClickedVertex() {
		return lastClickedVertex;
	}

	protected void setLastClickedVertex(int lastClickedVertex) {
		this.lastClickedVertex = lastClickedVertex;
	}

	protected abstract int getCurrentVertex();

	@Override
	protected void overlayRightClick() {
		super.overlayRightClick();

		setLastClickedVertex(getCurrentVertex());		
	}

}