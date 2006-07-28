package com.ian.google.maps;

import java.awt.Container;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.ian.google.maps.image.TileLayer;

public class GoogleMapPresentation extends JFrame {

	private TileLayer tileLayer = new TileLayer(this,
			"http://mt2.google.com/mt?n=404&v=w2.17&");

	private MapMouseListener mouseListener = new MapMouseListener(this);

	private Point origin = new Point(0, 0);

	private GLatLngBounds viewBounds = null;
	
	private int zoomLevel = 17;

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
		System.err.println(tileLayer.pixelToLatLng(new Point(0,0)));
		this.redrawMap();
	}
	
	public void transformBounds(GLatLng delta) {
		this.viewBounds.transform(delta);
		this.redrawMap();
	}

	public void redrawMap() {
		// TODO - only support for one tile layer right now
		this.tileLayer.repaint();
		System.err.println(this.viewBounds);
	}

	public void updateMap() {
		this.paintAll(this.getGraphics());
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

	public void setCenter(GLatLng center, int zoom) {
		// Based on the center lat/lng given and the width and height of the
		// window, calculate the LatLngBounds
		int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		double pixelsPerDegree = TileLayer.PIXELS_PER_LON_DEGREE[17-zoom];
		this.zoomLevel = zoom;
		
		double widthOfWindowDegree = (windowWidth / pixelsPerDegree) / 2;
		double heightOfWindowDegree = (windowHeight / pixelsPerDegree) / 2;
		
		System.err.println("height: " + heightOfWindowDegree + " width: " + widthOfWindowDegree);
		
		GLatLng sw = new GLatLng(center.lat() - widthOfWindowDegree, center.lng() - heightOfWindowDegree);
		GLatLng ne = new GLatLng(center.lat() + widthOfWindowDegree, center.lng() + heightOfWindowDegree);
		
		this.setLatLngBounds(new GLatLngBounds(sw, ne));
	}

	public int getZoom() {
		return this.zoomLevel;
	}
	
	public void setZoom(int zoom) {
		this.zoomLevel = zoom;
		// TODO - we have to update the map here
	}

	public TileLayer getTileLayer() {
		return this.tileLayer;
	}
}
