package com.mapki.netdraw.network;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import com.mapki.netdraw.Drawer;

public class ClientMaintainer extends Thread {

    private static final int SERVER_PORT = 8232;

    private Vector<Drawer> clients;
    
    private ServerSocket sSocket;
    
    private boolean listening;
    private boolean keepRunning = true;
    
    public ClientMaintainer() {
        this.clients = new Vector<Drawer>();
        this.listening = false;
        this.start();
    }
    
    public boolean hasClients() {
        return clients.size() > 0;
    }

    public void startListening() {
        try {
            sSocket = new ServerSocket(SERVER_PORT);
            this.listening = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(keepRunning) {
            if(listening) {
                try {
                    Socket clientSocket = sSocket.accept();
                    DrawerSocket drawSocket = new DrawerSocket(clientSocket);
                    Drawer drawer = new Drawer(drawSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void stopRunning() {
        // TODO: Make sure we're not still listening and that there are no clients
        // TODO: Check to make sure we're okay to quit
        this.keepRunning = false;
    }

}
