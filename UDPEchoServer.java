package clientserver;

import java.net.*;
import java.io.*;

public class UDPEchoServer {
    public static void main(String args[]) {
        int port = 8000;


        DatagramSocket serverDatagramSocket = null;
        try {
            serverDatagramSocket = new DatagramSocket(port);
            System.out.println("Created UDP Echo Server on port number : " + port);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        try {
            byte buffer[] = new byte[1024];
            DatagramPacket dp = new
            DatagramPacket(buffer, buffer.length);
            String input;
            while (true) {


                serverDatagramSocket.receive(dp);
                input = new String(dp.getData(), 0,
                    dp.getLength());
                System.out.println("the receved input server: " + input);

                serverDatagramSocket.send(dp);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}