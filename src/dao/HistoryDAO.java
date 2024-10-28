//package dao;
//
//import model.History;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//public class HistoryDAO  {
//
//    public HistoryDAO() {
//        super();
//    }
//
//   public  void addHistoryToWinner(int UserID, String nameUser1, String nameUser2, String status){
//
//       try {
//           PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO history(UserID, nameUser1, nameUser2, status)\n"
//                   + "VALUES(?,?,?,?)");
//           preparedStatement.setInt(1, UserID);
//           preparedStatement.setString(2, nameUser1);
//           preparedStatement.setString(3, nameUser2);
//           preparedStatement.setString(4, status);
//           System.out.println(preparedStatement);
//           preparedStatement.executeUpdate();
//       } catch (SQLException ex) {
//           ex.printStackTrace();
//       }
//
//    }
//
//    public  void addHistoryToLoser(int UserID, String nameUser1, String nameUser2, String status){
//
//        try {
//            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO history(UserID, nameUser1, nameUser2, status)\n"
//                    + "VALUES(?,?,?,?)");
//            preparedStatement.setInt(1, UserID);
//            preparedStatement.setString(2, nameUser1);
//            preparedStatement.setString(3, nameUser2);
//            preparedStatement.setString(4, status);
//            System.out.println(preparedStatement);
//            preparedStatement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    public  void addHistoryToDrawer(int UserID, String nameUser1, String nameUser2, String status){
//
//        try {
//            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO history(UserID, nameUser1, nameUser2, status)\n"
//                    + "VALUES(?,?,?,?)");
//            preparedStatement.setInt(1, UserID);
//            preparedStatement.setString(2, nameUser1);
//            preparedStatement.setString(3, nameUser2);
//            preparedStatement.setString(4, status);
//            System.out.println(preparedStatement);
//            preparedStatement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public  List<History> getHistoryByUserID(int UserID) {
//        List<History> list = new ArrayList<>();
//        try {
//            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM history WHERE UserID = ?");
//            preparedStatement.setInt(1, UserID);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                list.add(new History(rs.getInt("ID"),
//                        rs.getInt("UserID"),
//                        rs.getString("nameUser1"),
//                        rs.getString("nameUser2"),
//                        rs.getString("status")));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return list;
//    }
//
//
//}
