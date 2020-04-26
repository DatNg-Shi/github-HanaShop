/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.controller;

import DatNT.food.CreateFoodError;
import DatNT.food.FoodDAO;
import DatNT.food.FoodDTO;
import DatNT.utils.Tools;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CreateFoodServlet", urlPatterns = {"/CreateFoodServlet"})
public class CreateFoodServlet extends HttpServlet {

    private final String ADMIN_PAGE = "WelcomeAdminServlet";
    private final String ERROR_PAGE = "createFood.jsp";
    private final String CREATESUCC = "createFood.jsp";

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
        String url = ERROR_PAGE;
        String button = "";
        String name = "";
        String description = "";
        String price = "";
        String format = "^[0-9]+$";
        double priceFood = 0;
        Timestamp date = Tools.getTime();
        String category = "";
        String quantity = "";
        int quantityFood = 1;
        String source = "";
        String statusFood = "";
        String checkPath = (".*.jpg$"
                + "||.*.JPG$"
                + "||.*.png$"
                + "||.*.PNG$"
                + "||.*.GIF$"
                + "||.*.gif$");
        boolean foundErr = false;
        CreateFoodError errors = new CreateFoodError();
        try {
            boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
            if (!isMultiPart) {
                url = ERROR_PAGE;
            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    log("CreateFoodServlet _ FileUploadException: " + e.getMessage());
                }
                Iterator iter = items.iterator();
                Hashtable params = new Hashtable();
                String fileNameImg = null;
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        params.put(item.getFieldName(), item.getString());
                    } else {
                        try {
                            String itemName = item.getName();
                            fileNameImg = itemName.substring(itemName.lastIndexOf("\\") + 1);
                            System.out.println("path " + fileNameImg);
                            if (!fileNameImg.matches(checkPath)) {
                                foundErr = true;
                                errors.setImageEmpty("Only support .jpg or .gif or .png!");
                            } else {
                                String RealPath_Tmp = getServletContext().getRealPath("/") + "images\\" + fileNameImg;
                                System.out.println("Rpath " + RealPath_Tmp);
                                String RealPath = RealPath_Tmp.replace("build\\web\\", "web\\");
                                System.out.println("RealPath " + RealPath);
                                File savedFile = new File(RealPath);
                                item.write(savedFile);
                            }
                        } catch (Exception e) {
                            log("CreateFoodServlet _ Exception: " + e.getMessage());
                        }
                    }
                }//end while
                button = (String) params.get("btAction");
                System.out.println(button + " --- btAction");
                name = (String) params.get("txtName");
                description = (String) params.get("txtDescription");
                price = (String) params.get("txtPrice");
                category = (String) params.get("txtCategory");
                quantity = (String) params.get("txtQuantity");
                statusFood = (String) params.get("txtStatus");
                source = (String) params.get("txtSource");

                if (button.equals("Cancel")) {
                    url = ADMIN_PAGE;
                } else {
                    System.out.println("checked 1--------------");
                    System.out.println("checked 1");
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        System.out.println("checked 2");
                        if (name.isEmpty()) {
                            foundErr = true;
                            errors.setNameEmpty("The name must not empty!!!");
                        }
                        if (fileNameImg.isEmpty()) {
                            foundErr = true;
                            errors.setImageEmpty("The image must not empty!!!");
                        }
                        if (description.isEmpty()) {
                            foundErr = true;
                            errors.setDescriptionEmpty("The description must not empty!!!");
                        }
                        if (price.isEmpty() || !price.matches(format)) {
                            foundErr = true;
                            errors.setPriceEmpty("The price must not empty and input only number!!!");
                        } else {
                            priceFood = Double.parseDouble(price);
                        }
                        if (category.isEmpty()) {
                            foundErr = true;
                            errors.setCategoryEmpty("The category must not empty!!!");
                        }
                        if (quantity.isEmpty() || !quantity.matches(format)) {
                            foundErr = true;
                            errors.setQuantityEmpty("The quantity must not empty and input only number!!!");
                        } else {
                            quantityFood = Integer.parseInt(quantity);
                        }
                        if (source.isEmpty()) {
                            foundErr = true;
                            errors.setSourceEmpty("The source must not empty!!!");
                        }
                        if (foundErr) {
                            request.setAttribute("CREATEERRORS", errors);
                            url = ERROR_PAGE;
                        } else {
                            FoodDAO dao = new FoodDAO();
                            int result = dao.createNewFood(name, fileNameImg, description, priceFood, date, category, quantityFood, source, statusFood);
                            System.out.println(result + " ---result create");
                            if (result > 0) {
                                FoodDTO dto = new FoodDTO();
                                dto = dao.viewFood(name);
                                errors.setMessCreateSucc("Create SuccessFully!!!");
                                request.setAttribute("VIEWRESULT", dto);
                                request.setAttribute("CREATEERRORS", errors);
                                request.setAttribute("FILENAME", fileNameImg);
                                url = CREATESUCC;
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("CreateFoodServlet _ SQLException " + ex.getMessage());
            if (ex.getMessage().contains("duplicate")) {
                errors.setNameIsExisted(name + " is existed!!!");
                request.setAttribute("CREATEERRORS", errors);
            }
        } catch (NamingException ex) {
            log("CreateFoodServlet _ NamingException " + ex.getMessage());
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
