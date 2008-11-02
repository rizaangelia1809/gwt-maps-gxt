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
package com.claudiushauptmann.gwt.maps.gxt.samples.client;

import com.claudiushauptmann.gwt.maps.gxt.client.MarkerTip;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtMapsGxt_Sample implements EntryPoint {
	private MapWidget mapWidget;
	private Marker  marker;
	private MarkerTip markerTip;

	public void onModuleLoad() {
		mapWidget = new MapWidget();
		mapWidget.setCenter(LatLng.newInstance(48.136559, 11.576318), 13);
		mapWidget.setWidth("100%");
		mapWidget.setHeight("100%");
		mapWidget.addControl(new LargeMapControl());
		mapWidget.setContinuousZoom(true);
		mapWidget.setScrollWheelZoomEnabled(true);
		RootPanel.get().add(mapWidget);
		
		marker = new Marker(mapWidget.getCenter());
		mapWidget.addOverlay(marker);
		
		markerTip = new MarkerTip(mapWidget, marker);
		markerTip.setTitle("Marienplatz");
		markerTip.setDescription("Marienplatz is a central square in the"
				+ " city center of Munich, Germany since 1158.<br/>"
				+ " In the Middle Ages markets and tournaments were held in this"
				+ " city square. The Glockenspiel in the new city hall was inspired"
				+ " by these tournaments, and draws millions of tourists a year.");
	}
}
