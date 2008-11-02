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
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;

public class OverlayTip extends Tip  {
	private String title;
	private String description;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

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

	
	/*
	 * Utility method since mapWidget.convertLatLngToDivPixel(latlng)
	 * seems not to work correctly at the moment
	 */
	protected Point LatLng2Point(MapWidget mapWidget, LatLng ll) {
		double n = mapWidget.getBounds().getNorthEast().getLatitude();
		double s = mapWidget.getBounds().getSouthWest().getLatitude();
		double e = mapWidget.getBounds().getNorthEast().getLongitude();
		double w = mapWidget.getBounds().getSouthWest().getLongitude();
		double lat = ll.getLatitude();
		double lng = ll.getLongitude();
		int height = mapWidget.getSize().getHeight();
		int width = mapWidget.getSize().getWidth();
		int offy = mapWidget.getAbsoluteTop();
		int offx = mapWidget.getAbsoluteLeft();
		
		long fx = Math.round((lng-w)/(e-w)*width);
		String sx = String.valueOf(fx);
		int x = Integer.valueOf(sx);
		x += offx;
	
		long fy = Math.round((lat-n)/(s-n)*height);
		String sy = String.valueOf(fy);
		int y = Integer.valueOf(sy);
		y += offy;
			
		return Point.newInstance(x, y);
	}
}
