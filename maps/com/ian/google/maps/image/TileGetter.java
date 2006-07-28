package com.ian.google.maps.image;

import java.util.Queue;

public class TileGetter extends Thread {

	private Queue<TileImage> requestQueue;
	
	private boolean running = true;
	
	public TileGetter() {
		super();
	}
	
	public void addTile(TileImage tile) {
		this.requestQueue.add(tile);
	}

	public void run() {
		while(true) {
			if(running) {
				return;
			}
		}
	}
}
