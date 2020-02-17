/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computernetworkslecture.lab2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdulhalik
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        start();
    }
    public static void start() throws IOException{
        ServerSocket socket = new ServerSocket(44444);
        
        while (true) {
            Socket clientSocket = socket.accept();
            System.out.println("Connected");
            
            new ClientThread(clientSocket).start();
        }
            
            //PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            //printWriter.println("Conncected : " + new Date().toString());
        
            //ObjectOutputStream stream = new ObjectOutputStream(clientSocket.getOutputStream());
            //stream.writeObject("bağlandi: " + new Date().toString());
    }
    
    static class ClientThread extends Thread {
        
        Socket socket;
        
        private ClientThread(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Scanner input = new Scanner(socket.getInputStream());
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    
                    while (input.hasNextLine()) {
                        output.println("received : " + input.nextLine());
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
}
