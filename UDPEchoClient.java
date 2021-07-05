package clientserver;
import java.net.*;
import java.io.*;


public class UDPEchoClient {
    public static class UDPEchoReader extends Thread {
        public UDPEchoReader(DatagramSocket sc) {
            datagramSocket = sc;
            active = true;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            DatagramPacket receved = new DatagramPacket(buffer,
                buffer.length);
            String textreceved;
            while (active) {
                try {
                    datagramSocket.receive(receved);
                    textreceved = new String(receved.getData(),
                        0, receved.getLength());
                    System.out.println("Received from server:" + textreceved);
                } catch (IOException e) {
                    System.out.println(e);
                    active = false;
                }
            }
        }
        public boolean active;
        public DatagramSocket datagramSocket;
    }
    public static void main(String[] args) {
        InetAddress enwan = null;
        int port = 8000;
        DatagramSocket dataSocket = null;
        BufferedReader keyboardReader = null;

        try {
            enwan = InetAddress.getByName("127.0.0.1");
            dataSocket = new DatagramSocket();
            keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        UDPEchoReader reader = new UDPEchoReader(dataSocket);
        reader.setDaemon(true);
        reader.start();
        System.out.println("Ready to send your messages...");
        try {
            String input;
            while (true) {
                input = keyboardReader.readLine();
                DatagramPacket dataPacket = new DatagramPacket(input.getBytes(), input.length(), enwan, port);
                dataSocket.send(dataPacket);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}