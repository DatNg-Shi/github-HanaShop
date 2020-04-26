/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.purchaseHistory;

import DatNT.utils.Tools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author nguye
 */
public class PurchaseHistoryDAO {

    private List<PurchaseHistoryDTO> list = null;

    public List<PurchaseHistoryDTO> getList() {
        return list;
    }

    public void GetPurchaseHistoryList(String userID, String searchVar, String minDate, String maxDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = Tools.makeConnection();
            if (con != null) {
                String sql = "SELECT foodID, image, name, price, quantity, date "
                        + "FROM purchaseHistory "
                        + "WHERE name like ? "
                        + "And userID = ? "
                        + "And purchaseHistory.date between ? and ? "
                        + "ORDER BY purchaseHistory.date desc ";

                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchVar + "%");
                stm.setString(2, userID);
                stm.setString(3, minDate);
                stm.setString(4, maxDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString(1);
                    String image = rs.getString(2);
                    String name = rs.getString(3);
                    float price = rs.getFloat(4);
                    int quantity = rs.getInt(5);
                    Timestamp date = rs.getTimestamp(6);
                    PurchaseHistoryDTO dto = new PurchaseHistoryDTO(userID, foodID, image, name, quantity, price, date);
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void storePurchaseHistory(String userID, String itemID, String image, String name, int quantity, float price, Timestamp time) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();

            if (con != null) {
                String sql = "Insert into purchaseHistory(userID, foodID, image, name, quantity, price, date) "
                        + "Values (?,?,?,?,?,?,?)";

                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, itemID);
                stm.setString(3, image);
                stm.setString(4, name);
                stm.setInt(5, quantity);
                stm.setFloat(6, price);
                stm.setTimestamp(7, time);
                int row = stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
