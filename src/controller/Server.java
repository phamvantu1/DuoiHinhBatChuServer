/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.Admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Admin
 */
public class Server {

    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;
    public static int ROOM_ID;
    public static volatile Admin admin;

    public static void main(String[] args) {
        ServerSocket listener = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        ROOM_ID = 100;

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
        10,  // Số thread tối thiểu (core pool size)
        100, // Số thread tối đa
        10,  // Thời gian chờ trước khi hủy thread dư thừa
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(8),  // Hàng đợi các task đang chờ
        new ThreadPoolExecutor.CallerRunsPolicy() // Đảm bảo không bỏ sót task nếu quá tải
);

        admin = new Admin();
        try {
            while (true) {
                socketOfServer = listener.accept();
                System.out.println(socketOfServer.getInetAddress().getHostAddress());
                ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Số thread đang chạy là: " + serverThreadBus.getLength());
                executor.execute(serverThread);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
