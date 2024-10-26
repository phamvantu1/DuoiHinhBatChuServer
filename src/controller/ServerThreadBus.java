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
//        broadcastOnlineUsers();
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
    public void broadcastOnlineUsers(int userId) {
        List<User> onlineUsers = getOnlineUsers();
        StringBuilder message = new StringBuilder("online-users,");

        message.append(userId).append("!");
        for (User user : onlineUsers) {
            message.append(user.getAvatar()).append(":").
                    append(user.getNickname()).append(":").
                    append(user.getUsername()).append(":").
                    append(user.getNumberOfGame()).append(":").
                    append(user.getNumberOfWin()).append(":").
                    append(user.getNumberOfDraw()).append(":").
                    append(user.getRank()).append(":").
                    append(user.getScore()).
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



private void checkWinner() {
    ServerThread winner = null;
    List<ServerThread> tiedPlayers = new ArrayList<>(); // Danh sách người chơi hòa
    int maxCorrectAnswers = -1;

    // Duyệt qua danh sách người chơi để tìm người thắng và tình trạng hòa
    for (ServerThread thread : listServerThreads) {
        int correctAnswers = thread.getCorrectAnswers();

        if (correctAnswers > maxCorrectAnswers) {
            // Cập nhật người thắng mới với điểm cao nhất
            maxCorrectAnswers = correctAnswers;
            winner = thread;

            // Xóa danh sách người hòa và thêm người thắng mới vào
            tiedPlayers.clear();
            tiedPlayers.add(winner);
        } else if (correctAnswers == maxCorrectAnswers) {
            // Thêm người chơi vào danh sách hòa nếu điểm bằng nhau
            tiedPlayers.add(thread);
        }
    }

    // Nếu có nhiều người chơi với số câu đúng bằng nhau, không có người thắng
    if (tiedPlayers.size() > 1) {
        winner = null; // Không có người thắng duy nhất
    }

    // Gọi notifyClients để gửi thông báo
    notifyClients(winner, tiedPlayers);
}

private void notifyClients(ServerThread winner, List<ServerThread> tiedPlayers) {
    for (ServerThread thread : listServerThreads) {
        try {
            if (tiedPlayers.contains(thread) && winner == null) {
                // Gửi thông báo hòa nếu không có người thắng duy nhất
                thread.write("user-tie," + thread.getClientNumber());
                System.out.println("gui thong bao hoa den client : " + thread.getClientNumber());
            } else if (winner != null && thread == winner) {
                // Gửi thông báo thắng nếu có người thắng duy nhất
                thread.write("user-winner," + winner.getClientNumber());
                System.out.println("gui thong bao thang den : " + winner.getClientNumber());
            } else {
                // Gửi thông báo thua cho những người còn lại
                thread.write("user-loser," + (winner != null ? winner.getClientNumber() : "N/A"));
                System.out.println("gui thong bao thua den : " + thread.getClientNumber());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi gửi thông báo đến client: " + thread.getClientNumber());
            e.printStackTrace();
        }
    }

    // Reset correctAnswers cho tất cả các client về -1 sau khi gửi thông báo
    for (ServerThread thread : listServerThreads) {
        thread.resetCorrectAnswers();
    }
}


}
