package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectServer implements Runnable {
    String message;
Socket socket;
TextArea textArea;
public ConnectServer(Socket socket,TextArea textArea)
{  this.textArea=textArea;
    this.socket=socket;
}
    public static String decrypt(String text, int s)
    {   s=26-s;
        StringBuffer result= new StringBuffer();

        for (int i=0; i<text.length(); i++)
        {
           if(Character.isAlphabetic(text.charAt(i)))
           {
               if (Character.isUpperCase(text.charAt(i)))
               {
                   char ch = (char)(((int)text.charAt(i) +
                           s - 65) % 26 + 65);
                   result.append(ch);
               }
               else
               {
                   char ch = (char)(((int)text.charAt(i) +
                           s - 97) % 26 + 97);
                   result.append(ch);
               }
           }
           else
           {
               result.append(text.charAt(i));
           }
        }
        return result.toString();
    }
    @Override
    public void run() {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while(socket != null){
            message=br.readLine();

                message=decrypt(message,2);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.appendText(message + "\n");

                    }
                });
                if(message.equals("exit"))
                {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.exit(-3);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }



