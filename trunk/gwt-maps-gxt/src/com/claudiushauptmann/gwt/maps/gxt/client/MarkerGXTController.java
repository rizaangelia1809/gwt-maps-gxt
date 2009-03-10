package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Window;

public class MarkerGXTController extends MarkerMenuTipController {
	private OverlayTip overlayTip;
	private Menu menu;
	
	public MarkerGXTController(MapMenuController mapMenuController, Marker marker) {
		super(mapMenuController, marker);
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
		overlayTip.showAt(mapMenuController.getCurrentMousePosition().getX() + 20,
				mapMenuController.getCurrentMousePosition().getY()+20);
		updateOverlayTip();
	}
	
	@Override
	protected void updateOverlayTip() {
		int x = mapMenuController.getCurrentMousePosition().getX() + 20;
		int y = mapMenuController.getCurrentMousePosition().getY() + 20;
		
		int width = overlayTip.getWidth();
		int height = overlayTip.getHeight();

		if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
			x = mapMenuController.getCurrentMousePosition().getX() - 20 - width;
		}
		if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
			y = mapMenuController.getCurrentMousePosition().getY() - 20 - height;
		}

		overlayTip.setPosition(x, y);
	}
	
	@Override
	protected void hideOverlayTip() {
		overlayTip.hide();
	}

	@Override
	protected void showMenu() {
		MapGXTController.MenuTimer.showMenu(menu, mapMenuController.getCurrentMousePosition());
		mapMenuController.setCurrentMenu(menu);
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
}
