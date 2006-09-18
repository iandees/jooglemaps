/**
 * 
 */
package com.mapki.netdraw.network;

/**
 * @author Ian Dees
 *
 */
public class DrawConnector {
    private DrawersPool pool;
    private ClientMaintainer clients;
    
    public DrawConnector() {
        this.pool = new DrawersPool();
        this.clients = new ClientMaintainer();
    }
}
