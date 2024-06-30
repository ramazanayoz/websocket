package com.example.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);

        //MeuServerSocket(111);


        String next;
        try {
            ServerSocket serverSocket = new ServerSocket(9991);
            do{
                Socket socket = serverSocket.accept();
                Scanner scanner = new Scanner(socket.getInputStream());
                String getOuput = scanner.nextLine();
                System.out.println(getOuput);
                next = getOuput.toString();
                socket.sendUrgentData(21);
                //socket.close();
                //serverSocket.close();
                socket.close();
            }while(next.equals("close"));
            //serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public static void MeuServerSocket(int port) {
         Socket socket = null;
         ServerSocket server = null;
         DataInputStream in = null;

        // starts server and waits for a connection
        try {
            while(true){
                server = new ServerSocket(port);
                System.out.println("Server started");

                System.out.println("Waiting for a client ...");

                socket = server.accept();
                System.out.println("Client accepted");
                ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
                saida.flush();
                // send available data from server to client
                saida.writeObject("Texto enviado 123...");

                // takes input from the client socket
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));

                String line = "";

                // reads message from client until "Over" is sent
                boolean fim = false;
                while (!line.equals("Over") && !fim)
                {
                    try
                    {
                        line = in.readUTF();
                        System.out.println(line);

                    }
                    catch(IOException i)
                    {
                        fim = true;
                        System.out.println(i.toString());
                    }
                }
                System.out.println("Closing connection");

                // close connection
                socket.close();
                saida.close();
                in.close();
            }
        } catch (IOException i) {
            System.out.println(i);
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

}
