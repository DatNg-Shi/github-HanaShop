/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.cart.CartDAO;
import DatNT.cart.CartDTO;
import DatNT.food.FoodDAO;
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

/**
 *
 * @author nguye
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final String SEARCHING_PAGE = "WelcomeUserServlet";
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

        String itemID = request.getParameter("txtItemID");
        String txtAmount = request.getParameter("txtAmount");
        int quantity = txtAmount != null ? Integer.parseInt(txtAmount) : 1;
        System.out.println(quantity + " --- quan");
        String status = "Active";
        String user = "";
        boolean foodActive = false;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                user = (String) session.getAttribute("USERID");
                if (user != null) {
                    FoodDAO dao = new FoodDAO();
                    foodActive = dao.checkStatus(itemID, status);
                    if (foodActive) {
                        CartDAO cartDao = new CartDAO();
                        CartDTO cartDto = cartDao.getItemFromCart(user, itemID);
                        if (cartDto != null) {
                            quantity = cartDto.getQuantity() + quantity;
                            int result = cartDao.updateCart(user, itemID, quantity);
                        } else {
                            int result = cartDao.addToCart(user, itemID, quantity);
                        }
                        request.setAttribute("MESS_ADDTOCART", "Food was successfully added to your cart.");
                    }else{
                        request.setAttribute("MESS_ADDTOCART", "This food is out of stock!!!");
                    }
                    url = SEARCHING_PAGE;
                }
            }

        } catch (SQLException ex) {
            log("AddToCartServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddToCartServlet _ NamingException: " + ex.getMessage());
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
