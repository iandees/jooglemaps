package com.ian.google.maps;

import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.ian.google.maps.image.TileLayer;

public class GoogleMapPresentation extends JFrame {

	private TileLayer tileLayer = new TileLayer(this,
			"http://mt3.google.com/mt?n=404&v=w2.17&");

	private MapMouseListener mouseListener = new MapMouseListener(this);

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
		this.redrawMap();
	}
	
	public void transformBounds(GLatLng delta) {
		this.viewBounds.transform(delta);
		this.redrawMap();
	}

	public void redrawMap() {
		// TODO - only support for one tile layer right now
		this.tileLayer.repaint();
	}

	public void setCenter(GLatLng center, int zoom) {
		// Based on the center lat/lng given and the width and height of the
		// window, calculate the LatLngBounds
        this.zoomLevel = 17-zoom;
		int windowWidth = getWidth();
		int windowHeight = getHeight();
		double pixelsPerDegree = TileLayer.PIXELS_PER_LON_DEGREE[this.getZoom()];
		
		double widthOfWindowDegree = (pixelsPerDegree / (windowWidth / 2));
		double heightOfWindowDegree = (pixelsPerDegree / (windowHeight / 2));
		
		GLatLng sw = new GLatLng(center.lat() + (widthOfWindowDegree), center.lng() + (heightOfWindowDegree));
		GLatLng ne = new GLatLng(center.lat() - (widthOfWindowDegree), center.lng() - (heightOfWindowDegree));
		
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
    
    public void runatest() {

        System.err.println("Lat lon for tile 647, 1584, zoom 5 = "
                + TileLayer.getTileLatLong(647, 1584, 5));
        System.err.println("Tile x, y, z for -123.134 37.649 z=5 = "
                + TileLayer.getTileCoordinate(37.64903402157866, -123.134765625, 5));
        
        System.err.println("Northwest for bounds: " + this.getLatLngBounds().getNorthWest());
        System.err.println("Northeast for bounds: " + this.getLatLngBounds().getNorthEast());
        System.err.println("Southeast for bounds: " + this.getLatLngBounds().getSouthEast());
        System.err.println("Southwest for bounds: " + this.getLatLngBounds().getSouthWest());
        System.err.println("Pixel for -122.09795150214593, 37.48584849785407:");
        System.err.println(tileLayer.latLngToPixel(new Point2D.Double(-122.09795150214593, 37.48584849785407)));
        System.err.println("LatLng for 0,0:");
        System.err.println(tileLayer.pixelToLatLng(new Point(0,0)));
        System.err.println("LatLng for 512,512:");
        System.err.println(tileLayer.pixelToLatLng(new Point(512,512)));

        System.err.println("----------------");
    }

    public void moveToPixels(int x, int y) {
        // Determine how many degrees lat and lng the change represents
        GLatLng l = tileLayer.pixelToLatLng(new Point(x,y));
        System.err.println("dx: " + l.lng() + " dy: " + l.lat());
        
        // Move the view pane by that much
    }
}
