/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.userInfo.LoginError;
import DatNT.userInfo.UserInfoDAO;
import DatNT.userInfo.UserInfoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
public class LoginServlet extends HttpServlet {

    private final String ERROR = "login.jsp";
    private final String SEARCH_PAGE = "WelcomeUserServlet";
    private final String ADMIN_PAGE = "WelcomeAdminServlet";

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
        String url = ERROR;

        String userID = request.getParameter("txtUserID");
        String password = request.getParameter("txtPassword");
        String regex = "^[a-zA-Z0-9]+$";
        boolean foundErr = false;
        String welcome = "";
        LoginError error = new LoginError();

        try {
            if (!userID.matches(regex)) {
                foundErr = true;
                error.setNoSpecialChar("Empty or No special characters!!!");
            }

            UserInfoDAO dao = new UserInfoDAO();
            boolean result = dao.checkLogin(userID, password);
            if (result) {
                HttpSession session = request.getSession();
                UserInfoDTO thisName = dao.getFullName();
                System.out.println(thisName.getRole());
                if (thisName.getRole().trim().equals("admin")) {  
                    welcome = " (admin)";
                    session.setAttribute("ISADMIN", "yes");
                    url = ADMIN_PAGE;
                } else {
                    url = SEARCH_PAGE;
                }
                System.out.println(thisName.getFullName() + welcome);
                session.setAttribute("USERID", userID);
                session.setAttribute("NAME", thisName.getFullName() + welcome);

                Cookie cookie = new Cookie(userID, password);
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);

            } else {
                foundErr = true;
                error.setAccountNotFound("This account not existed or Invalid email or password!!!");
            }

            if (foundErr) {
                request.setAttribute("LOGINERROR", error);
            }

        } catch (NamingException ex) {
            log("LoginServlet _ NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("LoginServlet _ SQLException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
