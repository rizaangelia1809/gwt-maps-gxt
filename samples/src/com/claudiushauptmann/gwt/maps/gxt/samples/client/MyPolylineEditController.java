package com.claudiushauptmann.gwt.maps.gxt.samples.client;

import com.claudiushauptmann.gwt.maps.gxt.client.DefaultOverlayTip;
import com.claudiushauptmann.gwt.maps.gxt.client.MapGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.MenuProvider;
import com.claudiushauptmann.gwt.maps.gxt.client.PolylineGXTController;
import com.claudiushauptmann.gwt.maps.gxt.client.TipProvider;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.maps.client.event.PolylineEndLineHandler;
import com.google.gwt.maps.client.event.PolylineMouseOutHandler;
import com.google.gwt.maps.client.event.PolylineMouseOverHandler;
import com.google.gwt.maps.client.event.PolylineRemoveHandler;
import com.google.gwt.maps.client.overlay.PolyEditingOptions;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.Timer;

public class MyPolylineEditController {
	private MapGXTController mapGxtController;
	private Polyline polyline;
	private PolylineGXTController polylineGXTController;

	private MyTipProvider myTipProvider;
	private DefaultOverlayTip overlayTip;
	private String tipTitle;
	private String tipText;

	private PolylineMenuProvider polylineMenuProvider;
	private Menu polylineMenu;
	private MenuItem polylineDeleteMenuItem;
	private MyDeletePolylineHandler polylineDeleteMenuItemHandler;

	private MidVertexMenuProvider midVertexMenuProvider;
	private Menu midVertexMenu;
	private MenuItem midVertexDeleteMenuItem;
	private VertexDeleteMenuItemHandler midVertexDeleteMenuItemHandler;

	private EndVertexMenuProvider endVertexMenuProvider;
	private Menu endVertexMenu;
	private MenuItem endVertexAddMenuItem;
	private EndVertexAddMenuItemHandler endVertexAddMenuItemHandler;
	private MenuItem endVertexDeleteMenuItem;
	private VertexDeleteMenuItemHandler endVertexDeleteMenuItemHandler;

	@SuppressWarnings("unused")
	private MyPolylineHandlers myPolylineHandlers;

	public MyPolylineEditController(MapGXTController mapGxtController,
			Polyline polyline, String tipTitle, String tipText) {
		this.polyline = polyline;
		this.mapGxtController = mapGxtController;
		this.tipTitle = tipTitle;
		this.tipText = tipText;

		polylineGXTController = new PolylineGXTController(mapGxtController,
				polyline);

		myTipProvider = new MyTipProvider();
		polylineGXTController.setTipProvider(myTipProvider);

		polylineMenuProvider = new PolylineMenuProvider();
		polylineGXTController.setStandardMenuProvider(polylineMenuProvider);

		midVertexMenuProvider = new MidVertexMenuProvider();
		polylineGXTController.setVertexMenuProvider(midVertexMenuProvider);

		endVertexMenuProvider = new EndVertexMenuProvider();
		polylineGXTController.setEndMenuProvider(endVertexMenuProvider);
		polylineGXTController.setStartMenuProvider(endVertexMenuProvider);

		myPolylineHandlers = new MyPolylineHandlers();
	}

	private class MyTipProvider implements TipProvider {
		public Tip getTip() {
			if (overlayTip == null) {
				overlayTip = new DefaultOverlayTip();
				overlayTip.setTitle(tipTitle);
				overlayTip.setDescription(tipText);
			}

			return overlayTip;
		}
	}

	private class PolylineMenuProvider implements MenuProvider {
		public Menu getMenu() {
			if (polylineMenu == null) {
				polylineMenu = new Menu();

				polylineDeleteMenuItem = new MenuItem();
				polylineDeleteMenuItem.setText("Delete Polyline");
				polylineDeleteMenuItemHandler = new MyDeletePolylineHandler();
				polylineDeleteMenuItem
						.addSelectionListener(polylineDeleteMenuItemHandler);
				polylineMenu.add(polylineDeleteMenuItem);
			}

			return polylineMenu;
		}
	}

	private class MidVertexMenuProvider implements MenuProvider {
		public Menu getMenu() {
			if (midVertexMenu == null) {
				midVertexMenu = new Menu();

				midVertexDeleteMenuItem = new MenuItem();
				midVertexDeleteMenuItem.setText("Delete Vertex");
				midVertexDeleteMenuItemHandler = new VertexDeleteMenuItemHandler();
				midVertexDeleteMenuItem
						.addSelectionListener(midVertexDeleteMenuItemHandler);
				midVertexMenu.add(midVertexDeleteMenuItem);
			}

			midVertexDeleteMenuItem.setEnabled(polyline.getVertexCount() > 2);

			return midVertexMenu;
		}
	}

	private class EndVertexMenuProvider implements MenuProvider {
		public Menu getMenu() {
			if (endVertexMenu == null) {
				endVertexMenu = new Menu();

				endVertexAddMenuItem = new MenuItem();
				endVertexAddMenuItem.setText("Add Vertex");
				endVertexAddMenuItemHandler = new EndVertexAddMenuItemHandler();
				endVertexAddMenuItem
						.addSelectionListener(endVertexAddMenuItemHandler);
				endVertexMenu.add(endVertexAddMenuItem);

				endVertexDeleteMenuItem = new MenuItem();
				endVertexDeleteMenuItem.setText("Delete Vertex");
				endVertexDeleteMenuItemHandler = new VertexDeleteMenuItemHandler();
				endVertexDeleteMenuItem
						.addSelectionListener(endVertexDeleteMenuItemHandler);
				endVertexMenu.add(endVertexDeleteMenuItem);
			}

			endVertexDeleteMenuItem.setEnabled(polyline.getVertexCount() > 2);

			return endVertexMenu;
		}
	}

	private class MyDeletePolylineHandler extends SelectionListener<MenuEvent> {

		@Override
		public void componentSelected(MenuEvent me) {
			mapGxtController.getMapWidget().removeOverlay(polyline);
		}

	}

	private class VertexDeleteMenuItemHandler extends
			SelectionListener<MenuEvent> {

		@Override
		public void componentSelected(MenuEvent me) {
			polyline.deleteVertex(polylineGXTController.getLastClickedVertex());
		}

	}

	private class EndVertexAddMenuItemHandler extends
			SelectionListener<MenuEvent> {

		@Override
		public void componentSelected(MenuEvent ce) {
			PolyEditingOptions peo = PolyEditingOptions
					.newInstance(polylineGXTController.getLastClickedVertex() == 0);
			polyline.setDrawingEnabled(peo);
		}

	}

	private class MyPolylineHandlers implements PolylineMouseOverHandler,
			PolylineMouseOutHandler, PolylineRemoveHandler,
			PolylineEndLineHandler {

		public MyPolylineHandlers() {
			polyline.addPolylineMouseOverHandler(this);
			polyline.addPolylineMouseOutHandler(this);
			polyline.addPolylineEndLineHandler(this);
			polyline.addPolylineRemoveHandler(this);
		}

		public void onMouseOver(PolylineMouseOverEvent event) {
			polyline.setEditingEnabled(true);
		}

		public void onMouseOut(PolylineMouseOutEvent event) {
			polyline.setEditingEnabled(false);
		}

		public void onEnd(PolylineEndLineEvent event) {
			Timer timer = new Timer() {
				@Override
				public void run() {
					polyline.setEditingEnabled(true);
				}
			};
			timer.schedule(50);
		}

		public void onRemove(PolylineRemoveEvent event) {
			polyline.removePolylineMouseOverHandler(this);
			polyline.removePolylineMouseOutHandler(this);
			polyline.removePolylineEndLineHandler(this);
			polyline.removePolylineRemoveHandler(this);
		}
	}
}
