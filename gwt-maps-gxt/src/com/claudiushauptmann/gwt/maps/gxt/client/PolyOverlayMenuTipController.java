package com.claudiushauptmann.gwt.maps.gxt.client;

import com.google.gwt.maps.client.MapWidget;

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
	protected void overlayClick() {
		super.overlayClick();

		setLastClickedVertex(getCurrentVertex());		
	}

}