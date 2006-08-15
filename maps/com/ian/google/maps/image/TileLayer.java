package com.ian.google.maps.image;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ian.google.maps.GLatLng;
import com.ian.google.maps.GLatLngBounds;
import com.ian.google.maps.GoogleMapPresentation;

public class TileLayer extends JPanel {

	/** The base part of the URL that the x,y, and zoom will be tacked on to. */
	private String baseUrl;

	/** The parent map container. */
	private GoogleMapPresentation parentWindow;
    
    /** The cache of images for this tile layer. */
    private ImageCacheMap cache;
	
	/** The number of zoom levels for this tile layer. */
	public static final int NUM_ZOOM_LEVELS = 17;

	/** The width and height of one of this layer's tiles. */
	public static final int TILE_SIZE = 256;

	/**
	 * An array, indexed by zoom level, of the number of pixels per longitudal
	 * degree.
	 */
	public static double[] PIXELS_PER_LON_DEGREE = new double[NUM_ZOOM_LEVELS+1];

	/**
	 * An Array, indexed by zoom level, of the number of pixels per longitudal
	 * radian.
	 */
	public static double[] PIXELS_PER_LON_RADIAN = new double[NUM_ZOOM_LEVELS+1];

	/** An array, indexed by zoom level, of the number of tiles at a zoom level. */
	public static double[] NUM_TILES = new double[NUM_ZOOM_LEVELS+1];

	public static Point[] BITMAP_ORIGIN = new Point[NUM_ZOOM_LEVELS+1];

	private static double TWO_PI = 2.0 * Math.PI;

	private static double RADIAN_PI = Math.PI / 180.0;

	/* Fill in the static variables. */
	static {

		int c = TILE_SIZE;
		for (int d = NUM_ZOOM_LEVELS; d >= 0; --d) {
			PIXELS_PER_LON_DEGREE[d] = c / 360.0;
			PIXELS_PER_LON_RADIAN[d] = c / TWO_PI;
			int e = c / 2;
			BITMAP_ORIGIN[d] = new Point(e, e);
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
	public static Rectangle2D.Double getTileLatLong(int longitude, int latitude, int zoom) {
		double lon = -180; // x
		double lonWidth = 360; // width 360

		// double lat = -90; // y
		// double latHeight = 180; // height 180
		double lat = -1;
		double latHeight = 2;

		int tilesAtThisZoom = 1 << (NUM_ZOOM_LEVELS-zoom);
		lonWidth = 360.0 / tilesAtThisZoom;
		lon = -180 + (longitude * lonWidth);
		latHeight = -2.0 / tilesAtThisZoom;
		lat = 1 + (latitude * latHeight);

		// convert lat and latHeight to degrees in a transverse mercator
		// projection note that in fact the coordinates go from about -85 to +85
		// not -90 to 90!
		latHeight += lat;
		latHeight = (2.0 * Math.atan(Math.exp(Math.PI * latHeight)))
				- (Math.PI / 2.0);
		latHeight *= (180.0 / Math.PI);

		lat = (2.0 * Math.atan(Math.exp(Math.PI * lat))) - (Math.PI / 2.0);
		lat *= (180.0 / Math.PI);

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

	public void paint(Graphics g) {
		// Paint the tiles that we can see
		GLatLngBounds parentBounds = this.parentWindow.getLatLngBounds();
		GLatLng sw = parentBounds.getSouthWest();
		GLatLng ne = parentBounds.getNorthEast();
		GLatLng center = parentBounds.getCenter();
		int zoom = this.parentWindow.getZoom();
		
        // Get the numbers for tiles that are at each of our window's corners
		//Tiles swTiles = new Tiles(sw.lat(), sw.lng(), zoom);
		//Tiles neTiles = new Tiles(ne.lat(), ne.lng(), zoom);
		//Point swTile = swTiles.getTileCoord();
		//Point neTile = neTiles.getTileCoord();
		Point swTile = getTileCoordinate(sw.lat(), sw.lng(), 17-zoom);
		Point neTile = getTileCoordinate(ne.lat(), ne.lng(), 17-zoom);
		
		System.err.println("Painting... sw: " + swTile + " to ne: " + neTile);

        // Determine where in the window that tile should go
		int swCornerX = lngToX(center.lng());
		int swCornerY = latToY(center.lat());
        // For each of the tiles in between the corner tiles...
		for(int x = swTile.x-1; x < neTile.x+1; x++) {
			for(int y = swTile.y+1; y > neTile.y-1; y--) {
                // Determine the tile's lat/long coordinate
				Rectangle2D.Double ll = getTileLatLong(x, y, 17-zoom);
				int tileCornerX = lngToX(ll.x);
				int tileCornerY = latToY(ll.y);
                Point tileloc = new Point(swCornerX - tileCornerX, swCornerY - tileCornerY);
                // Load the image from the cache
                ImageIcon l = cache.get(tileloc, x, y, 17-zoom);
                // Draw a rectangle over where the image is supposed to be
                g.drawRect(tileloc.x, tileloc.y, 256, 256);
                g.drawImage(l.getImage(), tileloc.x, tileloc.y, this);
				System.err.println("Painting a tile: x: " + x + " y: " + y + " z: " + zoom + " @ " + tileloc);
			}
		}
		super.paint(g);
	}
	
	public Point latLngToPixel(Point2D.Double mapPoint) {
		GLatLngBounds parentBounds = this.parentWindow.getLatLngBounds();
		GLatLng sw = parentBounds.getSouthWest();
		
        int x = lngToX(mapPoint.x) - lngToX(sw.lng());
        int y = latToY(mapPoint.y) - latToY(sw.lat());

		return new Point(x, y);
	}
    
    public int lngToX(double longitudeDegrees) {
    	int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
		int circumference = TILE_SIZE * tiles;
		double radius = circumference / (2.0 * Math.PI);
    	double longitude = Math.toRadians(longitudeDegrees);
    	int falsenorthing = (circumference / 2);
        return (int) ((radius * longitude) + falsenorthing) + BITMAP_ORIGIN[17-this.parentWindow.getZoom()].x;
    }
    
    public double xToLng(int x) {
    	int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
		int circumference = TILE_SIZE * tiles;
		double radius = circumference / (2.0 * Math.PI);
        double longRadians = x/radius;
        double longDegrees = Math.toDegrees(longRadians);
        
        /* The user could have panned around the world a lot of times.
        Lat long goes from -180 to 180.  So every time a user gets 
        to 181 we want to subtract 360 degrees.  Every time a user
        gets to -181 we want to add 360 degrees. */
           
        double rotations = Math.floor((longDegrees + 180)/360);
        double longitude = longDegrees - (rotations * 360.0);
        return longitude;
        
    }
    
    public int latToY(double latitudeDegrees) {
    	int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
		int circumference = TILE_SIZE * tiles;
		double radius = circumference / (2.0 * Math.PI);
        double latitude = Math.toRadians(latitudeDegrees);
        double y = radius/2.0 * 
                Math.log( (1.0 + Math.sin(latitude)) /
                          (1.0 - Math.sin(latitude)) );
        int falseeasting = -1 * (circumference / 2);
        return (int) y + falseeasting + BITMAP_ORIGIN[17-this.parentWindow.getZoom()].y;
    }
    
    public double yToLat(int y) {
    	int tiles = (int) Math.pow(2, this.parentWindow.getZoom());
		int circumference = TILE_SIZE * tiles;
		double radius = circumference / (2.0 * Math.PI);
		double latitude = (Math.PI / 2)
				- (2 * Math.atan(Math.exp(-1.0 * y / radius)));
		return Math.toDegrees(latitude);
        
    }
}
