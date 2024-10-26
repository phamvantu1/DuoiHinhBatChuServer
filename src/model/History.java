package model;

public class History {
    private int ID;
    private int userID;
    private String nameUser1;
    private String nameUser2;
    private String status;

    public History(int ID, int userID, String nameUser1, String nameUser2, String status) {
        this.ID = ID;
        this.userID = userID;
        this.nameUser1 = nameUser1;
        this.nameUser2 = nameUser2;
        this.status = status;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getNameUser1() {
        return nameUser1;
    }
    public void setNameUser1(String nameUser1) {
        this.nameUser1 = nameUser1;
    }
    public String getNameUser2() {
        return nameUser2;
    }
    public void setNameUser2(String nameUser2) {
        this.nameUser2 = nameUser2;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String Status) {
        this.status = status;
    }


}
