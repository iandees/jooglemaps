package com.ian.google.maps;

public class GLatLngBounds {

	private GLatLng northEast;
	private GLatLng southWest;
	
	public GLatLngBounds(GLatLngBounds bounds) {
		this(bounds.northEast, bounds.southWest);
	}
	
	public GLatLngBounds(GLatLng northEast, GLatLng southWest) {
		this.northEast = northEast;
		this.southWest = southWest;
	}

	public GLatLng getNorthEast() {
		return this.northEast;
	}
	
	public GLatLng getSouthWest() {
		return this.southWest;
	}
    
    public GLatLng getNorthWest() {
        return new GLatLng(this.northEast.lat(), this.southWest.lng());
    }
    
    public GLatLng getSouthEast() {
        return new GLatLng(this.southWest.lat(), this.northEast.lng());
    }
	
	public GLatLng getCenter() {
		return northEast.midpointTo(southWest);
	}

	public GLatLngBounds transform(GLatLng delta) {
		this.northEast.transform(delta);
		this.southWest.transform(delta);
		return this;
	}
	
	public String toString() {
		return "(" + this.southWest + "," + this.northEast + ")";
	}
}
