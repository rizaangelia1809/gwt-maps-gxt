package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.user.client.Timer;

/**
 * Utility class
 */
public abstract class Utility {
	
	/**
	 * Utility method since mapWidget.convertLatLngToDivPixel(latlng)
	 * seems not to work correctly at the moment
	 * 
	 * @param mapWidget the MapWidget.
	 * @param ll the position on the map.
	 * 
	 * @return the position on the window.
	 */
	public static Point LatLng2Point(MapWidget mapWidget, LatLng ll) {
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
