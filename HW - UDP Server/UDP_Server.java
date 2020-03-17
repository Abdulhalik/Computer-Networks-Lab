/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computernetworkslecture.udpui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdulhalik
 */
public class UDP_Server {
    
    private DatagramSocket socket;
    private javax.swing.JTextPane historyPane;
    private Thread serverThread;
    private HashSet<Integer> allClients = new HashSet<>();
    private InetAddress adress;
    
    protected void Start(int port, javax.swing.JTextPane JTexthistoryPane) throws IOException {
        
        socket = new DatagramSocket(port);
        System.out.println("Server Started On: " + port);
        adress = InetAddress.getByName("localhost");
        this.historyPane = JTexthistoryPane;
        
        serverThread = new ListenThread();
        serverThread.start();
    }
    
    protected void sendBroadcast(String message) throws IOException {
        byte[] data = message.getBytes();
        for(Integer port: allClients) {
            System.out.println(port);
            DatagramPacket packet = new DatagramPacket(data, data.length, adress, port);
            socket.send(packet);
        }
    }
    
    protected void writeToHistory(String message) {
        // server arayüzündeki history alanına mesajı yaz
        historyPane.setText(historyPane.getText() + "\n" + message);
    }
    
    protected void stop() throws IOException {
        if(socket != null) { socket.close(); }
        if(serverThread != null) { serverThread.interrupt(); };
    }
    
    class ListenThread extends Thread {
        @Override
        public void run() {
            writeToHistory("Server Created on Port: 44444");
            while (true) {
                try {
                    byte[] resultData = new byte[1024];
                    DatagramPacket resultPack = new DatagramPacket(resultData, resultData.length);
                    socket.receive(resultPack);
                    String Text = new String(resultPack.getData());
                    allClients.add(resultPack.getPort());
                    writeToHistory(resultPack.getPort() + ": " + Text);      
                    sendBroadcast(resultPack.getPort() + ": " +Text);
                    resultData = null;
                    resultPack = null;
                } catch (IOException ex) {
                    
                }
            }
        }
    }
    
}
