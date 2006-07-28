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
		this.updateMap();
	}
	
	public void transformBounds(GLatLng delta) {
		this.viewBounds.transform(delta);
		this.redrawMap();
	}

	public void redrawMap() {
		// TODO - only support for one tile layer right now
		this.tileLayer.redrawInBounds(this.viewBounds);
		System.err.println(this.viewBounds);
	}

	public void updateMap() {
		this.repaint();
	}

	public Point getOrigin() {
		return this.origin;
	}

	public void setOrigin(int x, int y) {
		this.origin.x = x;
		this.origin.y = y;
		tileLayer.setBounds((int) (origin.x), (int) (origin.y), tileLayer
				.getWidth(), tileLayer.getHeight());

		//System.err.println(this.viewBounds);
	}

	public void setCenter(GLatLng center, int zoom) {
		// Based on the center lat/lng given and the width and height of the
		// window, calculate the LatLngBounds
		int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		double pixelsPerDegree = TileLayer.PIXELS_PER_LON_DEGREE[zoom];
		this.zoomLevel = zoom;
		
		GLatLng sw = new GLatLng(center.lat()-((windowWidth/2)*pixelsPerDegree), center.lng()-((windowHeight/2*pixelsPerDegree)));
		GLatLng ne = new GLatLng(center.lat()+((windowWidth/2)*pixelsPerDegree), center.lng()+((windowHeight/2*pixelsPerDegree)));
		
		this.setLatLngBounds(new GLatLngBounds(sw, ne));
	}

	public int getZoom() {
		return this.zoomLevel;
	}
	
	public void setZoom(int zoom) {
		this.zoomLevel = zoom;
		// TODO - we have to update the map here
	}
}
