/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.cart;

import DatNT.utils.Tools;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author nguye
 */
public class CartDAO implements Serializable{
    
    private List<CartDTO> cartList = null;

    public List<CartDTO> getCartList() {
        return cartList;
    }
    
    public void getCartList(String userID) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = Tools.makeConnection();
            if(con != null){
                String sql = "Select foodName, quantity "
                            + "From cart "
                            + "Where userID = ? ";
                
               stm = con.prepareStatement(sql);
               stm.setString(1, userID);
               rs = stm.executeQuery();
               while(rs.next()){
                   String foodID = rs.getString(1);
                   int quantity = rs.getInt(2);
                   if(this.cartList == null){
                       this.cartList = new ArrayList<>();
                   }
                   
                   CartDTO dto = new CartDTO(userID, foodID, quantity);
                   this.cartList.add(dto);
               }
            }   
        } finally {
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
    }    
    
    public int addToCart(String userID, String foodID, int quantity) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();
            
            if(con != null){
                String sql = "Insert into cart(userID, foodName, quantity) "
                        + "Values (?,?,?) ";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, foodID);
                stm.setInt(3, quantity);
                
                int row = stm.executeUpdate();
                if (row > 0) {
                    return row;
                }
            }
        } finally {
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
        return 0;
    }
    
    public CartDTO getItemFromCart(String userID, String foodID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = Tools.makeConnection();
            if(con !=null){
                String sql = "Select quantity "
                        + "From cart "
                        + "Where userID = ? "
                        + "And foodName = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, foodID);
                
                rs = stm.executeQuery();
                
                if(rs.next()){
                    int quantity = rs.getInt(1);
                    CartDTO dto = new CartDTO(userID, foodID, quantity);
                    return dto;
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
        return null;
    }
    
    public int updateCart(String userID, String itemID, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();
            
            if(con != null){
                String sql ="Update cart set quantity = ? "
                            + "Where userID = ? "
                            + "And foodName = ? ";
                
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, userID);
                stm.setString(3, itemID);
                
                int row = stm.executeUpdate();
                if (row > 0) {
                    return row;
                }
            }
        } finally {
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
        return 0;
    }
    
    public void deleteCart(String userID, String itemID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = Tools.makeConnection();
            
            if(con != null){
                String sql ="Delete from cart "
                            + "Where userID = ? "
                            + "And foodName = ? ";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
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
