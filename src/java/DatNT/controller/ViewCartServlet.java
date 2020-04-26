/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.cart.CartDAO;
import DatNT.cart.CartDTO;
import DatNT.food.FoodDAO;
import DatNT.food.FoodDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
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
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    private final String CART_PAGE = "cart.jsp";
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
        LinkedHashMap<CartDTO, FoodDTO> list = null;
        String user = "";
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                user = (String) session.getAttribute("USERID");
                if (user != null) {
                    CartDAO cartDao = new CartDAO();
                    cartDao.getCartList(user);
                    List<CartDTO> cartDtos = cartDao.getCartList();
                    FoodDAO dao = new FoodDAO();
                    dao.getCategory();
                    List<FoodDTO> listCategory = dao.getListCategory();
                    if (cartDtos != null) {
                        list = new LinkedHashMap<>();

                        for (CartDTO cartDTO : cartDtos) {
                            String itemID = cartDTO.getFoodID();
                            FoodDTO dto = dao.viewFood(itemID);
                            list.put(cartDTO, dto);
                        }
                        request.setAttribute("CARTLIST", list);
                    }
                    request.setAttribute("LISTCATEGORY", listCategory);
                    url = CART_PAGE;
                }
            }

        } catch (SQLException ex) {
            log("ViewCartServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("ViewCartServlet _ NamingException: " + ex.getMessage());
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
