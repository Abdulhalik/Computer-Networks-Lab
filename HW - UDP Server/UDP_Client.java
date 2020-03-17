/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computernetworkslecture.udpui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdulhalik
 */
public class UDP_Client {
    private DatagramSocket clientSocket;
    private javax.swing.JTextPane historyPane;
    private javax.swing.JLabel nameJLabel;
    private Thread clientThread;
    private final String host = "localhost";
    private final int port = 44444;
    private InetAddress adress;
    
    
    protected void start(int port, javax.swing.JTextPane jTextPaneHistory, javax.swing.JLabel jLabelName) throws  IOException {
        clientSocket = new DatagramSocket();
        adress = InetAddress.getByName(host);
        this.historyPane = jTextPaneHistory;
        this.nameJLabel = jLabelName;
        
        clientThread = new ListenThread();
        clientThread.start();
    } 
    
    protected void sendMessage(String message) throws IOException {
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, adress, port);
        clientSocket.send(packet);
    }
    
    protected void writeToHistory(Object message) {
        historyPane.setText(historyPane.getText() + "\n" + message);
    }
    
    protected void disconnect() throws IOException {
        if(clientSocket != null) { clientSocket.close(); }
        if (clientThread != null) { clientThread.interrupt(); }    
    }  
    
    class ListenThread extends Thread {
        // server'dan gelen mesajları dinle
        @Override
        public void run() {
        String message = "Newby on the line!";
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, adress, port);
            try {
                clientSocket.send(packet);
            } catch (IOException ex) {
                Logger.getLogger(UDP_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
                while (true) {
                    try {
                        byte[] resultData = new byte[1024];
                        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length);
                        clientSocket.receive(resultPacket);
                        String Text = new String(resultPacket.getData());
                        writeToHistory(Text);
                        resultData = null;
                        resultPacket = null;
                    } catch (Exception e) {

                    }
                }
        }
    }
}
