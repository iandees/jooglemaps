package com.ian.google.maps;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.ian.google.maps.image.TileLayer;

public class MapMouseListener implements MouseListener, MouseMotionListener {

	private GoogleMapPresentation pres = null;
	
	private Point initialClick = null;

    private GLatLngBounds initialBounds;
	
	public MapMouseListener(GoogleMapPresentation presentation) {
		this.pres = presentation;
	}

	public void mouseClicked(MouseEvent e) {
		System.err.println("Click at: " + pres.getTileLayer().pixelToLatLng(e.getPoint()));
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mousePressed(MouseEvent e) {
		initialClick = new Point(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		initialClick = null;
	}

	public void mouseDragged(MouseEvent e) {
		int dX = (e.getX()-initialClick.x);
		int dY = (e.getY()-initialClick.y);
        pres.moveToPixels(e.getX(), e.getY());
		//Point newOrigin = new Point(originalOrigin.x + dX, originalOrigin.y + dY);
		//this.pres.setOrigin(newOrigin.x, newOrigin.y);
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
