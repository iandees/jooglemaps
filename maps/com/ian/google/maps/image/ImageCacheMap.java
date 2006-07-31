package com.ian.google.maps.image;

import java.awt.Image;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class ImageCacheMap extends HashMap {

    private String baseURL = "";
    
    public ImageCacheMap(String baseUrl) {
        super();
        this.baseURL = baseUrl;
    }
    
    /*public Object put(ImageIcon image, Point tileloc, int zoom) {
        TileImage im = new TileImage(tileloc,zoom);
        
    }*/

    public ImageIcon get(Point tileloc, int x, int y, int zoom) {
        TileImage im = new TileImage(tileloc,zoom);
        if(this.containsKey(im)) {
            ImageIcon image = (ImageIcon) this.get(im);
            return image;
        } else {
            try {
                URL u = new URL(baseURL + "x="+x+"&y="+y+"&zoom="+(18-zoom));
                System.err.println(u);
                ImageIcon i = new ImageIcon(u);
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
