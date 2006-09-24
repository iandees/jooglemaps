/**
 * 
 */
package com.mapki.netdraw.network;

import java.awt.BasicStroke;
import java.awt.Color;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.mapki.netdraw.Drawer;


/**
 * @author Ian Dees
 *
 */
public class DrawConnector {
    public static final int STATUS_IDLE = 0;
    public static final int STATUS_CONNECTING = 1;
    public static final int STATUS_CONNECTED = 2;
    public static final int STATUS_LISTENING = 3;
    
    private DrawersPool pool;
    private ClientMaintainer clients;
    private int status;
    
    public DrawConnector() {
        this.pool = new DrawersPool();
        this.clients = new ClientMaintainer();
        this.status = STATUS_IDLE;
    }

    public void connectTo(String address) {
        try {
            InetAddress addrToConnect = InetAddress.getByName(address);
            this.clients.connectTo(addrToConnect);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startHosting() {
        this.clients.startListening();
    }

    public boolean isHosting() {
        if(this.clients.hasClients()) {
            return true;
        } else {
            return false;
        }
    }

    public void stopHosting() {
        // TODO Auto-generated method stub
        
    }

    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }

    public void closeConnections() {
        // TODO Auto-generated method stub
        
    }

    public void mouseAt(int x, int y) {
        
    }

    public boolean hasDrawers() {
        return this.pool.getDrawers().hasNext();
    }

    public Drawer getSelf() {
        return this.pool.getSelf();
    }

    public void setColor(Drawer self, Color c) {
        // TODO Hook to tell people that my color has been changed here
        this.pool.setColor(self, c);
    }

    public void setWeight(Drawer self, int weight) {
        this.pool.setStroke(self, weight);
    }
}
