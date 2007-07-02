package com.ian.google.maps;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GoogleMapPresentation presentation = new GoogleMapPresentation();
		presentation.setCenter(new GLatLng(43.03677, -87.9895), 10);
        presentation.runatest();
		presentation.setVisible(true);
	}

}
