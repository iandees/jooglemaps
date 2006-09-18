package com.mapki.netdraw.network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DrawerSocket extends Thread {
    private Socket rawSocket;
    private PrintWriter output;
    private BufferedReader input;
    
    private HashMap<String, String> data;
    
    public static final Vector<String> ACCEPTABLE_PROTOCOL = new Vector<String>();
    static {
        ACCEPTABLE_PROTOCOL.add("NAME");
        ACCEPTABLE_PROTOCOL.add("COLO");
        ACCEPTABLE_PROTOCOL.add("WEIG");
        ACCEPTABLE_PROTOCOL.add("LINE");
        ACCEPTABLE_PROTOCOL.add("OVAL");
        ACCEPTABLE_PROTOCOL.add("RECT");
    }
    
    private boolean keepRunning = true;

    public DrawerSocket(Socket clientSocket) {
        this.rawSocket = clientSocket;
        try {
            this.output = new PrintWriter(new GZIPOutputStream(clientSocket.getOutputStream()));
            this.input = new BufferedReader(new InputStreamReader(new GZIPInputStream(clientSocket.getInputStream())));
            initConnection();
            this.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initConnection() {
        
    }

    public void run() {
        while(keepRunning) {
            if(rawSocket.isBound()) {
                try {
                    String line = this.input.readLine();
                    String[] split = line.split("\t");
                    if(ACCEPTABLE_PROTOCOL.contains(split[0])) {
                        data.put(split[0], split[1]);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Thread.yield();
            }
        }
    }

    public InetAddress getInetAddress() {
        return this.rawSocket.getInetAddress();
    }

    public String getUserName() {
        if(this.data.containsKey("NAME")) {
            return this.data.get("NAME");
        } else {
            return null;
        }
    }
    
    public void sendUserName(String name) {
        this.output.write("NAME" + "\t" + name + "\n");
    }

    public Color getColor() {
        if(this.data.containsKey("COLO")) {
            String val = this.data.get("COLO");
            String[] split = val.split("|");
            
            if(split.length == 3) {
                Color c = new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                return c;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void sendColor(Color color) {
        this.output.write("COLO" + "\t" +color.getRed()+"|"+color.getGreen()+"|"+color.getBlue()+"\n");
    }
}
