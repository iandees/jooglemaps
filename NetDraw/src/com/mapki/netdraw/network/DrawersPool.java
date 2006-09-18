package com.mapki.netdraw.network;

import java.util.Iterator;
import java.util.Vector;

import com.mapki.netdraw.Drawer;

public class DrawersPool {
    private Vector<Drawer> pool;
    
    public DrawersPool() {
        this.pool = new Vector<Drawer>(2);
    }
    
    public Iterator<Drawer> getDrawers() {
        return this.pool.iterator();
    }
    
    public void addDrawer(Drawer newDrawer) {
        this.pool.add(newDrawer);
    }
}
