package com.lao.Web;

import com.lao.UI.Login;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 33408);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("send login to login, send quit to quit");
            while (true) {
                String strWord = reader.readLine();
                if (strWord.equalsIgnoreCase("quit")) {
                    System.out.println("I send: " + strWord);
                    dataOutputStream.writeBytes(strWord + System.getProperty("line.separator"));
                    System.out.println("Sever send: " + bufferedReader.readLine());
                    break;
                } else if(strWord.equalsIgnoreCase("login")){
                    System.out.println("I send: " + strWord);
                    dataOutputStream.writeBytes(strWord + System.getProperty("line.separator"));

                    System.out.println("Sever send: " + bufferedReader.readLine());
                    new Login();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("local login");
            new Login();
        }
    }
}
