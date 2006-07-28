package com.ian.google.maps;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.ian.google.maps.image.TileLayer;

public class MapMouseListener implements MouseListener, MouseMotionListener {

	private GoogleMapPresentation pres = null;
	
	private Point initialClick = null;

	private Point originalOrigin;
	
	public MapMouseListener(GoogleMapPresentation presentation) {
		this.pres = presentation;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mousePressed(MouseEvent e) {
		initialClick = new Point(e.getX(), e.getY());
		originalOrigin = new Point(pres.getOrigin());
	}

	public void mouseReleased(MouseEvent e) {
		initialClick = null;
	}

	public void mouseDragged(MouseEvent e) {
		int dX = (e.getX()-initialClick.x);
		int dY = (e.getY()-initialClick.y);
		double dLat = dX * TileLayer.PIXELS_PER_LON_DEGREE[pres.getZoom()];
		double dLng = dY * TileLayer.PIXELS_PER_LON_DEGREE[pres.getZoom()];
		pres.transformBounds(new GLatLng(dLat, dLng));
		//Point newOrigin = new Point(originalOrigin.x + dX, originalOrigin.y + dY);
		//this.pres.setOrigin(newOrigin.x, newOrigin.y);
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
