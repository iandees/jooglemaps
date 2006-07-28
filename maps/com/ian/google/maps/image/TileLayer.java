package com.ian.google.maps.image;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.ian.google.maps.gui.TileImagePanel;

public class TileLayer extends JPanel {

	private String baseUrl;
	
	private Vector<TileImagePanel> labels;
	
	private Rectangle2D.Double bigArea = new Rectangle2D.Double(-256, -256, 1024, 1024);
	private Rectangle2D.Double leftSide = new Rectangle2D.Double(-256, -256, 256, 1024);

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
            System.err.println(this.getTopLevelAncestor().getBounds());
            for (int i = 0; i < labels.size(); i++) {
                TileImagePanel panel = labels.get(i);
                if (this.getParent().getBounds().intersects(panel.getBounds()) == false) {
                    System.err.println("GO AWAY TILES (" + i + ")!");
                    this.remove(panel);
                    panel.remove(panel);
                    labels.remove(panel);
                }
            }
        }
        super.setBounds(x, y, width, height);
    }

	public void setBounds(Rectangle r) {
		if(bigArea.contains(r)) {
			super.setBounds(r);
		}
	}

	public void moveTo(double d, double e) {
		
	}
}
