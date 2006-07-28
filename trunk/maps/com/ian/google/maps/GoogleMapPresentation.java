package com.ian.google.maps;

import java.awt.Container;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.ian.google.maps.image.TileLayer;

public class GoogleMapPresentation extends JFrame {

	private TileLayer tileLayer = new TileLayer(
			"http://mt2.google.com/mt?n=404&v=w2.17&");

	private MapMouseListener mouseListener = new MapMouseListener(this);

	private Point origin = new Point(0, 0);

	private GLatLngBounds viewBounds = null;

	public GoogleMapPresentation() {
		super("Map");
		initGUI();
	}

	private void initGUI() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(512, 512);
		this.setLayout(null);

		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);

		Container c = this.getContentPane();
        tileLayer.setBounds(0,0,2048,2048);
		//tileLayer.setBounds(0, 0, 512, 512);
		c.add(tileLayer);
	}

	public GLatLngBounds getLatLngBounds() {
		return this.viewBounds;
	}

	public void setLatLngBounds(GLatLngBounds bounds) {
		this.viewBounds = bounds;
		this.updateMap();
	}

	public void updateMap() {
		// TODO Auto-generated method stub

	}

	public Point getOrigin() {
		return this.origin;
	}

	public void setOrigin(int x, int y) {
		this.origin.x = x;
		this.origin.y = y;
		tileLayer.setBounds((int) (origin.x), (int) (origin.y), tileLayer
				.getWidth(), tileLayer.getHeight());
	}

	public void setCenter(GLatLng latlng, int zoom) {
		// Based on the center lat/lng given and the width and height of the
		// window, calculate the LatLngBounds
		int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		
		
	}

	public Point fromLatLngToPixel(GLatLng latLng) {
		// TODO Auto-generated method stub
		return null;
	}

	public GLatLng fromPixelToLatLng(Point point) {
		return null;
	}
}
