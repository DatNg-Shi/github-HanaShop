/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.userInfo.UserInfoDAO;
import DatNT.userInfo.UserInfoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String HANA_SHOP = "welcomeUser";
    private final String SEARCH_PAGE = "welcomeUser";
    private final String ADMIN_PAGE = "welcomeAdmin";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HANA_SHOP;
        String welcome = "";
        
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie: cookies) {
                    String userID = cookie.getName();
                    String password = cookie.getValue();
                    
                    UserInfoDAO dao = new UserInfoDAO();
                    boolean result = dao.checkLogin(userID, password);
                                        
                    if (result) {
                        HttpSession session = request.getSession();
                        UserInfoDTO thisName = dao.getFullName();
                        if (thisName.getRole().matches("admin")) {  
                            welcome = " (admin)";
                            url = ADMIN_PAGE;
                            session.setAttribute("ISADMIN", "yes");
                        } else {
                            url = SEARCH_PAGE;
                        }
                        
                        session.setAttribute("USERID", userID);
                        session.setAttribute("NAME", thisName.getFullName() + welcome);
                        break;
                        
                    }
                }
            }

        } catch (NamingException ex) {
            log("StartusServlet _ NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("StartusServlet _ SQLException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
