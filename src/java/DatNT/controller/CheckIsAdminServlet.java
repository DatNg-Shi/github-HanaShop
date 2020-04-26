/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.userInfo.UserInfoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CheckIsAdminServlet", urlPatterns = {"/CheckIsAdminServlet"})
public class CheckIsAdminServlet extends HttpServlet {

    private final String CREATE_SERVLET = "CreatePageServlet";
    private final String SEARCH_AND_UPDATE_SERVLET = "ManagerItemsListServlet";
    private final String CREATE_FOOD_SERVLET = "CreateFoodServlet";
    private final String ADMIN_PAGE = "WelcomeAdminServlet";
    private final String CHANGE_STATUS_ITEM_SERVLET = "ChangeStatusItemServlet";
    private final String MANAGER_VIEW_ITEM_SERVLET = "ManagerViewItemServlet";
    private final String MANAGER_UPDATE_SERVLET = "ManagementUpdateServlet";
    private final String LOGIN_PAGE = "login.jsp";

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
        PrintWriter out = response.getWriter();
        String userID = "";
        String url = LOGIN_PAGE;
        
        String button = request.getParameter("btAction");
        System.out.println(button + " -------- button");
        try {
            HttpSession session = request.getSession(false);
//            check is user=======================================================
            if (session != null) {
                userID = (String) session.getAttribute("USERID");
                UserInfoDAO userDao = new UserInfoDAO();
                if (userDao != null) {
                    if (userDao.checkAdmin(userID)) {
//                        if (button.equals("create")) {
//                            url = CREATE_SERVLET;
                        if (button.equals("searchItemAd")
                                || button.equals("Return to Products Management Page")) {
                            url = SEARCH_AND_UPDATE_SERVLET;
                        } else if (button.equals("Create")) {
                            url = CREATE_FOOD_SERVLET;
                        } else if (button.equals("Cancle")) {
                            url = ADMIN_PAGE;
                        } else if (button.equals("Confirm delete")
                                || button.equals("Delete this Product")
                                || button.equals("Confirm reactivate")
                                || button.equals("Reactivate this Product")) {
                            url = CHANGE_STATUS_ITEM_SERVLET;
                        } else if (button.equals("detail")) {
                            url = MANAGER_VIEW_ITEM_SERVLET;
                        } else if (button.equals("Confirm update")) {
                            url = MANAGER_UPDATE_SERVLET;
                        }
                    }

                }
            }
        } catch (NamingException ex) {
            log("CheckIsAdminServlet _ NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("CheckIsAdminServlet _ SQLException " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();

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
