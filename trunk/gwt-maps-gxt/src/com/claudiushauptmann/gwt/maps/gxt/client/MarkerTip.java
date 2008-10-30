package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
import com.google.gwt.maps.client.event.MarkerMouseOutHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Marker;

/*
 *  Marker and Polylines could have one common base class that handles
 *  updateContent and Properties for data to display
 */
public class MarkerTip extends Tip implements MarkerMouseOverHandler,
			MarkerMouseOutHandler, MarkerClickHandler, MarkerDragStartHandler {

	private Marker marker;
	//private SummitbookMap map;
	private MapWidget mapWidget;
	
	public MarkerTip(MapWidget mapWidget, Marker marker) {
		this.mapWidget = mapWidget;
		this.marker = marker;
		addMarkerListeners();
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		removeMarkerListeners();
		
		this.marker = marker;
		
		addMarkerListeners();
	}
	
	protected void addMarkerListeners() {
		if (this.marker != null) {
			marker.addMarkerMouseOverHandler(this);
			marker.addMarkerMouseOutHandler(this);
			marker.addMarkerClickHandler(this);
			marker.addMarkerDragStartHandler(this);
		}
	}
	
	protected void removeMarkerListeners() {
		marker.removeMarkerMouseOverHandler(this);
		marker.removeMarkerMouseOutHandler(this);
		marker.removeMarkerClickHandler(this);
		marker.removeMarkerDragStartHandler(this);
	}
	
	protected Point LatLng2Point(LatLng ll) {
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

	@Override
	protected void updateContent() {
	      String title = "Watzmann";
	      String text = "H&ouml;he: 2717m";
	      text += "<br/>Bergsteiger: 237";
	      text += "<br/>Berichte: 23";
	      getHeader().setText(title == null ? "" : title);
	      if (text != null) {
	        getBody().update(text);
	      }
	}

	public void onMouseOut(MarkerMouseOutEvent event) {
		hide();
	}

	public void onMouseOver(MarkerMouseOverEvent event) {
		Point p = LatLng2Point(marker.getLatLng());
		//Point p = mapWidget.convertLatLngToDivPixel(marker.getLatLng());
		showAt(p.getX()+20, p.getY()-20);
	}

	public void onClick(MarkerClickEvent event) {
		hide();
	}

	public void onDragStart(MarkerDragStartEvent event) {
		hide();
	}

}
