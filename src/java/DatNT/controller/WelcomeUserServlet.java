/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.food.FoodDAO;
import DatNT.food.FoodDTO;
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

/**
 *
 * @author nguye
 */
@WebServlet(name = "WelcomeUserServlet", urlPatterns = {"/WelcomeUserServlet"})
public class WelcomeUserServlet extends HttpServlet {

    private final String USER_PAGE = "searching.jsp";

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
        String url = USER_PAGE;
        int totalFoodPerPage = Tools.totalFoodPerPage;
        String searchValue = "";
        String category = "";
        String statusFood = "Active";

        float price1 = 0;
        float price2 = Float.MAX_VALUE;

        try {
            FoodDAO dao = new FoodDAO();
            int totalPage = dao.totalPage(totalFoodPerPage, searchValue, category, price1, price2, statusFood);

            if (totalPage > 0) {
                dao.searchFood(1, totalFoodPerPage, searchValue, category, price1, price2, statusFood);
                dao.getCategory();
                List<FoodDTO> listCategory = dao.getListCategory();
                List<FoodDTO> result = dao.getListFood();
                if (statusFood.equals("Active")) {
                    request.setAttribute("ISACTIVE", "yes");
                }
                request.setAttribute("SEARCHRESULT", result);
                request.setAttribute("LISTCATEGORY", listCategory);
                request.setAttribute("ALL", "All");
                request.setAttribute("SHOWFOOD", "show");
                request.setAttribute("PAGE", ("1/" + totalPage));
            }
        } catch (SQLException ex) {
            log("WelcomeUserServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("WelcomeUserServlet _ NamingException: " + ex.getMessage());
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
