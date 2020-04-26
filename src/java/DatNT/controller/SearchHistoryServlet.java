/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.purchaseHistory.PurchaseHistoryDAO;
import DatNT.purchaseHistory.PurchaseHistoryDTO;
import DatNT.utils.Tools;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

    private final String HISTORY_PAGE = "shoppingHistory.jsp";
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
        String url = LOGIN_PAGE;
        String user = "";
        String searchVar = request.getParameter("txtSearchHistory");
        String searchMinTime = request.getParameter("txtMinDate");
        String searchMaxTime = request.getParameter("txtMaxDate");

        String itemName = searchVar == null ? "" : (searchVar);
        String minDateTime = searchMinTime == null ? Tools.minDate : (searchMinTime.isEmpty() ? Tools.minDate : searchMinTime);
        String maxDateTime = searchMaxTime == null ? Tools.maxDate : (searchMaxTime.isEmpty() ? Tools.maxDate : searchMaxTime);

        List<PurchaseHistoryDTO> list = null;

        try {
            HttpSession session = request.getSession(false);
//            check is user=======================================================
            if (session != null) {
                System.out.println("checke 1");
                user = (String) session.getAttribute("USERID");
                if (user != null) {
                     System.out.println("checke 2");
                    minDateTime = minDateTime + " 00:00:00:000";
                    maxDateTime = maxDateTime + " 23:59:59:999";

                    PurchaseHistoryDAO purDao = new PurchaseHistoryDAO();
                    purDao.GetPurchaseHistoryList(user, searchVar, minDateTime, maxDateTime);
                    list = purDao.getList();
                    request.setAttribute("HISTORYLIST", list);
                    request.setAttribute("SHOW", "show");
                    url = HISTORY_PAGE;
                }
            }
        } catch (SQLException ex) {
            log("SearchHistoryServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchHistoryServlet _ NamingException: " + ex.getMessage());
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
