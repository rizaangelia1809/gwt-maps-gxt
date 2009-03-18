package com.claudiushauptmann.gwt.maps.gxt.client;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Window;

public class MarkerGXTController extends MarkerMenuTipController {
	private Tip tip;
	private Menu menu;
	private MapGXTController mapGXTController;
	
	public MarkerGXTController(MapGXTController mapGXTController, Marker marker) {
		super(mapGXTController, marker);
		
		this.mapGXTController = mapGXTController;
	}

	public Tip getTip() {
		return tip;
	}

	public void setTip(Tip tip) {
		this.tip = tip;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	protected void showTip() {
		if (tip != null) {
			tip.showAt(mapMenuController.getCurrentMousePosition().getX() + 20,
					mapMenuController.getCurrentMousePosition().getY()+20);
			updateTip();
		}
	}
	
	@Override
	protected void updateTip() {
		if (tip != null) {
			int x = mapMenuController.getCurrentMousePosition().getX() + 20;
			int y = mapMenuController.getCurrentMousePosition().getY() + 20;
			
			int width = tip.getWidth();
			int height = tip.getHeight();
	
			if ((x + width) > Window.getClientWidth() + Window.getScrollLeft() - 10) {
				x = mapMenuController.getCurrentMousePosition().getX() - 20 - width;
			}
			if ((y + height) > Window.getClientHeight() + Window.getScrollTop() - 10) {
				y = mapMenuController.getCurrentMousePosition().getY() - 20 - height;
			}
	
			tip.setPosition(x, y);
		}
	}
	
	@Override
	protected void hideTip() {
		if (tip != null) {
			tip.hide();
		}
	}

	@Override
	protected void showMenu() {
		if (menu != null) {
			hideTip();
			MapGXTController.MenuTimer.showMenu(menu, mapMenuController.getCurrentMousePosition());
			mapGXTController.setCurrentMenu(menu);
		}
	}

	@Override
	protected boolean isMenuVisible() {
		boolean result;
		
		if (menu != null) {
			result = menu.isVisible();
		} else {
			result = false;
		}
		
		return result;
	}

	@Override
	protected void hideMenu() {
		if ((menu != null) && (menu.isVisible())) {
			menu.hide();
		}
	}	
}
