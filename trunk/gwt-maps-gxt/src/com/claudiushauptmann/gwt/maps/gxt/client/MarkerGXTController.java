package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Timer;

public class MarkerGXTController extends MarkerMenuTipController {
	private OverlayTip overlayTip;
	private Menu menu;
	
	public MarkerGXTController(MapWidget mapWidget, Marker marker) {
		super(mapWidget, marker);
	}

	public OverlayTip getOverlayTip() {
		return overlayTip;
	}

	public void setOverlayTip(OverlayTip overlayTip) {
		this.overlayTip = overlayTip;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	protected void showOverlayTip() {
		overlayTip.showAt(currentMousePosition.getX() + 20, currentMousePosition.getY()+20);
	}
	
	@Override
	protected void updateOverlayTip() {
		overlayTip.setPosition(currentMousePosition.getX()+20, currentMousePosition.getY()+20);
	}
	
	@Override
	protected void hideOverlayTip() {
		overlayTip.hide();
	}

	@Override
	protected void showMenu() {
		MenuTimer.showMenu(menu, currentMousePosition);
	}

	@Override
	protected boolean isMenuVisible() {
		return menu.isVisible();
	}

	private static class MenuTimer extends Timer{
		private Menu menu;
		private Point point;
		
		MenuTimer(Menu menu, Point point) {
			this.menu = menu;
			this.point = point;
		}

		@Override
		public void run() {
			menu.showAt(point.getX(), point.getY());
		}
		
		public static void showMenu(Menu menu, Point point) {
			new MenuTimer(menu, point).schedule(50);
		}
	}
}
