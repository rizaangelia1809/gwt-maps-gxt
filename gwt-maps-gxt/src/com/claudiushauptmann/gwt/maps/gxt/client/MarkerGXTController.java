package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

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
		updateOverlayTip();
	}
	
	@Override
	protected void updateOverlayTip() {
		int x = currentMousePosition.getX() + 20;
		int y = currentMousePosition.getY() + 20;
		
		int width = overlayTip.getWidth();
		int height = overlayTip.getHeight();

		if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
			x = currentMousePosition.getX() - 20 - width;
		}
		if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
			y = currentMousePosition.getY() - 20 - height;
		}

		overlayTip.setPosition(x, y);
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

	@Override
	protected void hideMenu() {
		if ((menu != null) && (menu.isVisible())) {
			menu.hide();
		}
	}	

	public static class MenuTimer extends Timer{
		private Menu menu;
		private Point point;
		
		MenuTimer(Menu menu, Point point) {
			this.menu = menu;
			this.point = point;
		}
		
		@Override
		public void run() {
			int x = point.getX();
			int y = point.getY();

			menu.showAt(x, y);

			int width = menu.getWidth();
			int height = menu.getHeight();

			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
				x = Window.getClientWidth() + Window.getScrollLeft() - width -10;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
				y = Window.getClientHeight() + Window.getScrollTop() - height - 10;
			}
			
			menu.setPosition(x, y);
		}
		
		public static void showMenu(Menu menu, Point point) {
			new MenuTimer(menu, point).schedule(50);
		}
	}
}
