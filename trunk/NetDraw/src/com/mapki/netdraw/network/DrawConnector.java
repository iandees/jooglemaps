/**
 * 
 */
package com.mapki.netdraw.network;


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
        // TODO Auto-generated method stub
        
    }

    public boolean hasDrawers() {
        return this.pool.getDrawers().hasNext();
    }

}
