/**
 * 
 */
package com.mapki.netdraw.network;

import com.mapki.netdraw.gui.NetDrawable;

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
