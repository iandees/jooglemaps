package com.mapki.netdraw.network;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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

    private boolean isAClient;

    private InetSocketAddress connectToSocket;
    
    public ClientMaintainer() {
        this.clients = new Vector<Drawer>();
        this.listening = false;
        this.isAClient = false;
        this.start();
    }
    
    public boolean hasClients() {
        return clients.size() > 0;
    }

    public void startListening() {
        try {
            sSocket = new ServerSocket(SERVER_PORT);
            System.err.println("Listening on " + SERVER_PORT);
            this.listening = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(keepRunning) {
            if(listening) {
                try {
                    System.err.println("Waiting for connection...");
                    Socket clientSocket = sSocket.accept();
                    System.err.println("Connected! From " + clientSocket);
                    DrawerSocket drawSocket = new DrawerSocket(clientSocket);
                    Drawer drawer = new Drawer(drawSocket);
                    this.clients.add(drawer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(isAClient) {
                try {
                    System.err.println("Connecting to " + connectToSocket);
                    Socket connectTo = new Socket();
                    connectTo.connect(this.connectToSocket);
                    System.err.println("Conencted to " + connectToSocket);
                    this.isAClient = false;
                } catch(Exception e) {
                    
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

    public void connectTo(InetAddress addrToConnect) {
        this.connectToSocket = new InetSocketAddress(addrToConnect, SERVER_PORT);
        this.isAClient = true;
    }

}
