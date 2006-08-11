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
	public static final int NUM_ZOOM_LEVELS = 18;

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

	public static Point2D.Double[] BITMAP_ORIGIN = new Point2D.Double[NUM_ZOOM_LEVELS+1];

	private static double TWO_PI = 2.0 * Math.PI;

	private static double RADIAN_PI = Math.PI / 180.0;

	/* Fill in the static variables. */
	static {

		int c = TILE_SIZE;
		for (int d = NUM_ZOOM_LEVELS; d >= 0; --d) {
			PIXELS_PER_LON_DEGREE[d] = c / 360.0;
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

		int tilesAtThisZoom = 1 << (17-zoom);
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
		Point swTile = getTileCoordinate(sw.lat(), sw.lng(), zoom);
		Point neTile = getTileCoordinate(ne.lat(), ne.lng(), zoom);
		
		System.err.println("Painting... sw: " + swTile + " to ne: " + neTile);
		
        // For each of the tiles in between the corner tiles...
		for(int x = swTile.x; x > neTile.x; x--) {
			for(int y = swTile.y; y > neTile.y; y--) {
                // Determine the tile's lat/long coordinate
				Rectangle2D.Double ll = getTileLatLong(x, y, zoom);
                // Determine where in the window that tile should go
				int swCornerX = lngToX(center.lng());
				int swCornerY = latToY(center.lat());
				int tileCornerX = lngToX(ll.x);
				int tileCornerY = latToY(ll.y);
                Point tileloc = new Point(swCornerX - tileCornerX, swCornerY - tileCornerY);
                //Point tileloc = this.latLngToPixel(new Point2D.Double(ll.x, ll.y));
                // Load the image from the cache
                //ImageIcon l = cache.get(tileloc, x, y, zoom);
                // Draw a rectangle over where the image is supposed to be
                g.drawRect(tileloc.x, tileloc.y, 256, 256);
                //g.drawImage(l.getImage(), tileloc.x, tileloc.y, this);
				System.err.println("Painting a tile: x: " + x + " y: " + y + " z: " + zoom + " @ " + tileloc);
			}
		}
		super.paintComponents(g);
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
        return (int) (radius * longitude) + falsenorthing;
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
        return (int) y + falseeasting;
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

class Tiles {

	// The Point (x,y) for this tile
	private Point p;
	// The coord (lat,lon) for this tile
	private Point2D.Double co;
	// Zoom level for this tile
	private int z;

	// ...Constants...
	private double PI = 3.1415926535;
	private int tileSize = 256;
	private double[] pixelsPerLonDegree = new double[19];
	private double[] pixelsPerLonRadian = new double[19];
	private double[] numTiles = new double[19];
	private Point2D.Double[] bitmapOrigo = new Point2D.Double[19];
	// Note: These variable names are based on the variables names found in the
	//       Google maps.*.js code.
	private static int c = 256;
	private double bc;
	private double Wa;

	// Fill in the constants array
	public void fillinconstants() {
		bc = 2*PI;
		Wa = PI/180;
	
		for(int d = 0; d <= 18; d++) {
  			pixelsPerLonDegree[d] = c/360;
  			pixelsPerLonRadian[d] = c/bc;
  			double e = c/2;
  			bitmapOrigo[d] = new Point2D.Double(e,e);
  			numTiles[d] = c/256;
  			c *= 2;
		}
	}

	public Tiles(double latitude, double longitude, int zoomLevel) {
		fillinconstants();
		z = zoomLevel;
		p = getTileCoordinate(latitude, longitude, zoomLevel);
		co = getLatLong(latitude, longitude, zoomLevel);
	}
	
	public Point getTileCoord() {
		return this.p;
	}

	public Point2D.Double getTileLatLong() {
		return this.co;
	}

	public char getKeyholeString() {
		char s;
		double myX = p.x;
		double myY = p.y;
		
		for(int i = 17; i > z; i--) {
			double rx = myX % 2;
			myX = myX/2;
			double ry = myY % 2;
			myY = (myY / 2);
			//s = this.getKeyholeDirection(rx, ry).$s;
			s = getKeyholeDirection(rx, ry);
			
			// pas clair
		}
		//return 't'.$s;
		return 't';
	}

	public char getKeyholeDirection(double x, double y) {
		if(x == 1) {
			if(y == 1) {
				return 's';
			} else if(y == 0) {
				return 'r';
			}
		} else if(x == 0) {
			if(y == 1) {
				return 't';
			} else if(y == 0) {
				return 'q';
			}
		}
		return ' ';
	}
		
	public Point getBitmapCoordinate(double a, double b, int c) {
  		Point d = new Point(0,0);

  		d.x =(int) (bitmapOrigo[c].x + b*pixelsPerLonDegree[c]);
  		double e = Math.sin(a*Wa);

  		if(e > 0.9999) {
    		e = 0.9999;
  		}

  		if(e < -0.9999) {
    		e = -0.9999;
  		}

  		d.y = (int) (bitmapOrigo[c].y + 0.5*Math.log((1+e)/(1-e))*-1*(pixelsPerLonRadian[c]));
  		return d;
	}

	public Point getTileCoordinate(double a, double b, int c) {
  		Point d = getBitmapCoordinate(a, b, c);
  		d.x = (d.x / tileSize);
  		d.y = (d.y / tileSize);

  		return d;
	}

	public Point2D.Double getLatLong(double a, double b, int c) {
		Point2D.Double d = new Point2D.Double(0, 0);
		Point e = getBitmapCoordinate(a, b, c);
		a = e.x;
		b = e.y;

		d.x = ((a - bitmapOrigo[c].x)/pixelsPerLonDegree[c]);
		double et = (b - bitmapOrigo[c].y) / (-1*pixelsPerLonRadian[c]);
		d.y = ((2 * Math.atan(Math.exp(et)) - PI/2)/Wa);
		return d;
	}
	

}
