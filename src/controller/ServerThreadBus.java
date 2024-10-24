/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 * @author Admin
 */
public class ServerThreadBus {
    private final List<ServerThread> listServerThreads;

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public void add(ServerThread serverThread) {
        listServerThreads.add(serverThread);
    }

    public void boardCast(int id, String message) {
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            if (serverThread.getClientNumber() != id) {
                try {
                    serverThread.write(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public int getLength() {
        return listServerThreads.size();
    }

    public void sendMessageToUserID(int id, String message) {
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            if (serverThread.getUser().getID() == id) {
                try {
                    serverThread.write(message);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public ServerThread getServerThreadByUserID(int ID) {
        for (int i = 0; i < Server.serverThreadBus.getLength(); i++) {
            if (Server.serverThreadBus.getListServerThreads().get(i).getUser().getID() == ID) {
                return Server.serverThreadBus.listServerThreads.get(i);
            }
        }
        return null;
    }

    public void remove(int id) {
        for (int i = 0; i < Server.serverThreadBus.getLength(); i++) {
            if (Server.serverThreadBus.getListServerThreads().get(i).getClientNumber() == id) {
                Server.serverThreadBus.listServerThreads.remove(i);
                break;
            }
        }
         // Gửi lại danh sách người dùng online sau khi một người thoát
        broadcastOnlineUsers();
    }
        // Phương thức lấy danh sách người dùng đang online
    public List<User> getOnlineUsers() {
        List<User> onlineUsers = new ArrayList<>();
        for (ServerThread serverThread : listServerThreads) {
            if (serverThread.getUser() != null) {
                onlineUsers.add(serverThread.getUser());
            }
        }
        return onlineUsers;
    }

    // Gửi danh sách online đến tất cả các client
    public void broadcastOnlineUsers() {
        List<User> onlineUsers = getOnlineUsers();
        StringBuilder message = new StringBuilder("online-users,");

        for (User user : onlineUsers) {
            message.append(user.getAvatar()).append(":").
                    append(user.getNickname()).append(":").
                    append(user.getUsername()).append(":").
                    append(user.getNumberOfGame()).append(":").
                    append(user.getNumberOfWin()).append(":").
                    append(user.getNumberOfDraw()).append(":").
                    append(user.getRank()).
                    append(";");
           
        }

        for (ServerThread serverThread : listServerThreads) {
            try {
                serverThread.write(message.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void checkAllPlayersReady() {
        int countReady = 0;

        // Đếm số người chơi đã gửi đáp án
        for (ServerThread thread : listServerThreads) {
            if (thread.getCorrectAnswers() != -1) {
                System.out.println("thread.getCorrectAnswers():  " + thread.getCorrectAnswers() );
                countReady++;
            }
        }

        // Nếu tất cả người chơi đã gửi đáp án, kiểm tra người thắng
        if (countReady == listServerThreads.size()) {
            checkWinner();
            System.out.println("so nguoi choi " + listServerThreads.size() );
        }
    }


    // Method to check the winner based on correct answers
    public void checkWinner() {
        ServerThread winner = null;
        for (ServerThread thread : listServerThreads) {
            if (winner == null || thread.getCorrectAnswers() > winner.getCorrectAnswers()) {
                winner = thread;
                
            }
           
        }
        notifyClients(winner);
    }


        private void notifyClients(ServerThread winner) {
        for (ServerThread thread : listServerThreads) {
            try {
                if (thread == winner) {
                    thread.write("user-winer," + winner.getClientNumber()  );
                    System.out.println("gui thong bao thang den client : " + winner.getClientNumber());
                } else {
                    thread.write("user-loser," + winner.getClientNumber());
                    System.out.println("gui thong bao thua den client: " + thread.getClientNumber());
                }
            } catch (IOException e) {
                System.err.println("loi khi gui thong bao client: " + thread.getClientNumber());
                e.printStackTrace();
            }
        }
         for (ServerThread thread : listServerThreads) {
        thread.resetCorrectAnswers();
    }
        }

}
