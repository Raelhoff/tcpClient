/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

/**
 *https://www.devzoneoriginal.com/2020/07/java-socket-example-for-sending-and.html
 * @author RaelH
 */
// A Java program for a Client 
import java.net.*;
import java.util.Arrays;
import java.io.*;

public class Client {
 // initialize socket and input output streams
 private Socket socket = null;
 private OutputStream out = null;
 private InputStream in = null;

 public static byte[] hexToBytes(char[] hex) {
    int length = hex.length / 2;
    byte[] raw = new byte[length];
    for (int i = 0; i < length; i++) {
      int high = Character.digit(hex[i * 2], 16);
      int low = Character.digit(hex[i * 2 + 1], 16);
      int value = (high << 4) | low;
      if (value > 127)
        value -= 256;
      raw[i] = (byte) value;
    }
    return raw;
  }

  public static byte[] hexToBytes(String hex) {
    return hexToBytes(hex.toCharArray());
  }
  
 // constructor to put ip address and port
 public Client(String address, int port) {
  // establish a connection
  try {
   socket = new Socket(address, port);
   if (socket.isConnected()) {
    System.out.println("Connected");
   }

   // sends output to the socket
   out = new DataOutputStream(socket.getOutputStream());
   //takes input from socket
   in = new DataInputStream(socket.getInputStream());
  } catch (UnknownHostException u) {
   System.out.println(u);
  } catch (IOException i) {
   System.out.println(i);
  }

  
  try {
   byte[] arr = {
        0x0A,(byte)0x00,(byte)0x00, 0x00, // INT INIT
       (byte)0x0E,(byte)0x00,(byte)0x00,(byte)0x00, // INT BYTES
       (byte)0xA1,(byte)0x00,(byte)0x00,(byte)0x00, // INT FRAME
       (byte)0x48, (byte)0x65, (byte)0x6C, (byte)0x6C, (byte)0x6F, (byte)0x20,
       (byte)0x57, (byte)0x6F, (byte)0x72, (byte)0x6C, (byte)0x64, //DATA
       (byte)0xDC,(byte)0x00,(byte)0x00,(byte)0x00, // INT CRC
       (byte)0x0D,(byte)0x00,(byte)0x00,(byte)0x00}; //INT END 

   // sending data to server
   out.write(arr);

   String req = Arrays.toString(arr);
   //printing request to console
   System.out.println("Sent to server : " + req);

   // Receiving reply from server
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   byte buffer[] = new byte[1024];
   baos.write(buffer, 0 , in.read(buffer));
   
   byte result[] = baos.toByteArray();

   String res = Arrays.toString(result);

   // printing reply to console
   System.out.println("Recieved from server : " + res);
  } catch (IOException i) {
   System.out.println(i);
  }
  // }

  // close the connection
  try {
   //input.close();
   in.close();
   //out.close();
  // socket.close();
  } catch (IOException i) {
   System.out.println(i);
  }
 }

 public static void main(String args[]) {
  new Client("127.0.0.1", 4447);
 }
}

