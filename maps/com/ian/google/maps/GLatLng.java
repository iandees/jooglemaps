package com.ian.google.maps;

/**
 * GLatLng is a point in geographical coordinates longitude and latitude.
 * 
 * Notice that although usual map projections associate longitude with the
 * x-coordinate of the map, and latitude with the y-coordinate, the latitude
 * cooridnate is always written first, followed by the longitude, as it is
 * custom in cartography.
 * 
 * Notice also that you cannot modify the coordinates of a GLatLng. If you want
 * to compute another point, you have to create a new one.
 * 
 * 
 */
public class GLatLng {

	private double latitude;

	private double longitude;

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public GLatLng(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double lat() {
		return latitude;
	}

	public double lng() {
		return longitude;
	}

	public boolean equals(GLatLng other) {
		if (Double.compare(this.latitude, other.latitude) == 0
				&& Double.compare(this.longitude, other.longitude) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public double distanceFrom(GLatLng other) {
		return Math.sqrt(Math
				.sqrt((Math.pow(other.latitude - this.latitude, 2))
						+ Math.pow(other.longitude - this.longitude, 2)));
	}
	
	public GLatLng midpointTo(GLatLng other) {
		return new GLatLng((other.latitude+this.latitude)/2.0, (other.longitude+this.longitude)/2.0);
	}

	public String toString() {
		return "(" + this.latitude + ", " + this.longitude + ")";
	}

	public void transform(GLatLng delta) {
		this.latitude += delta.latitude;
		this.longitude += delta.longitude;
	}

}
