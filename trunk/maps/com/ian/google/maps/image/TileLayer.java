package com.ian.google.maps.image;

import java.awt.Color;
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
import com.ian.google.maps.gui.TileImagePanel;

public class TileLayer extends JPanel {

	private String baseUrl;

	private Vector<TileImagePanel> labels;

	private Rectangle2D.Double bigArea = new Rectangle2D.Double(-256, -256,
			1024, 1024);

	private Rectangle2D.Double leftSide = new Rectangle2D.Double(-256, -256,
			256, 1024);

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

		System.err.println("Lat lon for tile 647, 1584, zoom 5 = "
				+ getLatLong(647, 1584, 5));
		System.err.println("Tile x, y, z for -123.134 37.649 z=5 = "
				+ getTileCoordinate(37.649, -123.134, 5));
	}

	public static Point2D.Double getBitmapCoordinate(double latitude, double longitude,
			int zoomLevel) {
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

	public static Point2D.Double getTileCoordinate(double latitude, double longitude,
			int zoomLevel) {
		Point2D.Double d = getBitmapCoordinate(latitude, longitude, zoomLevel);
		d.x = Math.floor(d.x / TILE_SIZE);
		d.y = Math.floor(d.y / TILE_SIZE);

		return d;
	}
	
	/*
	public static Point2D.Double getLatLong(double latitude, double longitude, int zoomLevel) {
		Point2D.Double d = new Point2D.Double(0,0);
		Point2D.Double e = getBitmapCoordinate(latitude, longitude, zoomLevel);
  		latitude = e.x;
		longitude = e.y;

		d.x = (latitude - BITMAP_ORIGIN[zoomLevel].x) / PIXELS_PER_LON_DEGREE[zoomLevel];
		double f = (longitude - BITMAP_ORIGIN[zoomLevel].y) / (-1*PIXELS_PER_LON_RADIAN[zoomLevel]);
		d.y = (2 * Math.atan(Math.exp(f)) - Math.PI / 2) / TWO_PI;
		return d;
	}*/
	
	   /**
	   * returns a Rectangle2D with x = lon, y = lat, width=lonSpan, height=latSpan
	   * for an x,y,zoom as used by google.
	   */
	   public static Rectangle2D.Double getLatLong(int x, int y, int zoom) {
	      double lon      = -180; // x
	      double lonWidth = 360; // width 360

	      //double lat = -90;  // y
	      //double latHeight = 180; // height 180
	      double lat       = -1;
	      double latHeight = 2;

	      int tilesAtThisZoom = 1 << (17 - zoom);
	      lonWidth  = 360.0 / tilesAtThisZoom;
	      lon       = -180 + (x * lonWidth);
	      latHeight = -2.0 / tilesAtThisZoom;
	      lat       = 1 + (y * latHeight);

	      // convert lat and latHeight to degrees in a transverse mercator projection
	      // note that in fact the coordinates go from about -85 to +85 not -90 to 90!
	      latHeight += lat;
	      latHeight = (2 * Math.atan(Math.exp(Math.PI * latHeight))) - (Math.PI / 2);
	      latHeight *= (180 / Math.PI);

	      lat = (2 * Math.atan(Math.exp(Math.PI * lat))) - (Math.PI / 2);
	      lat *= (180 / Math.PI);

	      latHeight -= lat;

	      if (lonWidth < 0) {
	         lon      = lon + lonWidth;
	         lonWidth = -lonWidth;
	      }

	      if (latHeight < 0) {
	         lat       = lat + latHeight;
	         latHeight = -latHeight;
	      }

	      return new Rectangle2D.Double(lon, lat, lonWidth, latHeight);
	   }
	   
	   

	public TileLayer(String base) {
		try {
			labels = new Vector<TileImagePanel>();
			this.setLayout(null);
			this.baseUrl = base;

			int baseTileX = 1043;
			int basePixX = 0;
			int baseTileY = 1501;
			int basePixY = 0;
			int zoom = 5;

			/*
			 * for (int y = baseTileY; y < baseTileY + 6; y++) { for (int x =
			 * baseTileX; x < baseTileX + 6; x++) { URL u = new URL(baseUrl +
			 * "x=" + x + "&y=" + y + "&zoom=" + zoom); ImageIcon tile = new
			 * ImageIcon(u); TileImagePanel tileLabel = new
			 * TileImagePanel(tile); tileLabel.setBounds(basePixX + (x -
			 * baseTileX) * 256, basePixY + (y - baseTileY) * 256, 256, 256);
			 * System.err.println(tileLabel); tileLabel.setBorder(new
			 * LineBorder(Color.black, 1)); this.add(tileLabel);
			 * labels.add(tileLabel); } }
			 */
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		}
	}

	public void setBounds(int x, int y, int width, int height) {
		/*
		 * if(bigArea.contains(x,y,width,height)) {
		 * if(leftSide.intersects(x,y,width,height)) { System.err.println("Left
		 * side"); } } else { System.err.println("Out of bounds!"); }
		 */
		if (this.getTopLevelAncestor() != null) {
			// System.err.println(this.getTopLevelAncestor().getBounds());
			for (int i = 0; i < labels.size(); i++) {
				TileImagePanel panel = labels.get(i);
				if (this.getParent().getBounds().intersects(panel.getBounds()) == false) {
					// System.err.println("GO AWAY TILES (" + i + ")!");
					// this.remove(panel);
					// panel.remove(panel);
					// labels.remove(panel);
				}
			}
		}
		super.setBounds(x, y, width, height);
		System.err.println(this.getBounds());
	}

	public void setBounds(Rectangle r) {
		if (bigArea.contains(r)) {
			super.setBounds(r);
		}
	}

	public void redrawInBounds(GLatLngBounds viewBounds) {
		GLatLng sw = viewBounds.getSouthWest();
		GLatLng ne = viewBounds.getNorthEast();

	}
}
