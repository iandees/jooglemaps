package com.ian.google.maps.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.ian.google.maps.GLatLng;
import com.ian.google.maps.GLatLngBounds;
import com.ian.google.maps.GoogleMapPresentation;
import com.ian.google.maps.gui.TileImagePanel;

public class TileLayer extends JPanel {

	/** The base part of the URL that the x,y, and zoom will be tacked on to. */
	private String baseUrl;

	/** The parent map container. */
	private GoogleMapPresentation parentWindow;
    
    /** The cache of images for this tile layer. */
    private ImageCacheMap cache;
	
	/** The number of zoom levels for this tile layer. */
	public static final int NUM_ZOOM_LEVELS = 18;

	/** The width and height of one of this layer's tiles. */
	public static final int TILE_SIZE = 256;

	/**
	 * An array, indexed by zoom level, of the number of pixels per longitudal
	 * degree.
	 */
	public static double[] PIXELS_PER_LON_DEGREE = new double[NUM_ZOOM_LEVELS];

	/**
	 * An Array, indexed by zoom level, of the number of pixels per longitudal
	 * radian.
	 */
	public static double[] PIXELS_PER_LON_RADIAN = new double[NUM_ZOOM_LEVELS];

	/** An array, indexed by zoom level, of the number of tiles at a zoom level. */
	public static double[] NUM_TILES = new double[NUM_ZOOM_LEVELS];

	public static Point2D.Double[] BITMAP_ORIGIN = new Point2D.Double[NUM_ZOOM_LEVELS];

	private static double TWO_PI = 2.0 * Math.PI;

	private static double RADIAN_PI = Math.PI / 180.0;
    
    private static double qd(double a) {
        return a / (Math.PI/180.0);
    }

	/* Fill in the static variables. */
	static {

		int c = TILE_SIZE;
		for (int d = NUM_ZOOM_LEVELS - 1; d >= 0; --d) {
			PIXELS_PER_LON_DEGREE[d] = c / 360;
			PIXELS_PER_LON_RADIAN[d] = c / TWO_PI;
			double e = c / 2.0;
			BITMAP_ORIGIN[d] = new Point2D.Double(e, e);
			NUM_TILES[d] = c / TILE_SIZE;
			c *= 2.0;
		}

	}

	public static Point2D.Double getBitmapCoordinate(double latitude,
			double longitude, int zoomLevel) {
		Point2D.Double d = new Point2D.Double(0, 0);

		d.x = Math.floor(BITMAP_ORIGIN[zoomLevel].x + longitude
				* PIXELS_PER_LON_DEGREE[zoomLevel]);
		double e = Math.sin(latitude * RADIAN_PI);

		if (e > 0.9999) {
			e = 0.9999;
		}

		if (e < -0.9999) {
			e = -0.9999;
		}

		d.y = Math.floor(BITMAP_ORIGIN[zoomLevel].y + 0.5
				* Math.log((1 + e) / (1 - e)) * -1
				* (PIXELS_PER_LON_RADIAN[zoomLevel]));
		return d;
	}

	public static Point getTileCoordinate(double latitude,
			double longitude, int zoomLevel) {
		Point2D.Double d = getBitmapCoordinate(latitude, longitude, zoomLevel);
		d.x = Math.floor(d.x / TILE_SIZE);
		d.y = Math.floor(d.y / TILE_SIZE);

		return new Point((int) d.x, (int) d.y);
	}

	/**
	 * returns a Rectangle2D with x = lon, y = lat, width=lonSpan,
	 * height=latSpan for an x,y,zoom as used by google.
	 */
	public static Rectangle2D.Double getTileLatLong(int x, int y, int zoom) {
		double lon = -180; // x
		double lonWidth = 360; // width 360

		// double lat = -90; // y
		// double latHeight = 180; // height 180
		double lat = -1;
		double latHeight = 2;

		int tilesAtThisZoom = 1 << (17 - zoom);
		lonWidth = 360.0 / tilesAtThisZoom;
		lon = -180 + (x * lonWidth);
		latHeight = -2.0 / tilesAtThisZoom;
		lat = 1 + (y * latHeight);

		// convert lat and latHeight to degrees in a transverse mercator
		// projection note that in fact the coordinates go from about -85 to +85
		// not -90 to 90!
		latHeight += lat;
		latHeight = (2 * Math.atan(Math.exp(Math.PI * latHeight)))
				- (Math.PI / 2);
		latHeight *= (180 / Math.PI);

		lat = (2 * Math.atan(Math.exp(Math.PI * lat))) - (Math.PI / 2);
		lat *= (180 / Math.PI);

		latHeight -= lat;

		if (lonWidth < 0) {
			lon = lon + lonWidth;
			lonWidth = -lonWidth;
		}

		if (latHeight < 0) {
			lat = lat + latHeight;
			latHeight = -latHeight;
		}

		return new Rectangle2D.Double(lon, lat, lonWidth, latHeight);
	}

	public TileLayer(GoogleMapPresentation parent, String base) {
		this.setLayout(null);
		this.baseUrl = base;
		this.parentWindow = parent;
        this.cache = new ImageCacheMap(this.baseUrl);
	}

	public void setBounds(int x, int y, int width, int height) {
		
		super.setBounds(x, y, width, height);
	}

	public void paint(Graphics g) {
		// Paint the tiles that we can see
		GLatLngBounds parentBounds = this.parentWindow.getLatLngBounds();
		GLatLng sw = parentBounds.getSouthWest();
		GLatLng ne = parentBounds.getNorthEast();
		int zoom = 17-this.parentWindow.getZoom();
		
        // Get the numbers for tiles that are at each of our window's corners
		Point swTile = getTileCoordinate(sw.lat(), sw.lng(), zoom);
		Point neTile = getTileCoordinate(ne.lat(), ne.lng(), zoom);
		
		//System.err.println("Painting... sw: " + swTile + " to ne: " + neTile);
		
        // For each of the tiles in between the corner tiles...
		for(int x = swTile.x; x < neTile.x; x++) {
			for(int y = swTile.y; y > neTile.y; y--) {
                // Determine the tile's lat/long coordinate
				Rectangle2D.Double ll = getTileLatLong(x, y, zoom);
                // Determine where in the window that tile should go
                Point tileloc = this.latLngToPixel(new Point2D.Double(ll.x, ll.y));
                // Load the image from the cache
                //ImageIcon l = cache.get(tileloc, zoom);
                // Draw a rectangle over where the image is supposed to be
                g.drawRect(tileloc.x, tileloc.y, 256, 256);
				//System.err.println("Painting a tile: x: " + x + " y: " + y + " z: " + zoom + " @ " + tileloc);
			}
		}
		super.paintComponents(g);
	}
	
    /*
	public Point2D.Double pixelToLatLng(Point pixelPoint) {
		int zoom = 17-parentWindow.getZoom();
		GLatLngBounds parentBounds = this.parentWindow.getLatLngBounds();
		GLatLng sw = parentBounds.getNorthWest();
		
		double lat = sw.lat() + (pixelPoint.y / PIXELS_PER_LON_DEGREE[zoom]);
		double lng = sw.lng() + (pixelPoint.x / PIXELS_PER_LON_DEGREE[zoom]);
		
		return new Point2D.Double(lat, lng);
	}
    */
	
	public Point latLngToPixel(Point2D.Double mapPoint) {
		int zoom = 17-parentWindow.getZoom();
		GLatLngBounds parentBounds = this.parentWindow.getLatLngBounds();
		GLatLng nw = parentBounds.getNorthWest();
		
        int x = lngToX(mapPoint.x) - lngToX(nw.lng());
        int y = latToY(mapPoint.y) - latToY(nw.lat());
        
		//int x = (int) ((mapPoint.x-nw.lng()) * (PIXELS_PER_LON_DEGREE[zoom]));
		//int y = (int) ((mapPoint.y-nw.lat()) * (PIXELS_PER_LON_DEGREE[zoom]));
		
		return new Point(x, y);
	}
    
    public int lngToX(double longitudeDegrees) {
        int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
        int circumference = TILE_SIZE * tiles;
        double falseEasting=1*circumference/2;
        double radius = circumference/ (2.0 * Math.PI);
        double longitude = (longitudeDegrees * RADIAN_PI);
        int x=(int) (radius * longitude + falseEasting);
        return (x);
    }
    
    public double xToLng(int x) {
        int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
        int circumference = TILE_SIZE * tiles;
        double falseEasting=1*circumference/2;
        double radius = circumference/ (2.0 * Math.PI);
        double lng = ((x - falseEasting) / radius);
        return lng;
    }
    
    public int latToY(double latitudeDegrees) {
        int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
        int circumference = TILE_SIZE * tiles;
        double falseNorthing=-circumference/2;
        double radius = circumference/ (2.0 * Math.PI);
        double lat = latitudeDegrees * RADIAN_PI;
        int y = (int) -(radius/2.0 *
                Math.log( (1.0 + Math.sin(lat)) /
                     (1.0 - Math.sin(lat)) )+ falseNorthing);
        return y;
    }
    
    public double yToLat(int y) {
        int zoom = this.parentWindow.getZoom();
        Point2D.Double e=BITMAP_ORIGIN[zoom];
        //double f=(pixel.x-e.x)/PIXELS_PER_LON_DEGREE[zoom];
        double g=(y-e.y)/-PIXELS_PER_LON_RADIAN[zoom];
        double h=qd(2*Math.atan(Math.exp(g))-Math.PI/2);
        return h;
    }
    
    public GLatLng pixelToLatLng(Point pixel) {
        int zoom = this.parentWindow.getZoom();
        Point2D.Double e=BITMAP_ORIGIN[zoom];
        double f=(pixel.x-e.x)/PIXELS_PER_LON_DEGREE[zoom];
        double g=(pixel.y-e.y)/-PIXELS_PER_LON_RADIAN[zoom];
        double h=qd(2*Math.atan(Math.exp(g))-Math.PI/2);
        return new GLatLng(h,f);
    }
}
