/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.userInfo;

import DatNT.utils.Tools;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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
public class UserInfoDAO implements Serializable {

    private UserInfoDTO fullName;

    public boolean checkLogin(String userID, String password)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Make connection
            con = Tools.makeConnection();
            if (con != null) {

                //2. Create Sql String
                String sql = "Select userID, password, name, role "
                        + "From UserInfo "
                        + "Where userID = ? And password = ? ";
                //3. Create Statement & assign parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                //4. Excute query
                rs = stm.executeQuery();
                //5. Process rs
                if (rs.next()) {
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    this.fullName = new UserInfoDTO(userID, password, name, role);

                    return true;
                }
            } //end if con is null
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

    private List<UserInfoDTO> listAccounts;

    public List<UserInfoDTO> getListAccounts() {
        return listAccounts;
    }

//    public boolean deleteAccount(String username)
//            throws SQLException, NamingException {
//        Connection con = null;
//        PreparedStatement stm = null;
//
//        try {
//            con = DBUtilites.makeConnection();
//            if (con != null) {
//                String sql = "SELECT UserRoles.roleInfo"
//                        + "FROM UserRoles, UserInfo "
//                        + "Where username = ? ";
//
//                stm = con.prepareStatement(sql);
//                stm.setString(1, username);
//
//                int row = stm.executeUpdate();
//
//                if (row > 0) {
//                    return true;
//                }
//            }
//        } finally {
//            if (stm != null) {
//                stm.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//        return false;
//    }
    public boolean checkAdmin(String userID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            System.out.println(userID + "------ DAO");
            con = Tools.makeConnection();
            if (con != null) {

                String sql = "SELECT UserInfo.role "
                        + "FROM UserInfo "
                        + "Where UserInfo.userID = ?; ";

                stm = con.prepareStatement(sql);
                stm.setString(1, userID);

                rs = stm.executeQuery();

                if (rs.next()) {
                    String role = "admin";
                    if (rs.getString(1).matches(role)) {
                        return true;
                    }
                }
            } //end if con is null
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return false;
    }

    public boolean createNewAccount(String userID, String password, String name, String role)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Make connection
            con = Tools.makeConnection();
            if (con != null) {
                //2. Create Sql String
                String sql = "Insert Into UserInfo(userID, password, name, role ) "
                        + "Values(?,?,?,?) ";
                //3. Create Statement & assign parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                stm.setString(3, name);
                stm.setString(4, role);

                //4. Excute query
                int row = stm.executeUpdate();

                if (row > 0) {
                    return true;
                }
            } //end if con is null
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public UserInfoDTO getFullName() {
        return fullName;
    }

}
