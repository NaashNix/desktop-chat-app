package controllers;

import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket,String username){
        try{
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedWriter, bufferedReader);
        }


    }

    public void clientSendImage(byte[] imageArray,String format,String fileName){

        String imageString = Arrays.toString(imageArray);

        try {

            bufferedWriter.write(imageString);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket,bufferedWriter,bufferedReader);
        }

    }

    public void clientSendMessage(String message) {
        try {
            bufferedWriter.write(username + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void listenForMessage(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String messageFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        String message = bufferedReader.readLine();
                        ClientFormController.receiveMessage(message, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if (bufferedReader != null ){
                bufferedReader.close();
            }

            if (bufferedWriter != null ){
                bufferedWriter.close();
            }

            if (socket != null ){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
