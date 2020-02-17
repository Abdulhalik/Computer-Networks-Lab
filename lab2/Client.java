/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computernetworkslecture.lab2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author abdulhalik
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 44444;
        
        Socket socket = new Socket(host, port);
        
        Scanner scan = new Scanner(socket.getInputStream());
        System.out.println("server response: " + scan.nextLine());
        
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("Hi, there!");
    }
}
