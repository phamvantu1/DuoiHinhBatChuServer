/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import java.io.IOException;

/**
 * @author Admin
 */
public class Room {
    private final int id;
    private final ServerThread user1;
    private ServerThread user2;
    private String password;


    public Room(ServerThread user1) {
        System.out.println("Tạo phòng thành công, ID là: " + Server.ROOM_ID);
        this.password = " ";
        this.id = Server.ROOM_ID++;

        this.user1 = user1;
        this.user2 = null;
    }

    public int getId() {
        return id;
    }

    public ServerThread getUser2() {
        return user2;
    }

    public void setUser2(ServerThread user2) {
        this.user2 = user2;
    }

    public ServerThread getUser1() {
        return user1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfUser() {
        return user2 == null ? 1 : 2;
    }

    public void boardCast(String message) {
        try {
            user1.write(message);
            user2.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getCompetitorID(int ID_ClientNumber) {
        if (user1.getClientNumber() == ID_ClientNumber)
            return user2.getUser().getID();
        return user1.getUser().getID();
    }

    public ServerThread getCompetitor(int ID_ClientNumber) {
        if (user1.getClientNumber() == ID_ClientNumber)
            return user2;
        return user1;
    }
//
//    public void setUsersToPlaying() {
//        userDAO.updateToPlaying(user1.getUser().getID());
//        if (user2 != null) {
//            userDAO.updateToPlaying(user2.getUser().getID());
//        }
//    }
//
//    public void setUsersToNotPlaying() {
//        userDAO.updateToNotPlaying(user1.getUser().getID());
//        if (user2 != null) {
//            userDAO.updateToNotPlaying(user2.getUser().getID());
//        }
//    }
//
//
//    public void increaseNumberOfGame() {
//        userDAO.addGame(user1.getUser().getID());
//        userDAO.addGame(user2.getUser().getID());
//    }
//
//     public void DrawOfGame() {
//        userDAO.addScoreDraw(user1.getUser().getID());
//        userDAO.addScoreDraw(user2.getUser().getID());
//    }
//
//    public void HistoryDraw(int DrawID) {
//        if (DrawID == user1.getUser().getID()) {
//            historyDAO.addHistoryToDrawer(user1.getUser().getID(), user1.getUser().getUsername(), user2.getUser().getUsername(), "hoa");
//        } else {
//            historyDAO.addHistoryToDrawer(user2.getUser().getID(), user2.getUser().getUsername(), user1.getUser().getUsername(), "hoa");
//        }
//    }
//
//    public void HistoryWin(int winnerID) {
//        String name1 = "";
//        String name2 = "";
//        if (winnerID == user1.getUser().getID()) {
//            name1 = user1.getUser().getUsername();
//            name2 = user2.getUser().getUsername();
//        } else {
//            name1 = user2.getUser().getUsername();
//            name2 = user1.getUser().getUsername();
//        }
//        historyDAO.addHistoryToWinner(winnerID,name1, name2, "thang");
//
//    }
//
//    public void HistoryLose(int LoserID) {
//
//        String name1 = "";
//        String name2 = "";
//        if (LoserID == user1.getUser().getID()) {
//            name1 = user1.getUser().getUsername();
//            name2 = user2.getUser().getUsername();
//        } else {
//            name1 = user2.getUser().getUsername();
//            name2 = user1.getUser().getUsername();
//        }
//        historyDAO.addHistoryToLoser(LoserID,name1, name2, "thua");
//
//    }
//
//
//    public void increaseNumberOfDraw() {
//        userDAO.addDrawGame(user1.getUser().getID());
//        userDAO.addDrawGame(user2.getUser().getID());
//    }
//
//    public void decreaseNumberOfGame() {
//        userDAO.decreaseGame(user1.getUser().getID());
//        userDAO.decreaseGame(user2.getUser().getID());
//    }


}
