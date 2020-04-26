/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.cart.CartDAO;
import DatNT.food.FoodDAO;
import DatNT.food.FoodDTO;
import DatNT.purchaseHistory.PurchaseHistoryDAO;
import DatNT.utils.Tools;
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
@WebServlet(name = "ConfirmItemServlet", urlPatterns = {"/ConfirmItemServlet"})
public class ConfirmItemServlet extends HttpServlet {

    private final String ERROR_PAGE = "ViewCartServlet";
    private final String SEARCHING_PAGE = "WelcomeUserServlet";

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
        String url = ERROR_PAGE;
        String status = "Active";
        List<String> invalid_item_List = null;
        List<String> amount_exceed_item_List = null;
        String itemList[] = request.getParameterValues("listCodeAndAmount");
        boolean foundErr = false;

        try {
            HttpSession session = request.getSession(false);
            String userID = (String) session.getAttribute("USERID");

            FoodDAO fDao = null;
            PurchaseHistoryDAO purDao = null;
            CartDAO cartDao = null;

            for (String itemObj : itemList) {
                String item[] = itemObj.split(";;;;;");
                String itemID = item[0].trim();
                String itemName = item[1].trim();
                int amount = Integer.parseInt(item[2].trim());

                fDao = new FoodDAO();
                boolean isActive = fDao.checkStatus(itemID, status);
                cartDao = new CartDAO();
                if (isActive) {

                    FoodDTO fDto = fDao.viewFood(itemID);
                    if (fDto.getQuantity() < amount) {
                        if (amount_exceed_item_List == null) {
                            amount_exceed_item_List = new ArrayList<>();
                        }
                        amount_exceed_item_List.add(fDto.getName() + " is only has " + fDto.getQuantity() + " in store. Please choose again!!!");
                        foundErr = true;
                    }
                } else {

                    cartDao.deleteCart(userID, itemID);
                    if (invalid_item_List == null) {
                        invalid_item_List = new ArrayList<>();
                    }
                    invalid_item_List.add(itemName);
                    foundErr = true;
                }
            }
            if (foundErr) {
                request.setAttribute("REMOVELIST", invalid_item_List);
                request.setAttribute("AMOUNTEXCEEDLIST", amount_exceed_item_List);
            } else {
                for (String itemObj : itemList) {
                    String item[] = itemObj.split(";;;;;");
                    String itemID = item[0].trim();
                    int amount = Integer.parseInt(item[2].trim());

                    fDao = new FoodDAO();
                    purDao = new PurchaseHistoryDAO();
                    cartDao = new CartDAO();

                    FoodDTO fDto = fDao.viewFood(itemID);
                    purDao.storePurchaseHistory(userID, itemID, fDto.getImage(), fDto.getName(), amount, fDto.getPrice(), Tools.getTime());
                    int left = fDto.getQuantity() - amount;
                    fDao.updateFoodAfterBought(itemID, left);
                    cartDao.deleteCart(userID, itemID);
                    request.setAttribute("BUYSUCCESS", "Thanks for your choice with my store.");
                    url = SEARCHING_PAGE;
                }
            }
        } catch (SQLException ex) {
            log("ConfirmItemServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("ConfirmItemServlet _ NamingException: " + ex.getMessage());
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
