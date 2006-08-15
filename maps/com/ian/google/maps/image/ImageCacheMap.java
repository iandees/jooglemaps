package com.ian.google.maps.image;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.WeakHashMap;

import javax.swing.ImageIcon;

public class ImageCacheMap extends WeakHashMap<TileImage, ImageIcon> {

    private String baseURL = "";
    
    public ImageCacheMap(String baseUrl) {
        super();
        this.baseURL = baseUrl;
    }
    
    /*public Object put(ImageIcon image, Point tileloc, int zoom) {
        TileImage im = new TileImage(tileloc,zoom);
        
    }*/

    public ImageIcon get(Point tileloc, int x, int y, int zoom) {
        TileImage im = new TileImage(new Point(x,y), zoom);
        ImageIcon image = null;
        if((image = (ImageIcon) this.get(im)) != null) {
        	System.err.println("Already had image in cache.");
            return image;
        } else {
            try {
                URL u = new URL(baseURL + "x="+x+"&y="+y+"&zoom="+(zoom));
                System.err.print(u);
                ImageIcon i = new ImageIcon(u);
                System.err.println(" -- " + i.getImageLoadStatus());
                this.put(im, i);
                return i;
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL: ");
                e.printStackTrace();
                return null;
            }
        }
    }

}
