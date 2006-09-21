package com.mapki.netdraw.network;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import com.mapki.netdraw.Drawer;

public class DrawersPool {
    private HashMap<InetAddress, Drawer> pool;
    
    public DrawersPool() {
        this.pool = new HashMap<InetAddress, Drawer>();
        addSelf();
    }
    
    private void addSelf() {
        try {
            this.pool.put(InetAddress.getLocalHost(), new Drawer());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Iterator<Drawer> getDrawers() {
        return this.pool.values().iterator();
    }
    
    public void addDrawer(Drawer newDrawer) {
        this.pool.put(newDrawer.getAddress(), newDrawer);
    }

    public Drawer getSelf() {
        try {
            return this.pool.get(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void setColor(Drawer user, Color c) {
        ((Drawer)(this.pool.get(user.getAddress()))).setColor(c);
    }
}
