/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

/**
 *
 * @author nguye
 */
public class Tools implements Serializable {
    
    public static String minDate = "1753-01-01";
    public static String maxDate = LocalDate.now().toString();
    public static Integer totalFoodPerPage = 6;

    public static Timestamp getTime() {
        Date date = new Date();
        long dateTime = date.getTime();
        Timestamp ts = new Timestamp(dateTime);
        return ts;

    }
    
    public static Connection makeConnection() 
            throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcat = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcat.lookup("DB001");
        Connection con = ds.getConnection();
        
        return con;
    }
    
    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
