package com.ian.google.maps;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.ian.google.maps.image.TileLayer;

public class GoogleMapPresentation extends JFrame {

	private TileLayer tileLayer = null;

	private MapMouseListener mouseListener = new MapMouseListener(this);

	private GLatLngBounds viewBounds = null;
	
	private GLatLng centerLatLng = null;
	
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
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resized();
			}
		});
		
		tileLayer = new TileLayer(this,
		"http://mt3.google.com/mt?n=404&v=w2.25&");
		
		Container c = this.getContentPane();
		c.add(tileLayer);
        
	}

	protected void resized() {
		if (this.centerLatLng != null) {
			this.setCenter(this.centerLatLng, this.zoomLevel);
		}
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
		this.repaint();
	}

	public void setCenter(GLatLng center, int zoom) {
		// Based on the center lat/lng given and the width and height of the
		// window, calculate the LatLngBounds
        this.zoomLevel = zoom;
        this.centerLatLng = center;
		int windowWidth = getWidth();
		int windowHeight = getHeight();
		System.err.println("Width: " + windowWidth + " Height: " + windowHeight);
        
        // This is the center of the viewport in pixels, related to the top left corner of the world
        int centerX = tileLayer.lngToX(center.lng());
        int centerY = tileLayer.latToY(center.lat());
        
        // From the center of the view, move down to the left corner
        int swX = centerX + (windowWidth / 2);//;
        int swY = centerY + (windowHeight / 2);//;
        
        // Get the lat/lng for this point
        double swLng = tileLayer.xToLng(swX);
        double swLat = tileLayer.yToLat(swY);
        GLatLng sw = new GLatLng(swLat, swLng);
        
        // From the center, now move north and to the east to get the right corner
        int neX = centerX - (windowWidth / 2);
        int neY = centerY - (windowHeight / 2);
        
        // Get the lat/lng for this point
        double neLng = tileLayer.xToLng(neX);
        double neLat = tileLayer.yToLat(neY);
        GLatLng ne = new GLatLng(neLat, neLng);
        
		this.setLatLngBounds(new GLatLngBounds(sw, ne));
	}

	public int getZoom() {
		return 17-this.zoomLevel;
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

        System.err.println("----------------");
        System.err.println("Northwest for bounds   : " + this.getLatLngBounds().getNorthWest());
        System.err.println("Northeast for bounds ->: " + this.getLatLngBounds().getNorthEast());
        System.err.println("Southeast for bounds   : " + this.getLatLngBounds().getSouthEast());
        System.err.println("Southwest for bounds ->: " + this.getLatLngBounds().getSouthWest());
        System.err.println("----------------");
        System.err.println("Pixel for -122.09795150214593, 37.48584849785407:");
        System.err.println(tileLayer.latLngToPixel(new Point2D.Double(-122.09795150214593, 37.48584849785407)));
        //System.err.println("LatLng for 0,0:");
        //System.err.println(tileLayer.pixelToLatLng(new Point(0,0)));
        //System.err.println("LatLng for 512,512:");
        //System.err.println(tileLayer.pixelToLatLng(new Point(512,512)));

        System.err.println("----------------");
    }

    public void moveToPixels(int x, int y) {
        // Determine how many degrees lat and lng the change represents
        //GLatLng l = tileLayer.pixelToLatLng(new Point(x,y));
        System.err.println("dx: " + x + " dy: " + y);
        
        // Move the view pane by that much
    }

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.tileLayer.paint(g);
	}
    
}
