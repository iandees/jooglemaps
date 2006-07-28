package com.ian.google.maps;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GoogleMapPresentation presentation = new GoogleMapPresentation();
		presentation.setCenter(new GLatLng(37.4419, -122.1419), 13);
		presentation.setVisible(true);
	}

}
