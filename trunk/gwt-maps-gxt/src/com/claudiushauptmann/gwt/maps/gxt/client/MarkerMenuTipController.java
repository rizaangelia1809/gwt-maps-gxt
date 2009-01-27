package com.claudiushauptmann.gwt.maps.gxt.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
import com.google.gwt.maps.client.event.MarkerMouseOutHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.event.MarkerRemoveHandler;
import com.google.gwt.maps.client.overlay.Marker;

public abstract class MarkerMenuTipController extends OverlayMenuTipController {
	
	protected Marker marker;
	private MarkerEventHandler markerEventHandler;
	
	public MarkerMenuTipController(MapWidget mapWidget, Marker marker) {
		super(mapWidget);
		
		this.marker = marker;
		
		markerEventHandler = new MarkerEventHandler();
		marker.addMarkerMouseOverHandler(markerEventHandler);
		marker.addMarkerMouseOutHandler(markerEventHandler);
		marker.addMarkerClickHandler(markerEventHandler);
		marker.addMarkerRemoveHandler(markerEventHandler);
		marker.addMarkerDragStartHandler(markerEventHandler);
		marker.addMarkerDragEndHandler(markerEventHandler);
	}

	@Override
	protected void detach() {
		marker.removeMarkerClickHandler(markerEventHandler);
		marker.removeMarkerMouseOverHandler(markerEventHandler);
		marker.removeMarkerMouseOutHandler(markerEventHandler);
		marker.removeMarkerRemoveHandler(markerEventHandler);
		marker.removeMarkerDragStartHandler(markerEventHandler);
		marker.removeMarkerDragEndHandler(markerEventHandler);
	}

	private class MarkerEventHandler implements MarkerMouseOverHandler, MarkerMouseOutHandler,
				MarkerClickHandler, MarkerRemoveHandler, MarkerDragStartHandler,
				MarkerDragEndHandler {

		public void onMouseOver(MarkerMouseOverEvent event) {
			overlayMouseOver();
		}
		
		public void onMouseOut(MarkerMouseOutEvent event) {
			overlayMouseOut();
		}

		public void onClick(MarkerClickEvent event) {
			overlayClick();
		}

		public void onRemove(MarkerRemoveEvent event) {
			overlayRemove();
		}

		public void onDragStart(MarkerDragStartEvent event) {
			overlayStartUpdate();
		}
		
		public void onDragEnd(MarkerDragEndEvent event) {
			overlayEndUpdate();
		}
	}
}
