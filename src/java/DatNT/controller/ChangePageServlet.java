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
@WebServlet(name = "ChangePageServlet", urlPatterns = {"/ChangePageServlet"})
public class ChangePageServlet extends HttpServlet {

    private final String ADMIN_PAGE = "adminPage.jsp";
    private final String SEARCHING_PAGE = "searching.jsp";

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

        String searchValue = request.getParameter("txtSearch");
        String statusFood = request.getParameter("txtStatusFood");
        String category = request.getParameter("txtCategory");
        System.out.println(category + " -- check category");
        String moneyMinValue = request.getParameter("txtMin");
        String moneyMaxValue = request.getParameter("txtMax");
        System.out.println(moneyMinValue + " --- MIN" + moneyMaxValue + "--- Max");
        float min = moneyMinValue != null ? (moneyMinValue.isEmpty() ? 0 : Float.parseFloat(moneyMinValue)) : 0;
        float max = moneyMaxValue != null ? (moneyMaxValue.isEmpty() ? Float.MAX_VALUE : Float.parseFloat(moneyMaxValue)) : Float.MAX_VALUE;

        String page = request.getParameter("page").trim();
        String pages[] = new String[2];
        String option = request.getParameter("option");
        int totalFoodPerPage = Tools.totalFoodPerPage;
        String userID = "";
        String url = SEARCHING_PAGE;

        try {
            if (page != null) {
                System.out.println("check 1");
                HttpSession session = request.getSession(false);
                if (session != null) {
                    System.out.println("check 2");
                    userID = (String) session.getAttribute("USERID");
                    System.out.println(userID);
                    if (userID != null) {
                        System.out.println("check 3");
                        UserInfoDAO dao = new UserInfoDAO();
                        if (dao.checkAdmin(userID)) {
                            System.out.println("check 4");

                            url = ADMIN_PAGE;
                        }
                    }
                }
                System.out.println("check 5");
                if (category.matches("All")) {
                    category = "";
                }

                pages = page.split("/");
                int pageNum = Integer.parseInt(pages[0]);
                FoodDAO dao = new FoodDAO();
                int totalPage = dao.totalPage(totalFoodPerPage, searchValue, category, min, max, statusFood);
                System.out.println(totalPage + " -- check totalPage");
                if (totalPage > 0) {
                    System.out.println("check 6");
                    if (option.matches("previous") && pageNum > 1) {
                        pageNum = pageNum - 1;
                    } else if (option.matches("next") && pageNum < totalPage) {
                        pageNum = pageNum + 1;
                    }
                    dao.searchFood(pageNum, totalFoodPerPage, searchValue, category, min, max, statusFood);
                    dao.getCategory();
                    List<FoodDTO> result = dao.getListFood();
                    List<FoodDTO> listCategory = dao.getListCategory();
                    if (statusFood.equals("Active")) {
                        request.setAttribute("ISACTIVE", "yes");
                    }
                    request.setAttribute("SEARCHRESULT", result);
                    request.setAttribute("LISTCATEGORY", listCategory);
                    request.setAttribute("SHOWFOOD", "show");
                    request.setAttribute("PAGE", (pageNum + "/" + totalPage));
                }

            }
        } catch (SQLException ex) {
            log("ChangePageServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("ChangePageServlet _ NamingException: " + ex.getMessage());
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
