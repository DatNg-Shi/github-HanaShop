/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.cart.CartDAO;
import DatNT.food.FoodDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "SaveChangeCartServlet", urlPatterns = {"/SaveChangeCartServlet"})
public class SaveChangeCartServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "ViewCartServlet";

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
        String status = "Active";
        List<String> invalid_item_List = null;
        String itemList[] = request.getParameterValues("listCodeAndAmount");
        String url = VIEW_CART_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String user = (String) session.getAttribute("USERID");
                if (user != null) {
                    for (String itemObj : itemList) {
                        String items[] = itemObj.split(";;;;;");
                        String foodID = items[0].trim();
                        String foodName = items[1].trim();
                        int quantity = Integer.parseInt(items[2].trim());

                        FoodDAO fDao = new FoodDAO();
                        boolean isActive = fDao.checkStatus(foodID, status);
                        CartDAO cartDao = new CartDAO();
                        if (isActive) {
                            cartDao.updateCart(user, foodID, quantity);
                        } else {
                            cartDao.deleteCart(user, foodID);
                            if (invalid_item_List == null) {
                                invalid_item_List = new ArrayList<>();
                            }
                            invalid_item_List.add(foodName);
                        }
                    }
                    request.setAttribute("REMOVELIST", invalid_item_List);
                }
            }
        } catch (SQLException ex) {
            log("SaveChangeCartServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SaveChangeCartServlet _ NamingException: " + ex.getMessage());
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
