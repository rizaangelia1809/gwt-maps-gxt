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

import com.claudiushauptmann.gwt.maps.gxt.client.MapGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.MenuProvider;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.maps.client.overlay.PolygonOptions;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.maps.client.overlay.PolylineOptions;
import com.google.gwt.user.client.ui.RootPanel;

public class OverlayEditor implements EntryPoint {
	private MapWidget mapWidget;
	private MapGXTController mapGxtController;

	private MapMenuProvider mapMenuProvider;
	private Menu mapMenu;
	private MenuItem addMarkerMenuItem;
	private AddMarkerMenuItemHandler addMarkerMenuItemHandler;
	private AddPolygonMenuItemHandler addPolygonMenuItemHandler;
	private MenuItem addPolygonMenuItem;
	private AddPolylineMenuItemHandler addPolylineMenuItemHandler;
	private MenuItem addPolylineMenuItem;

	public void onModuleLoad() {
		// Map
		mapWidget = new MapWidget();
		mapWidget.setCenter(LatLng.newInstance(48.136559, 11.576318), 13);
		mapWidget.setWidth("100%");
		mapWidget.setHeight("100%");
		mapWidget.addControl(new LargeMapControl());
		mapWidget.setContinuousZoom(true);
		mapWidget.setScrollWheelZoomEnabled(true);
		RootPanel.get().add(mapWidget);

		// MapController
		mapGxtController = new MapGXTController(mapWidget);
		mapMenuProvider = new MapMenuProvider();
		mapGxtController.setMenuProvider(mapMenuProvider);

		// Marker
		MarkerOptions mo = MarkerOptions.newInstance();
		mo.setClickable(true);
		mo.setDraggable(true);
		Marker marker = new Marker(mapWidget.getCenter(), mo);
		mapWidget.addOverlay(marker);
		new MyMarkerEditController(
				mapGxtController,
				marker,
				"Marienplatz",
				"Marienplatz is a central square in the"
						+ " city center of Munich, Germany since 1158.<br/>"
						+ " In the Middle Ages markets and tournaments were held in this"
						+ " city square. The Glockenspiel in the new city hall was inspired"
						+ " by these tournaments, and draws millions of tourists a year.");

		// Polyline
		LatLng[] llline = new LatLng[3];
		llline[0] = LatLng.newInstance(48.131955, 11.527061);
		llline[1] = LatLng.newInstance(48.11809, 11.579247);
		llline[2] = LatLng.newInstance(48.127143, 11.638298);
		PolylineOptions plo = PolylineOptions.newInstance(true, false);
		Polyline line = new Polyline(llline, "#FF0000", 2, 1.0, plo);
		mapWidget.addOverlay(line);
		new MyPolylineEditController(mapGxtController, line, "Polyline",
				"This is a polyline.");

		// Polygon
		LatLng[] llpolygon = new LatLng[4];
		llpolygon[0] = LatLng.newInstance(48.119809, 11.539936);
		llpolygon[1] = LatLng.newInstance(48.158185, 11.541138);
		llpolygon[2] = LatLng.newInstance(48.155894, 11.569118);
		llpolygon[3] = llpolygon[0];
		PolygonOptions pgo = PolygonOptions.newInstance(true);
		Polygon polygon = new Polygon(llpolygon, "#0000FF", 2, 1.0, "#0000FF",
				0.3, pgo);
		mapWidget.addOverlay(polygon);
		new MyPolygonEditController(mapGxtController, polygon, "Polygon",
				"This is a polygon.");
	}

	private class MapMenuProvider implements MenuProvider {
		public Menu getMenu() {
			if (mapMenu == null) {
				mapMenu = new Menu();

				addMarkerMenuItem = new MenuItem();
				addMarkerMenuItem.setText("add Marker");
				addMarkerMenuItemHandler = new AddMarkerMenuItemHandler();
				addMarkerMenuItem
						.addSelectionListener(addMarkerMenuItemHandler);
				mapMenu.add(addMarkerMenuItem);

				addPolygonMenuItem = new MenuItem();
				addPolygonMenuItem.setText("add Polygon");
				addPolygonMenuItemHandler = new AddPolygonMenuItemHandler();
				addPolygonMenuItem
						.addSelectionListener(addPolygonMenuItemHandler);
				mapMenu.add(addPolygonMenuItem);

				addPolylineMenuItem = new MenuItem();
				addPolylineMenuItem.setText("add Polyline");
				addPolylineMenuItemHandler = new AddPolylineMenuItemHandler();
				addPolylineMenuItem
						.addSelectionListener(addPolylineMenuItemHandler);
				mapMenu.add(addPolylineMenuItem);
			}

			return mapMenu;
		}
	}

	private class AddMarkerMenuItemHandler extends SelectionListener<MenuEvent> {

		@Override
		public void componentSelected(MenuEvent me) {
			MarkerOptions mo = MarkerOptions.newInstance();
			mo.setClickable(true);
			mo.setDraggable(true);
			Marker newMarker = new Marker(mapGxtController
					.getRightClickedLatLng(), mo);
			mapWidget.addOverlay(newMarker);

			new MyMarkerEditController(mapGxtController, newMarker, "Marker",
					"This is a new marker.");
		}
	}

	private class AddPolylineMenuItemHandler extends
			SelectionListener<MenuEvent> {

		@Override
		public void componentSelected(MenuEvent ce) {
			LatLng[] llline = new LatLng[1];
			llline[0] = mapGxtController.getRightClickedLatLng();
			PolylineOptions plo = PolylineOptions.newInstance(true, false);
			Polyline line = new Polyline(llline, "#FF0000", 2, 1.0, plo);
			mapWidget.addOverlay(line);
			line.setDrawingEnabled();

			new MyPolylineEditController(mapGxtController, line, "Polyline",
					"This is a new polyline.");
		}
	}

	private class AddPolygonMenuItemHandler extends
			SelectionListener<MenuEvent> {

		@Override
		public void componentSelected(MenuEvent me) {
			LatLng[] llpolygon = new LatLng[1];
			llpolygon[0] = mapGxtController.getRightClickedLatLng();
			PolygonOptions pgo = PolygonOptions.newInstance(true);
			Polygon polygon = new Polygon(llpolygon, "#0000FF", 2, 1.0,
					"#0000FF", 0.3, pgo);
			mapWidget.addOverlay(polygon);
			polygon.setDrawingEnabled();

			new MyPolygonEditController(mapGxtController, polygon, "Polygon",
					"This is a new polygon.");

		}
	}
}
