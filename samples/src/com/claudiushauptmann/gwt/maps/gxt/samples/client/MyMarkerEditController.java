package com.claudiushauptmann.gwt.maps.gxt.samples.client;

import com.claudiushauptmann.gwt.maps.gxt.client.MapGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.MarkerGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.MenuProvider;
import com.claudiushauptmann.gwt.maps.gxt.client.OverlayTip;
import com.claudiushauptmann.gwt.maps.gxt.client.TipProvider;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Timer;

public class MyMarkerEditController {
	private MapGXTController mapGxtController;
	private Marker marker;
	private MarkerGXTController markerGxtController;

	private MyTipProvider myTipProvider;
	private OverlayTip overlayTip;

	private MarkerMenuProvider markerMenuProvider;
	private Menu markerMenu;
	private MenuItem markerDeleteMenuItem;
	private MarkerDeleteMenuItemHandler markerDeleteMenuItemHandler;

	public MyMarkerEditController(MapGXTController mapGxtController,
			Marker marker) {
		this.marker = marker;
		this.mapGxtController = mapGxtController;

		markerGxtController = new MarkerGXTController(mapGxtController, marker);

		myTipProvider = new MyTipProvider();
		markerGxtController.setTipProvider(myTipProvider);

		markerMenuProvider = new MarkerMenuProvider();
		markerGxtController.setMenuProvider(markerMenuProvider);
	}

	private class MyTipProvider implements TipProvider {
		public Tip getTip() {
			if (overlayTip == null) {
				overlayTip = new OverlayTip();
				overlayTip.setTitle("Marienplatz");
				overlayTip
						.setDescription("loading...&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

				new Timer() {
					@Override
					public void run() {
						overlayTip = new OverlayTip();
						overlayTip.setTitle("Marienplatz");
						overlayTip
								.setDescription("Marienplatz is a central square in the"
										+ " city center of Munich, Germany since 1158.<br/>"
										+ " In the Middle Ages markets and tournaments were held in this"
										+ " city square. The Glockenspiel in the new city hall was inspired"
										+ " by these tournaments, and draws millions of tourists a year.");
						markerGxtController.refreshTip();
					}
				}.schedule(1500);
			}

			return overlayTip;
		}
	}

	private class MarkerMenuProvider implements MenuProvider {
		public Menu getMenu() {
			if (markerMenu == null) {
				markerMenu = new Menu();

				markerDeleteMenuItem = new MenuItem();
				markerDeleteMenuItem.setText("Delete Marker");
				markerDeleteMenuItemHandler = new MarkerDeleteMenuItemHandler();
				markerDeleteMenuItem
						.addSelectionListener(markerDeleteMenuItemHandler);
				markerMenu.add(markerDeleteMenuItem);
			}

			return markerMenu;
		}
	}

	private class MarkerDeleteMenuItemHandler extends
			SelectionListener<ComponentEvent> {

		@Override
		public void componentSelected(ComponentEvent ce) {
			mapGxtController.getMapWidget().removeOverlay(marker);
		}

	}
}
