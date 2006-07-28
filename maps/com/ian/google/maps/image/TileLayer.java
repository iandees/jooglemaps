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
	
	private Rectangle2D.Double bigArea = new Rectangle2D.Double(-256, -256, 1024, 1024);
	private Rectangle2D.Double leftSide = new Rectangle2D.Double(-256, -256, 256, 1024);
	
	/** The number of zoom levels for this tile layer. */
	public static final int NUM_ZOOM_LEVELS = 17;
	
	/** The width and height of one of this layer's tiles. */
	public static final int TILE_SIZE = 256;
	
	/** An array, indexed by zoom level, of the number of pixels per longitudal degree. */
	public static double[] PIXELS_PER_LON_DEGREE = new double[NUM_ZOOM_LEVELS];
	
	/** An Array, indexed by zoom level, of the number of pixels per longitudal radian. */
	public static double[] PIXELS_PER_LON_RADIAN = new double[NUM_ZOOM_LEVELS];
	
	/** An array, indexed by zoom level, of the number of tiles at a zoom level. */
	public static double[] NUM_TILES = new double[NUM_ZOOM_LEVELS];
	
	public static Point2D.Double[] BITMAP_ORIGIN = new Point2D.Double[NUM_ZOOM_LEVELS];
	
	private static double TWO_PI = 2.0 * Math.PI;
	
	private static double RADIAN_PI = Math.PI / 180.0;
	
	/* Fill in the static variables. */
	static {
		
		double c = TILE_SIZE;
		for(int d = NUM_ZOOM_LEVELS-1; d >= 0; --d) {
			PIXELS_PER_LON_DEGREE[d] = c / 360.0;
			PIXELS_PER_LON_RADIAN[d] = c / TWO_PI;
  			double e = c / 2.0;
  			BITMAP_ORIGIN[d] = new Point2D.Double(e,e);
  			NUM_TILES[d] = c / TILE_SIZE;
  			c *= 2.0;
		}
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
			for (int y = baseTileY; y < baseTileY + 6; y++) {
				for (int x = baseTileX; x < baseTileX + 6; x++) {
					URL u = new URL(baseUrl + "x=" + x + "&y=" + y + "&zoom=" + zoom);
					ImageIcon tile = new ImageIcon(u);
                    TileImagePanel tileLabel = new TileImagePanel(tile);
					tileLabel.setBounds(basePixX + (x - baseTileX) * 256,
							basePixY + (y - baseTileY) * 256, 256, 256);
                    System.err.println(tileLabel);
                    tileLabel.setBorder(new LineBorder(Color.black, 1));
					this.add(tileLabel);
					labels.add(tileLabel);
				}
			}
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
            //System.err.println(this.getTopLevelAncestor().getBounds());
            for (int i = 0; i < labels.size(); i++) {
                TileImagePanel panel = labels.get(i);
                if (this.getParent().getBounds().intersects(panel.getBounds()) == false) {
                    //System.err.println("GO AWAY TILES (" + i + ")!");
                    //this.remove(panel);
                    //panel.remove(panel);
                    //labels.remove(panel);
                }
            }
        }
        super.setBounds(x, y, width, height);
        System.err.println(this.getBounds());
    }

	public void setBounds(Rectangle r) {
		if(bigArea.contains(r)) {
			super.setBounds(r);
		}
	}

	public void moveTo(double d, double e) {
		
	}

	public void redrawInBounds(GLatLngBounds viewBounds) {
		GLatLng sw = viewBounds.getSouthWest();
		GLatLng ne = viewBounds.getNorthEast();
		
		for()
	}
}
