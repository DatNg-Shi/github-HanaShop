/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.food.FoodDAO;
import DatNT.food.FoodDTO;
import DatNT.food.UpdateFoodError;
import DatNT.utils.Tools;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author nguye
 */
@WebServlet(name = "UpdateFoodServlet", urlPatterns = {"/UpdateFoodServlet"})
public class UpdateFoodServlet extends HttpServlet {

    private final String ERROR_PAGE = "foodDetail.jsp";
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
            throws ServletException, IOException, NamingException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        String confirm = request.getParameter("btAction");
        System.out.println(confirm + " --- check confirm");
        String name = request.getParameter("txtName");
        String pk = request.getParameter("pk");

        String imageOld = request.getParameter("txtImageOld");
        String imageNew = request.getParameter("txtImageNew");
        String image = null;
        String checkPath = (".*.jpg$"
                + "||.*.JPG$"
                + "||.*.png$"
                + "||.*.PNG$"
                + "||.*.GIF$"
                + "||.*.gif$");
        
        String description = request.getParameter("txtDescription");
        String price = request.getParameter("txtPrice");
        String format = "^[0-9\\.]+$";
        float priceFood = 0;
        String txtDate = request.getParameter("txtCreateDate");
        Timestamp date = Timestamp.valueOf(txtDate);
        Timestamp updateDate = Tools.getTime();
        String category = request.getParameter("txtCategory");
        String quantity = request.getParameter("txtQuantity");
        int quantityFood = 1;
        String source = request.getParameter("txtSource");
        String statusFood = "Inactive";

        boolean foundErr = false;
        UpdateFoodError errors = new UpdateFoodError();
        FileItem item = null;
        FoodDAO daoCate = new FoodDAO();
        daoCate.getCategory();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                if (confirm.equals("Delete")) {
                    System.out.println("checked delete");
                    FoodDAO dao = new FoodDAO();
                    int result = dao.changeStatus(pk, statusFood, updateDate);
                    if (result > 0) {
                        url = ADMIN_PAGE;
                    }
                } else if (confirm.equals("Restore")) {
                    FoodDAO dao = new FoodDAO();
                    statusFood = "Active";
                    int result = dao.changeStatus(pk, statusFood, updateDate);
                    if (result > 0) {
                        url = ADMIN_PAGE;
                    }
                } else {
                    System.out.println("checked 2");
                    if (name.isEmpty()) {
                        System.out.println("checked 2.1");
                        foundErr = true;
                        errors.setNameEmpty("The name must not empty!!!");
                    }
                    if (imageNew.isEmpty()) {
                        System.out.println("checked 2.2");
                        image = imageOld;
                    } else {
                        try {
                            image = imageNew.substring(imageNew.lastIndexOf("\\") + 1);
                            System.out.println("path " + image);
                            if (!image.matches(checkPath)) {
                                foundErr = true;
                                errors.setImageEmpty("Only support .jpg or .gif or .png!");
                            } else {
                                String RealPath = getServletContext().getRealPath("/") + "images\\" + image;
                                System.out.println("Rpath " + RealPath);
                                File savedFile = new File(RealPath);
                                item.write(savedFile);
                            }
                        } catch (Exception e) {
                            log("CreateFoodServlet _ Exception: " + e.getMessage());
                        }
//                        System.out.println("checked 2.3");
//                        int lastIndex = imageNew.lastIndexOf("\\");
//                        image = imageNew.substring(lastIndex + 1);
//                        System.out.println(image + " --- image");
                    }
                    if (description.isEmpty()) {
                        System.out.println("checked 2.4");
                        foundErr = true;
                        errors.setDescriptionEmpty("The description must not empty!!!");
                    }
                    if (price.isEmpty() || !price.matches(format)) {
                        System.out.println("checked 2.5");
                        foundErr = true;
                        errors.setPriceEmpty("The price must not empty and input only number!!!");
                    } else {
                        System.out.println("checked 2.6");
                        priceFood = Float.parseFloat(price);
                    }
                    if (category.isEmpty()) {
                        System.out.println("checked 2.7");
                        foundErr = true;
                        errors.setCategoryEmpty("The category must not empty!!!");
                    }
                    if (quantity.isEmpty() || !quantity.matches(format)) {
                        System.out.println("checked 2.8");
                        foundErr = true;
                        errors.setQuantityEmpty("The quantity must not empty and input only number!!!");
                    } else if (Integer.parseInt(quantity) < 1) {
                        foundErr = true;
                        errors.setQuantityEmpty("the value must bigger than 1!!!");
                    } else {
                        System.out.println("checked 2.9");
                        quantityFood = Integer.parseInt(quantity);
                    }
                    if (source.isEmpty()) {
                        System.out.println("checked 2.10");
                        foundErr = true;
                        errors.setSourceEmpty("The source must not empty!!!");
                    }
                    if (foundErr) {
                        System.out.println("checked 3");
                        FoodDAO dao = new FoodDAO();
                        dao.getCategory();
                        List<FoodDTO> listCategory = dao.getListCategory();
                        FoodDTO dto = new FoodDTO(name, image, description, priceFood, date, category, quantityFood, source, "Active", updateDate);
                        request.setAttribute("VIEWRESULT", dto);
                        request.setAttribute("PRIMARYKEY", pk);
                        request.setAttribute("LISTCATEGORY", listCategory);
                        request.setAttribute("CREATEERRORS", errors);
                        url = ERROR_PAGE;
                    } else {
                        System.out.println("checked 4");
                        FoodDAO dao = new FoodDAO();
                        int result = dao.updateFood(pk, name, image, description, priceFood, category, quantityFood, source, updateDate);
                        System.out.println(result + " ---result create");
                        if (result > 0) {
                            System.out.println("checked 5");
                            url = ADMIN_PAGE;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("UpdateFoodServlet _ SQLException " + ex.getMessage());
            if (ex.getMessage().contains("duplicate")) {
                errors.setNameIsExisted(name + " is existed!!!");
                List<FoodDTO> listCategory = daoCate.getListCategory();
                FoodDTO dto = new FoodDTO(name, image, description, priceFood, date, category, quantityFood, source, "Active", updateDate);
                request.setAttribute("LISTCATEGORY", listCategory);
                request.setAttribute("CREATEERRORS", errors);
                request.setAttribute("VIEWRESULT", dto);
                request.setAttribute("PRIMARYKEY", pk);
            }
        } catch (NamingException ex) {
            log("UpdateFoodServlet _ NamingException " + ex.getMessage());
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UpdateFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UpdateFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
