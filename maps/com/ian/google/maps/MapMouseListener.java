package com.ian.google.maps;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.ian.google.maps.image.TileLayer;

public class MapMouseListener implements MouseListener, MouseMotionListener {

	private GoogleMapPresentation pres = null;
	
	private Point initialClick = null;

    private Point initialCenter = null;
	
	public MapMouseListener(GoogleMapPresentation presentation) {
		this.pres = presentation;
	}

	public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Point o = pres.getOrigin();
            o.translate(-e.getX(), -e.getY());
            System.err.println("Double click at: " + o.x
                    + " " + o.y);
            pres.setCenter(o);

        }
    }

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mousePressed(MouseEvent e) {
		initialClick = new Point(e.getX(), e.getY());
		initialCenter = this.pres.getCenter();
	}

	public void mouseReleased(MouseEvent e) {
		initialClick = null;
		initialCenter = null;
		System.err.println("Center: " + pres.getCenter());
	}

	public void mouseDragged(MouseEvent e) {
		int X = initialCenter.x + (e.getX()-initialClick.x);
		int Y = initialCenter.y + (e.getY()-initialClick.y);
        pres.moveToPixels(X, Y);
		//Point newOrigin = new Point(originalOrigin.x + dX, originalOrigin.y + dY);
		//this.pres.setOrigin(newOrigin.x, newOrigin.y);
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
