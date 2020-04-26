/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.food;

import DatNT.utils.Tools;
import java.io.Serializable;
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
public class FoodDAO implements Serializable {

    private List<FoodDTO> listFood;

    public List<FoodDTO> getListFood() {
        return listFood;
    }

    private List<FoodDTO> listCategory;

    public List<FoodDTO> getListCategory() {
        return listCategory;
    }

    public void searchFood(int pageNum, int perPage, String searchValue, String Fcategory, float value1, float value2, String status)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int start = perPage * (pageNum - 1) + 1;
        int end = pageNum * perPage;

        try {
            con = Tools.makeConnection();
            String sql = "Select name, image, description, price, "
                    + "createDate, category, quantity, source, statusFood, updateDate "
                    + "From "
                    + "(Select Food.name, Food.image, Food.description, Food.price, "
                    + "Food.createDate, Food.category, Food.quantity, Food.source, Food.statusFood, Food.updateDate, "
                    + "ROW_NUMBER() OVER (ORDER BY Food.createDate desc) AS RowNum "
                    + "From Food "
                    + "Where Food.name like ? "
                    + "And Food.category like ? "
                    + "And Food.price BETWEEN ? And ? "
                    + "And Food.statusFood = ?) AS List "
                    + "Where RowNum >= ? and RowNum <= ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            stm.setString(2, "%" + Fcategory + "%");
            stm.setDouble(3, value1);
            stm.setDouble(4, value2);
            stm.setString(5, status);
            stm.setInt(6, start);
            stm.setInt(7, end);

            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String image = rs.getString("image");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                Timestamp date = rs.getTimestamp("createDate");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                String source = rs.getString("source");
                String statusFood = rs.getString("statusFood");
                Timestamp updateDate = rs.getTimestamp("updateDate");

                FoodDTO dto = new FoodDTO(name, image, description, price, date, category, quantity, source, statusFood, updateDate);
                if (this.listFood == null) {
                    this.listFood = new ArrayList<>();
                }
                this.listFood.add(dto);

            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

    }

    public int totalPage(int totalFoodPerPage, String searchValue, String Fcategory, float value1, float value2, String status)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float page = 0;

        try {
            con = Tools.makeConnection();
            String sql = "Select count (RowNum) "
                    + "From "
                    + "(Select Food.name, ROW_NUMBER() OVER (ORDER BY Food.createDate desc) AS RowNum "
                    + "From Food "
                    + "Where Food.name like ? "
                    + "And Food.category like ? "
                    + "And Food.price BETWEEN ? And ? "
                    + "And Food.statusFood = ?) AS list ";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            stm.setString(2, "%" + Fcategory + "%");
            stm.setDouble(3, value1);
            stm.setDouble(4, value2);
            stm.setString(5, status);

            rs = stm.executeQuery();
            if (rs.next()) {
                page = (float) rs.getInt(1);

                if (page > 0) {
                    return (int) Math.ceil(page / totalFoodPerPage);
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

        return 0;
    }

    public void getCategory() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = Tools.makeConnection();
            String sql = "Select Count(Food.name), Food.category "
                    + "From Food "
                    + "Group by Food.category ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int amount = rs.getInt(1);
                String category = rs.getString(2);
                System.out.println(category + " --category");
                Timestamp date = null;
                Timestamp update = null;
                FoodDTO dto = new FoodDTO("", "", "", 0, date, category, 0, "", "", update);
                if (this.listCategory == null) {
                    this.listCategory = new ArrayList<>();

                }
                this.listCategory.add(dto);

            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public int createNewFood(String name, String image, String description, double price, Timestamp createDate, String category, int quantity, String source, String statusFood)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = Tools.makeConnection();
            String sql = "Insert into Food(name, image, description, price, createDate, category, quantity, source, statusFood) "
                    + "Values(?,?,?,?,?,?,?,?,?) ";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, image);
            stm.setString(3, description);
            stm.setDouble(4, price);
            stm.setTimestamp(5, createDate);
            stm.setString(6, category);
            stm.setInt(7, quantity);
            stm.setString(8, source);
            stm.setString(9, statusFood);

            int row = stm.executeUpdate();
            if (row > 0) {
                return row;
            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }

        }
        return 0;
    }

    public FoodDTO viewFood(String pk) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = Tools.makeConnection();
            if (con != null) {
                String sql = "Select name, image, description, price, "
                        + "createDate, category, quantity, source, statusFood, updateDate "
                        + "From Food "
                        + "Where Food.name = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, pk);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(1);
                    String image = rs.getString(2);
                    String description = rs.getString(3);
                    float price = rs.getFloat(4);
                    Timestamp date = rs.getTimestamp(5);
                    String category = rs.getString(6);
                    int quantity = rs.getInt(7);
                    String source = rs.getString(8);
                    String statusFood = rs.getString(9);
                    Timestamp updateDate = rs.getTimestamp(10);
                    FoodDTO dto = new FoodDTO(name, image, description, price, date, category, quantity, source, statusFood, updateDate);
                    return dto;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }

    public int updateFood(String pk, String name, String image, String description, double price, String category, int quantity, String source, Timestamp updateDate)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();
            String sql = "Update Food "
                    + "Set name = ?, image = ?, description = ?, price = ?, category = ?, quantity = ?, source = ?, updateDate = ? "
                    + "Where name = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, image);
            stm.setString(3, description);
            stm.setDouble(4, price);
            stm.setString(5, category);
            stm.setInt(6, quantity);
            stm.setString(7, source);
            stm.setTimestamp(8, updateDate);
            stm.setString(9, pk);

            int row = stm.executeUpdate();
            System.out.println(row + " - row ");
            if (row > 0) {
                return row;
            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return 0;
    }

    public int changeStatus(String pk, String statusFood, Timestamp updateDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();
            String sql = "Update Food "
                    + "Set statusFood = ?, updateDate = ? "
                    + "Where name = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, statusFood);
            stm.setTimestamp(2, updateDate);
            stm.setString(3, pk);

            int row = stm.executeUpdate();
            System.out.println(row + " - row ");
            if (row > 0) {
                return row;
            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return 0;
    }

    public boolean checkExisted(String pk) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = Tools.makeConnection();
            if (con != null) {
                String sql = "select name "
                        + "from Food "
                        + "where name = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, pk);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }
    
    public boolean checkStatus(String foodID, String status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = Tools.makeConnection();
            if(con !=null){
                String sql = "Select name "
                        + "From Food "
                        + "Where name = ? "
                        + "And statusFood = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, foodID);
                stm.setString(2, status);
                
                rs = stm.executeQuery();
                
                if(rs.next()){
                    return true;
                }
            }
        } finally{
            if(rs != null){
                rs.close();
            }
            if(stm != null ){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
        return false;
    }
    
    public void updateFoodAfterBought(String itemID, int left) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();
            
            if(con != null){
                String sql ="Update Food set quantity = ? "
                            + "where name = ? ";
                
                stm = con.prepareStatement(sql);
                stm.setInt(1, left);
                stm.setString(2, itemID);
                
                int row = stm.executeUpdate();
            }
        } finally {
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
    
    
}
