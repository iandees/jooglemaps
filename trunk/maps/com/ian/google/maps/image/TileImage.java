package com.ian.google.maps.image;

import java.awt.Point;

public class TileImage {

	private Point coordinates;
    private int zoom;
    
    public TileImage(Point coordinates, int zoom) {
        this.coordinates = coordinates;
        this.zoom = zoom;
    }
    
    public Point getCoordinates() {
        return coordinates;
    }
    
    public int getZoom() {
        return zoom;
    }
    
    public boolean equals(Object obj) {
        if(obj instanceof TileImage) {
            TileImage obja = (TileImage) obj;
            return this.coordinates.equals(obja.coordinates) && this.zoom == obja.zoom;
        } else {
            return false;
        }
    }
    
    public String toString() {
        return "Tile ("+this.coordinates+") @ zoom " + this.zoom;
    }

	public int hashCode() {
		System.err.println("coords: " + coordinates);
		return coordinates.x*coordinates.y*zoom;
	}
    
}
