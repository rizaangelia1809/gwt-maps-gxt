package com.claudiushauptmann.gwt.maps.gxt.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.Point;

public abstract class PolyOverlayMenuTipController extends
		OverlayMenuTipController {

	private int lastClickedVertex;

	public PolyOverlayMenuTipController(MapWidget mapWidget) {
		super(mapWidget);
		
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
	protected void click() {
		setLastClickedVertex(getCurrentVertex());
		
		super.click();
	}

}