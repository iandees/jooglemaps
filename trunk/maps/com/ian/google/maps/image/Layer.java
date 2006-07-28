package com.ian.google.maps.image;

public abstract class Layer {
	/** The numerical id for this layer. */
	private int id;
	
	/** The depth of this layer. */
	private int depth;

	public int getDepth() {
		return depth;
	}

	public int getId() {
		return id;
	}
	
	
}
