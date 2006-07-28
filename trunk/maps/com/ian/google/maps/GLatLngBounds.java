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
	
	public GLatLng getCenter() {
		return northEast.midpointTo(southWest);
	}

	public void transform(GLatLng delta) {
		this.northEast.transform(delta);
		this.southWest.transform(delta);
	}
}
