package com.lao.Web;

import java.net.*;
import java.io.*;


public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(33408);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("I know,Client.".getBytes());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Client ask " + bufferedReader.readLine());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

