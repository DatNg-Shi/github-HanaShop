/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.food.FoodDAO;
import DatNT.food.FoodDTO;
import DatNT.userInfo.UserInfoDAO;
import DatNT.utils.Tools;
import java.io.IOException;
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
@WebServlet(name = "SearchFoodServlet", urlPatterns = {"/SearchFoodServlet"})
public class SearchFoodServlet extends HttpServlet {

    private final String SEARCHING_PAGE = "searching.jsp";
    private final String ADMIN_PAGE = "adminPage.jsp";

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
        String url = SEARCHING_PAGE;

        int totalFoodPerPage = Tools.totalFoodPerPage;
        String searchValue = request.getParameter("txtSearch");
        String moneyMinValue = request.getParameter("txtMin");
        String moneyMaxValue = request.getParameter("txtMax");
        System.out.println(moneyMinValue + " --- MIN" + moneyMaxValue + "--- Max");
        float min = moneyMinValue != null ? (moneyMinValue.isEmpty() ? 0 : Float.parseFloat(moneyMinValue)) : 0;
        float max = moneyMaxValue != null ? (moneyMaxValue.isEmpty() ? Float.MAX_VALUE : Float.parseFloat(moneyMaxValue)) : Float.MAX_VALUE;
        String category = request.getParameter("txtCategory");
        String statusFood = request.getParameter("txtStatusFood");
        System.out.println(statusFood + " - statusFood");
        System.out.println(category + " - category");
        String userID = "";

        try {
            if (category.matches("All")) {
                category = "";
            }

            HttpSession session = request.getSession();
            userID = (String) session.getAttribute("USERID");
            System.out.println(userID + " -userID");
            if (userID != null) {
                UserInfoDAO dao = new UserInfoDAO();
                if (dao.checkAdmin(userID)) {
                    url = ADMIN_PAGE;
                }
            }

            FoodDAO dao = new FoodDAO();
            dao.getCategory();
            List<FoodDTO> listCategory = dao.getListCategory();
            request.setAttribute("LISTCATEGORY", listCategory);
            int totalPage = dao.totalPage(totalFoodPerPage, searchValue, category, min, max, statusFood);
            System.out.println(totalPage + " totalPage");
            if (totalPage > 0) {
                dao.searchFood(1, totalFoodPerPage, searchValue, category, min, max, statusFood);

                List<FoodDTO> result = dao.getListFood();
                if (statusFood.equals("Active")) {
                    request.setAttribute("ISACTIVE", "yes");
                }
                request.setAttribute("SEARCHRESULT", result);
                request.setAttribute("SHOWFOOD", "show");
                request.setAttribute("PAGE", ("1/" + totalPage));
            }

        } catch (SQLException ex) {
            log("SearchFoodServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchFoodServlet _ NamingException: " + ex.getMessage());
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
