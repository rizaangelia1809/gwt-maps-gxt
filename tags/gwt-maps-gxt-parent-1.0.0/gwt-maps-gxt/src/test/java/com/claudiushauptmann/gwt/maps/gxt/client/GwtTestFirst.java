package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.maps.client.overlay.PolygonOptions;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.maps.client.overlay.PolylineOptions;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtTestFirst extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.claudiushauptmann.gwt.maps.gxt.GwtMapsGxtTest";
	}

	/**
	 * A very very basic test for gwt-maps-gxt.
	 */
	public void testGwtMapsGxt() {
		//Create a map
		MapWidget mapWidget = new MapWidget();
		RootPanel.get().add(mapWidget);

		//Create a menu for map and overlays
		Menu menu = new Menu();
		DefaultMenuProvider menuProvider = new DefaultMenuProvider(menu);
		
		//Create tip for overlays
		DefaultOverlayTip tip = new DefaultOverlayTip();
		DefaultTipProvider tipProvider = new DefaultTipProvider(tip);		
		
		//Create and attach MapGXTController
		MapGXTController mapGxtController = new MapGXTController(mapWidget);
		mapGxtController.setMenuProvider(menuProvider);

		//Create Marker
		Marker marker = new Marker(mapWidget.getCenter());
		mapWidget.addOverlay(marker);

		//Create and attach MarkerGXTController
		MarkerGXTController markerGxtController = new MarkerGXTController(mapGxtController, marker);
		markerGxtController.setMenuProvider(menuProvider);
		markerGxtController.setTipProvider(tipProvider);

		//Create Polyline
		LatLng[] llline = new LatLng[3];
		llline[0] = LatLng.newInstance(48.131955, 11.527061);
		llline[1] = LatLng.newInstance(48.11809, 11.579247);
		llline[2] = LatLng.newInstance(48.127143, 11.638298);
		PolylineOptions plo = PolylineOptions.newInstance(true, false);
		Polyline line = new Polyline(llline, "#FF0000", 2, 1.0, plo);
		mapWidget.addOverlay(line);

		//Create and attach PolylineGXTController
		PolylineGXTController polylineGxtController = new PolylineGXTController(mapGxtController, line);
		polylineGxtController.setTipProvider(tipProvider);
		polylineGxtController.setStandardMenuProvider(menuProvider);
		polylineGxtController.setVertexMenuProvider(menuProvider);
		polylineGxtController.setStartMenuProvider(menuProvider);
		polylineGxtController.setEndMenuProvider(menuProvider);
		polylineGxtController.setTipProvider(tipProvider);

		//CreatePolygon
		LatLng[] llpolygon = new LatLng[4];
		llpolygon[0] = LatLng.newInstance(48.119809, 11.539936);
		llpolygon[1] = LatLng.newInstance(48.158185, 11.541138);
		llpolygon[2] = LatLng.newInstance(48.155894, 11.569118);
		llpolygon[3] = llpolygon[0];
		PolygonOptions pgo = PolygonOptions.newInstance(true);
		Polygon polygon = new Polygon(llpolygon, "#0000FF", 2, 1.0, "#0000FF",
				0.3, pgo);
		mapWidget.addOverlay(polygon);

		//Create and attach PolygonGXTController
		PolygonGXTController polygonGxtController = new PolygonGXTController(mapGxtController, polygon);
		polygonGxtController.setTipProvider(tipProvider);
		polygonGxtController.setStandardMenuProvider(menuProvider);
		polygonGxtController.setVertexMenuProvider(menuProvider);
		polygonGxtController.setTipProvider(tipProvider);
	}

}
